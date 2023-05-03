package file.Mgr;

import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.ResponseParse;
import Pojo.LjxUtils.StringUtils;
import Pojo.LjxUtils.UUID;
import file.DB.FileInfo;
import file.DB.UserFileVo;
import file.Dao.FileDao;
import file.Dao.UserFileDao;
import file.MinIo.Minio;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


@Service
public class FileMgr {
    private final Logger mylog = LoggerFactory.getLogger(FileMgr.class);

    @Resource
    private Minio minio;
    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;
    @Resource
    private FileDao fileDao;
    @Resource
    private UserFileDao userFileDao;


    private String getUserName(User user) {
        if (user.getId() == null || user.getId() <= 0) {
            throw new TypeException("E000001_02");
        }

        String userInfo = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        String name = ResponseParse.getUserName(userInfo, "name");
        return name;
    }


    /**
     * 用户上传头像
     *
     * @param user
     * @param bucket
     * @param multipartFile
     */
    @SneakyThrows
    public void saveFile(User user, String bucket, MultipartFile multipartFile) {
        if (user.getId() == null || user.getId() <= 0) throw new TypeException("E000001_02");

        String fileName = UUID.getUUID();
        minio.uploadFile(
                multipartFile.getInputStream(),
                multipartFile.getOriginalFilename(),
                bucket,
                fileName);

        // 文件上传到minio后 会直接将文件与用户的关联信息保存到数据库中
        FileInfo fileInfo = new FileInfo(bucket, fileName);
        fileDao.saveFile(fileInfo, user.getId());
        userFileDao.addUserFile(user.getId(), fileInfo.getId());
    }

    /**
     * 获取一个文件的预览
     *
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
     *
     * @param user
     * @param bucket
     * @param name
     * @throws Exception
     */
    public void delFile(User user, String bucket, String name) throws Exception {
        if (user.getId() == null || user.getId() <= 0) {
            throw new TypeException("E000001_02");
        }
        if (StringUtils.isEmp(bucket) || StringUtils.isEmp(name)) {
            throw new TypeException("E0000004_001");
        }
        minio.deleteFile(bucket, name);

    }


    public String getUserFile(User user) throws Exception {
        if (user.getId() == null || user.getId() <= 0) {
            throw new TypeException("E000001_02");
        }
        List<UserFileVo> userFileInfo = userFileDao.getUserFileInfo(user.getId());
        if (StringUtils.isListEmp(userFileInfo)) return "";

        UserFileVo userFileVo = userFileInfo.get(0);
        String preUrl = this.getPreUrl(userFileVo.getBucket(), userFileVo.getFileName());

        return preUrl;
    }


}
