package com.mooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix ="projecturl")
public class ProjectUrlConfig {

    /**
     * 获取openIdurl
     */
    private String getOpenIdUrl;

    /**
     * 发起预支付url
     */
    private String prePayUrl;

    /**
     * 退款URL
     */
    private String refundUrl;

    /**
     * 项目url
     */
    private String sellUrl;
}
