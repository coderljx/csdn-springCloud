package file.Dao;


import file.DB.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileDao {

    Integer saveFile(@Param("fileInfo") FileInfo fileInfo,
                     @Param("id") Integer userId);


}
