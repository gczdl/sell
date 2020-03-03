<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#include "../common/nav.ftl">

    <div id="page-content-wrapper">
        <div class="container-fluid">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-condensed table-hover table-bordered">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>地址</th>
                    <th>金额</th>
                    <th>订单状态</th>
                    <th>支付状态</th>
                    <th>创建时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTOIPage.records as orderDTO>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.getOrderStatusEnum().message}</td>
                        <td>${orderDTO.getPayStatusEnum().message}</td>
                        <td>${orderDTO.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}&size=${size}&page=${page}">详情</a></td>
                        <td>
                            <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}&size=${size}&page=${page}">取消</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#--分页-->
        <div class="col-md-12 column">
            <ul class="pagination pull-right" >
                <#if page lte 1>
                    <li class="disabled"><a href="#">Prev</a></li>
                <#else >
                    <li><a href="/sell/seller/order/list?page=${page-1}&size=${size}">Prev</a></li>
                </#if>

                <#list 1..totalPages as index>
                    <#if page==index>
                        <li class="disabled"><a href="#">${index}</a></li>
                    <#else >
                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                    </#if>
                </#list>


                <#if page gte totalPages>
                    <li class="disabled"><a href="#">Next</a></li>
                <#else >
                    <li><a href="/sell/seller/order/list?page=${page+1}&size=${size}">Next</a></li>
                </#if>
            </ul>
        </div>
    </div>
</div>
    </div>
</div>
</body>
</html>