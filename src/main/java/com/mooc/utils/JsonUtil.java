package com.mooc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class JsonUtil {
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static Map Json2Map(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson((String)object , Map.class);
    }

}
