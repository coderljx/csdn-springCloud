package coderljxTitle.Dao;

import Pojo.DB.TextDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TextDetailDao {

    // 新增一个文章点赞等信息
    Integer addTextDetail(TextDetail textDetail);

    // update一个文章点赞等信息
    Integer updateTextDetail(TextDetail textDetail);


    Integer delTextDetail(Integer id,String modify,Integer userid);

}
