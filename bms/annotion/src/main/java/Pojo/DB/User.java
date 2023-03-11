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

    private SearchArgsMap searchArgsMap;
}
