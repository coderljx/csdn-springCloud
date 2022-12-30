package Pojo.DB;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Base {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String password;
    private Integer roleid;
}
