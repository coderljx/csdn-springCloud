package coderljxTitle.Dao;

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
    List<Text> getUserText(@Param("userid") Integer userid);

    /**
     * 用户新增一个文章
     * @param text
     * @return
     */
    Integer addUserText(@Param("text") Text text);


    Integer deleteUserText(@Param("id")Integer id,
                           @Param("modifyBy")String modifyBy);

}
