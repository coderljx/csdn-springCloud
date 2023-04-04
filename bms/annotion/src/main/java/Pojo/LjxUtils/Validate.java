package Pojo.LjxUtils;


import Pojo.DB.User;
import Pojo.SearchArgsMap;

public class Validate {


    public Boolean validate(String appid, String appkey, String sign,  String data) {

        return true;
    }

    public User validate(String appid, Integer userid,String sign, String data) throws NoSuchFieldException, IllegalAccessException {

//        this.validate(appid, userid,sign);

        return this.validate(appid, userid,data);
    }

    public User validate(String appid, Integer userid,String data) throws NoSuchFieldException, IllegalAccessException{

        User user = new User();
        user.setId(userid);
        user.setAppId(Integer.valueOf(appid));
        user.setSearchArgsMap(this.parseData(data));
        return user;
    }

    public SearchArgsMap validate(String appid, String data) throws NoSuchFieldException, IllegalAccessException {
        return parseData(data);
    }


    public void validate(String appid, String sign,String data)  {

    }


    private SearchArgsMap parseData(String data) throws NoSuchFieldException, IllegalAccessException {
        return new SearchArgsMap(data);
    }


    public void validate(String appid)  {

    }

}
