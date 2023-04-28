package file.Mgr;

import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.ResponseParse;
import Pojo.LjxUtils.StringUtils;
import file.MinIo.Minio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class FileMgr {
    private final Logger mylog = LoggerFactory.getLogger(FileMgr.class);

    @Resource
    private Minio minio;
    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;


    private String getUserName(User user) {
        if (user.getId() == null || user.getId() <= 0) {
            throw new TypeException("E000001_02");
        }

        String userInfo = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        String name = ResponseParse.getUserName(userInfo, "name");
        return name;
    }



    public void saveFile() {
        System.out.println("t1");
    }

    /**
     * 获取一个文件的预览
     * @param bucket
     * @param name
     * @return
     * @throws Exception
     */
    public String getPreUrl(String bucket, String name) throws Exception {
        if (StringUtils.isEmp(bucket) || StringUtils.isEmp(name)) {
            throw new TypeException("E0000004_001");
        }
        return minio.getPreUrl(bucket, name);
    }


    /**
     * 删除一个文件信息
     * @param user
     * @param bucket
     * @param name
     * @throws Exception
     */
    public void delFile(User user,String bucket, String name) throws Exception {
        if (user.getId() == null || user.getId() <= 0) {
            throw new TypeException("E000001_02");
        }
        if (StringUtils.isEmp(bucket) || StringUtils.isEmp(name)) {
            throw new TypeException("E0000004_001");
        }
        minio.deleteFile(bucket, name);

    }







}
