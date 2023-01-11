package coderljxTitle.Dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "userService")
public interface kk {

    @RequestMapping("userService/api/user/a")
    String koko();

}
