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

    private SearchArgsMap searchArgsMap;
}
