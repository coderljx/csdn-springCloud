package Pojo.DB;

import Pojo.SearchArgsMap;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Base {
    private String name;
    private String phone;
    private String address;
    private String password;
    private Integer roleid;
    private String userLoginKey; // 当前用户的key，存在redis 用来检查是否登录
    private Integer appId;

    private String avatar; // 用户的头像

    private SearchArgsMap searchArgsMap;


    public User(){
    }


    public User(Integer id){
         super.setId(id);
    }

}
