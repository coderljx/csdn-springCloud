package coderljxTitle.Dao;

import Pojo.DB.Advertisement;
import Pojo.DB.Text;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TextDao {

    /**
     * 获取用户的所有文章信息
     * @param userid
     * @return
     */
    List<Text> getUserText(@Param("userid") Integer userid,
                           @Param("id") Integer id);

    /**
     * 用户新增一个文章
     * @param text
     * @return
     */
    Integer addUserText(@Param("text") Text text);


    /**
     * 删除用户的文章，软删除
     * @param id
     * @param modifyBy
     * @return
     */
    Integer deleteUserText(@Param("id")Integer id,
                           @Param("modifyBy")String modifyBy);

    /**
     * 获取广告信息
     * @return
     */
    List<Advertisement> getAdv(@Param("list") List<Integer> imgId);


    /**
     * 获取用户关注的人发布的文章
     * @param userid
     * @return
     */
    List<Text> getFollowedText(@Param("userid") Integer userid);

}
