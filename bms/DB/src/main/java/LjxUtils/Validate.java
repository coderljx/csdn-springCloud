package LjxUtils;

import DB.User;
import LjxEx.TypeException;

import java.util.Map;

public class Validate {

    public Boolean validate(String appid, String appkey, String params) throws TypeException {

        return true;
    }

    public User validate(String appid, String appkey, Integer userid, String params) throws TypeException {

        return new User();
    }

}
