package Pojo.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "fileService")
public interface FileOpenFeign {

    /**
     * 获取用户的头像信息
     * @param appid
     * @param userid
     * @return
     */
    @RequestMapping("fileService/api/file/getUserFile/{appid}")
    String getUserAvactor(@PathVariable("appid") Integer appid,
                          @RequestParam(value = "userid", required = false) Integer userid);


}
