package file.Dao;


import org.apache.ibatis.annotations.Param;

public interface FileDao {

    Integer saveFile();

    Integer delFile();

}
