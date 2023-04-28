package file.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import file.Mgr.FileMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RequestMapping ("/file")
@RestController
public class FileService extends Validate {
    private final Logger mylog = LoggerFactory.getLogger(FileService.class);


    @Resource
    private FileMgr fileMgr;


    @PostMapping ("/saveFile/{appid}")
    @LogEs (url = "file/saveFile", dec = "上传文件")
    public Response<?> saveFile(@PathVariable ("appid") String appid,
                                @RequestParam (value = "userid", required = false) Integer userid,
                                @RequestParam (value = "bucket", required = false) String bucket,
                                @RequestParam (value = "file", required = false) MultipartFile[] files) {
        Coco coco = Coco.InitCoco;
        coco.message = "success";
        coco.code = 200;
        Response<?> response = null;
        try {

            User validate = validate(appid, userid);


        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @GetMapping ("/getFile/{appid}")
    @LogEs (url = "file/getFile", dec = "获取文件访问路径")
    public Response<?> getFile(@PathVariable ("appid") String appid,
                               @RequestParam (value = "userid", required = false) Integer userid,
                               @RequestParam (value = "bucket", required = false) String bucket,
                               @RequestParam (value = "name", required = false) String name) {
        Coco coco = Coco.InitCoco;
        coco.message = "success";
        coco.code = 200;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid);


        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }

}
