package Pojo.LjxUtils;

import Pojo.DB.Base;
import Pojo.DB.Response;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResponseParse {

    private static Response<?> responseA;

    public static Response<?> parseResponse(String response) {
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
    private static String checkUserLoginStatus() {
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


    /**
     * 返回值的 data 是一个对象
     * @return
     */
    public static JSONObject getJsonObject() {
        Object data = responseA.getData();
        if (data instanceof JSONObject) {
             return (JSONObject) data;
        }
        return null;
    }


    /**
     * 返回值的data 是一个 数组
     * @return
     */
    public static <T extends Base> List<T> getArrayObject(String res, Class<T> cls) throws Exception {
        parseResponse(res);
        Object data = responseA.getData();
        List<T> tList = new CopyOnWriteArrayList<>();
        if (data instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) data;
            if (jsonArray.get(0) instanceof Map) {
                T t = cls.newInstance();
                jsonArray.forEach(item -> {
                    try {
                        T base = (T) MapUtils.MapToObject((Map) item, cls);
                        tList.add(base);
                    } catch (IllegalAccessException e) {
                        throw new DataException("getArrayObject 转换错误 : " + e.getMessage());
                    }
                });
            }
        }
        return tList;
    }


    /**
     * 返回值的data 是一个 object
     * @return
     */
    public static JSONObject getJsonObject(String res) {
        parseResponse(res);
        return getJsonObject();
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
