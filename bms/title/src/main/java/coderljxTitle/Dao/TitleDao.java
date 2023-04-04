package coderljxTitle.Dao;

import Pojo.DB.Title;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TitleDao {


    /**
     * 根据模块id 查询该模块下的所有标签
     * @return
     */
    List<Title> getTitleByModule(@Param("id") Integer id);


    Integer addTitle(@Param("list") List<Title> titleList,
                     @Param("createBy") String createBy);


}
