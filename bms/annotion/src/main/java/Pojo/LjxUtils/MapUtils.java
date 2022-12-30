package Pojo.LjxUtils;


import Pojo.LjxEx.TypeException;
import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapUtils {

    private static <T> Field[] GetField(Class<T> tClass){
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t != null ? t.getClass().getDeclaredFields() : null;
    }


    /**
     * 检查该map中是否存在key
     */
    public static <T> boolean MapsKeys(Map<String,T> maps, String Checkey){
        for (String key : GetMapKey(maps)) {
            if (key.equals(Checkey))
                return true;
        }
        return false;
    }

    /**
     * 获取map中的所有key
     */
    public static <T> String[] GetMapKey(Map<String,T> maps){
        String[] Keys = new String[maps.size()];
        Set<Map.Entry<String, T>> entries = maps.entrySet();
        int i = 0;
        for (Map.Entry<String, T> entry : entries) {
            Keys[i] = entry.getKey();
            i++;
        }
        return Keys;
    }

    /**
     * 对前端查询条件进行过滤，key与bean字段相符则记录
     * @return
     */
    public static <V> Map<String,Object> ObjectToMap(V type) throws IllegalAccessException {
        Field[] fields = GetField(type.getClass());
        Map<String,Object> result = new HashMap<>();
        assert fields != null;
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(type);
            if (o == null) continue;

            if (o instanceof String){
                String value = (String) o;
                if (value.trim().equals(""))  continue;

                result.put(field.getName(),value);
            }
        }
        return result;
    }

    public static <T,V> V MapToObject(String data , Class<V> type) throws IllegalAccessException {
        if (StringUtils.isEmp(data)) throw new TypeException("data 不能为空");

        Map map = JSONObject.parseObject(data, Map.class);
        return MapToObject((Map<String,Object>) map.get("payload"),type);
    };

    /**
     *  将Map集合的数据转换成bean中的字段值，
     *  验证key和value的类型，两个都匹配了才会设置值
     *  不匹配的设置null
     */
    public static <T,V> V MapToObject(Map<String,T> maps , Class<V> type) throws IllegalAccessException {
        V newInstance = null;
        try {
            newInstance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
        Field[] declaredFields = newInstance.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            T value = null;
            if (maps.get(declaredField.getName()) != null) {
                value = maps.get(declaredField.getName());
            }
            if (value == null) {
                declaredField.set(newInstance,null);
                continue;
            }
            // 如果map中的值的类型能够向上转型成为Bean字段的类型
            if (declaredField.getType().isAssignableFrom(value.getClass())){
                declaredField.set(newInstance,value);
            }else {
                declaredField.set(newInstance,null);
            }
        }
        return newInstance;
    }

    /**
     * 验证key是否存在与 bean中, 用于解析查询接口的参数
     * 如果接口传入的字段不存在bean中，查询是没有结果的，也就不用继续执行下去了
     */
    public static <T,V> boolean MapExistsBean(String key , Class<V> type) {
        Field[] fields = GetField(type);
        int num = 0;
        for (Field field : fields) {
            if (field.getName().equals(key)) num ++;
        }
        return num > 0;
    }

    /**
     * 获取bean中的所有字段，以数组格式返回
     */
    public static <T,V> String[] BeanKeys(Class<V> type){
        Field[] fields = GetField(type);
        String [] names = new String[fields.length];
        int i = 0;
        for (Field field : fields) {
            names[i] = field.getName();
            i++;
        }
        return names;
    }


    /**
     * 过滤前端的请求，map和bean都存在的才会被记录到map集合
     * @param maps 原始数据map
     * @param type 验证类型
     * @throws IllegalAccessException
     */
    public static <T,V> Map<String,Object> FilterMapToMap(Map<String,T> maps , Class<V> type) throws IllegalAccessException {
        Map<String,Object> result = new HashMap<>();
        V v = MapToObject(maps, type);
        Field[] fields = GetField(type);
        String[] strings = GetMapKey(maps);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().isInstance("")){
                int i = 0;
                for (String string : strings) {
                    if (string.equals(field.getName())) i++;
                }
                if(i == 0) continue;

                String o = (String) field.get(v);
                if (o != null && !o.trim().equals("")){
                    result.put(field.getName(),o);
                }
            }
            if (field.getType().isInstance(new Date())){
            }
        }
        return result;
    }
    /**
     * 验证map集合与类中字段是否类型，个数，名称一致
     * @param maps 需要验证的map集合
     * @param type Bean对象
     */
    public static <T,V> boolean MapValiType(Map<String,T> maps , Class<V> type) throws TypeException {
        Field[] declaredFields = GetField(type);
        if (declaredFields == null)
            return false;

        for (Field declaredField : declaredFields) {
            // 如果bean的字段在map中不存在，验证失败
            if (maps.get(declaredField.getName()) == null){
                throw new TypeException("缺少字段 : " + declaredField.getName());
            }
            Class<?> type1 = declaredField.getType();
            T t = maps.get(declaredField.getName());
            // 判断map中的值类型是否可以向上转型成为 类定义的字段类型,验证接口的数据类型是否与定义的bean类型一致
            boolean equals = type1.isAssignableFrom(t.getClass());
            if (!equals) {
                throw new TypeException("字段类型异常 : " + declaredField.getName());
            }
        }
        return true;
    }

    /**
     * 将一个bean的值复制给另一个bean，用户将查询结果映射给返回bean
     * cls1 : 是被复制的bean
     * cls2 ：是复制的bean
     */
    public static <T,V> V BeanToBean(T cls1, V cls2) throws IllegalAccessException {
        Field[] fields1 = GetField(cls1.getClass());

        V newins = null;
        try {
            newins = (V) cls2.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        assert newins != null;
        Field[] fields2 = GetField(newins.getClass());

        assert fields1 != null;
        for (Field field : fields1) {
            field.setAccessible(true);
            String FieldName = field.getName();
            Class<?> type = field.getType();
            Object o = field.get(cls1);
            if (o == null) continue;

            assert fields2 != null;
            for (Field field1 : fields2) {
                field1.setAccessible(true);
                String FieldName2 = field1.getName();
                if (FieldName.equals(FieldName2) && type.isAssignableFrom(field1.getType())){
                    field1.set(newins,o);
                }
            }
        }
        return newins;
    }


}