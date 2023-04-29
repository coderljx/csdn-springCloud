package file.Api;

import Pojo.Consumer.Consumet;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import file.Mgr.FileMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RequestMapping("/file")
@RestController
public class FileService extends Validate {
    private final Logger mylog = LoggerFactory.getLogger(FileService.class);


    @Resource
    private FileMgr fileMgr;


    @PostMapping("/saveFile/{appid}")
    @LogEs(url = "file/saveFile", dec = "上传文件")
    public Response<?> saveFile(@PathVariable("appid") String appid,
                                @RequestParam(value = "userid", required = false) Integer userid,
                                @RequestParam(value = "bucket", required = false) String bucket,
                                @RequestParam(value = "file", required = false) MultipartFile[] files) {

        return Consumet.Logic(() -> {
            User validate = validate(appid, userid);


            return null;
        });
    }


    @GetMapping("/getFile/{appid}")
    @LogEs(url = "file/getFile", dec = "获取文件访问路径")
    public Response<?> getFile(@PathVariable("appid") String appid,
                               @RequestParam(value = "userid", required = false) Integer userid,
                               @RequestParam(value = "bucket", required = false) String bucket,
                               @RequestParam(value = "name", required = false) String name) {

        return Consumet.Logic(() -> {
            User validate = validate(appid, userid);

            String preUrl = fileMgr.getPreUrl(bucket, name);

            return preUrl;
        });
    }


    @DeleteMapping("/delFile/{appid}")
    @LogEs(url = "file/delFile", dec = "删除文件")
    public Response<?> delFile(@PathVariable("appid") String appid,
                               @RequestParam(value = "userid", required = false) Integer userid,
                               @RequestParam(value = "bucket", required = false) String bucket,
                               @RequestParam(value = "name", required = false) String name) {

        return Consumet.Logic(() -> {
            User validate = validate(appid, userid);

            fileMgr.delFile(validate, bucket, name);

            return null;
        });
    }


}
