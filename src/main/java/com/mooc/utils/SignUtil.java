package com.mooc.utils;
import com.mooc.config.WechatAccountConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class SignUtil {

    public  String createSign(SortedMap<String,Object> parameters,String  key){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + key);
        String sign = DigestUtils.md5DigestAsHex(sb.toString().getBytes()).toUpperCase();
        return sign;
    }
}
