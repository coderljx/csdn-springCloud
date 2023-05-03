package file.Dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作用户头像与文件的对应关系的
 */
@Mapper
public interface UserFileDao {

    /**
     * 新增一条用户文件数据
     * @param userId 用户id
     * @param fileId 文件id
     * @return
     */
    Integer addUserFile(@Param("userId") Integer userId,
                        @Param("fileId") Integer fileId);

    /**
     * 获取这个用户的头像信息，会返回该用户所有的图像信息
     * @param userId
     * @return
     */
    List<file.DB.UserFileVo> getUserFileInfo(@Param("userId") Integer userId);



    Integer delUserFile(@Param("userId") Integer userId);



}
