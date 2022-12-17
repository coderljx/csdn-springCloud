package LjxUtils;

public class StringUtils {

    public static Boolean isEmp(String value) {
        return value == null || value.length() == 0 || value.trim().equals("");
    }

}
