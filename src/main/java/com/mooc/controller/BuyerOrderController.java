package com.mooc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.VO.ResultVO;
import com.mooc.converter.OrderForm2OrderDTOConverter;
import com.mooc.dto.OrderDTO;
import com.mooc.entity.OrderMaster;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.form.OrderForm;
import com.mooc.service.BuyerService;
import com.mooc.service.OrderService;
import com.mooc.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult=orderService.create(orderDTO);
        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size) {

        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】参数不正确，openid={}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        IPage<OrderDTO> orderDTOIPage = orderService.findList(new Page<OrderMaster>(page, size), openid);
        return ResultVOUtil.success(orderDTOIPage.getRecords());
    }

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        OrderDTO orderOne = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderOne);
    }
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
       buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
