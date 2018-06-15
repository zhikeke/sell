package com.ke.sell.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/3 19:23
 * 返回Json 数据
 */
@Data
public class JsonData<T> {
    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;


    public JsonData(Integer code) {
        this.code = code;
    }

    public static JsonData success(String msg, Object data) {
        JsonData jsonData = new JsonData(0);
        jsonData.msg = msg;
        jsonData.data = data;
        return jsonData;
    }

    public static JsonData success(Integer code, String msg, Object data) {
        JsonData jsonData = new JsonData(code);
        jsonData.msg = msg;
        jsonData.data = data;
        return jsonData;
    }

    public static JsonData success(Object data) {
        JsonData jsonData = new JsonData(0);
        jsonData.msg = "成功";
        jsonData.data = data;
        return jsonData;
    }

    public static JsonData success() {
        JsonData jsonData = new JsonData(0);
        jsonData.setMsg("成功");
        return jsonData;
    }

    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(1);
        jsonData.msg = msg;
        return jsonData;
    }

    public  Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }


}
