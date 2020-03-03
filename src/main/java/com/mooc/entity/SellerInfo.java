package com.mooc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Data
public class SellerInfo {
    @TableId
    private String sellerId;

    private String username;

    private String password;

    private String openid;

    private Date create_time;

    private Date update_time;

}
