package Pojo.LjxUtils;

import Pojo.DB.Response;
import Pojo.LjxEx.TypeException;
import com.alibaba.fastjson2.JSONObject;

public class ResponseParse {

    private static Response responseA;

    public static Response parseResponse(String response) {
        if (StringUtils.isEmp(response)) {
            throw new TypeException("E000001_08");
        }
        try {
            responseA = JSONObject.parseObject(response, Response.class);
            return responseA;
        }catch (Exception e){
            throw new TypeException("E000001_08");
        }
    }


    /**
     * 通过返回值的key来获取字段
     * @param key
     */
    public static String getUserName(String res,String key) {
        parseResponse(res);
        if (responseA == null) {
            return null;
        }
        checkUserLoginStatus();
        JSONObject jsonObject = getJsonObject();
        return (String) jsonObject.get(key);
    }

    /**
     * 检查用户登录状态，如果没有登录抛出异常
     */
    public static String checkUserLoginStatus() {
        JSONObject jsonObject = getJsonObject();
        if (jsonObject == null) {
            throw new TypeException("E000001_09");
        }
        Object userLoginKey = jsonObject.get("userLoginKey");
        if (userLoginKey == null) {
            throw new TypeException("E000001_09");
        }
        return (String) userLoginKey;
    }


    public static JSONObject getJsonObject() {
        Object data = responseA.getData();
        if (data instanceof JSONObject) {
             return (JSONObject) data;
        }
        return null;
    }

    /**
     * 获取返回值的status
     * @return
     */
    public static String getStatus () {
        JSONObject jsonObject = getJsonObject();
        if (jsonObject != null) {
            return (String) jsonObject.get("status");
        }
        return "";
    }

    /**
     * 获取返回值的code
     * @return
     */
    public static String getCode () {
        JSONObject jsonObject = getJsonObject();
        if (jsonObject != null) {
            return (String) jsonObject.get("code");
        }
        return "";
    }

}
