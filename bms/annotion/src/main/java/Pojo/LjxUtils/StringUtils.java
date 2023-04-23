package Pojo.LjxUtils;

import java.util.List;

public class StringUtils {

    public static Boolean isEmp(String value) {
        return value == null || value.length() == 0 || value.trim().equals("");
    }

    public static Boolean isListEmp(List<?> value) {
        return value == null || value.size() == 0;
    }


    public static Boolean isEmp(Integer value) {
        return value == null;
    }

}
