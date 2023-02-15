package Pojo.LjxUtils;

import Pojo.DB.Coco;
import Pojo.LjxEx.TypeException;

import java.io.*;
import java.util.Properties;

public class UUID {

    private static ThreadLocal<Properties> threadLocal = new ThreadLocal<>();

    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 根据message信息去配置文件查询信息
     * @param message
     */
    public static String getCNException(String message) {
        Properties properties = loadResource("/config.properties");
        return properties != null ? properties.getProperty(message) : null;
    }

    /**
     * 查询英文提示，目前系统仅支持中文
     * @param message
     * @return
     */
    public static String getENException(String message) {
        Properties properties = loadResource("/config.properties");
        return properties != null ? properties.getProperty(message) : null;
    }


    private static Properties loadResource(String name) {
        if (threadLocal.get() != null) {
            return threadLocal.get();
        }
        InputStream resourceAsStream = UUID.class.getResourceAsStream(name);
        try {
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(resourceAsStream, "UTF-8"));
            Properties properties = new Properties();
            properties.load(inputStream);
            // 一般出错了都是主线程去执行，所以只需要主线程初始化一次，下次就不需要读文件了
            threadLocal.set(properties);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 如果是自定义抛出的异常，捕获后处理
     * @return
     */
    public static Coco ExceptionFill(TypeException exception) {
        String message = exception.getMessage();
        Coco coco = Coco.ok;
        coco.message = getCNException(message);
        coco.code = -101;
        return coco;
    }




}
