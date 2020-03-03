package com.mooc.utils;

import org.springframework.util.DigestUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class UrlUtil {
    public static String createUrl(String hostname, SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append(hostname + "?");
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (it.hasNext()) {
                if (null != v && !"".equals(v)) {
                    sb.append(k + "=" + v + "&");
                }
            } else {
                if (null != v && !"".equals(v)) {
                    sb.append(k + "=" + v);
                }
            }
        }
        return sb.toString();
    }
}
