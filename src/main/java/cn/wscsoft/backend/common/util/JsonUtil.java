package cn.wscsoft.backend.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonUtil {
    public static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    @SuppressWarnings("unchecked")
    public static <T> List<T> fromList(String json, Class<T> eleType) {
        return (List<T>) GSON.fromJson(json, TypeToken.getParameterized(List.class, eleType).getType());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> fromList2D(String json, Class<T> eleType) {
        return (List<T>) GSON.fromJson(json, TypeToken.getParameterized(List.class, TypeToken.getParameterized(List.class, eleType).getRawType()).getType());
    }

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }
}
