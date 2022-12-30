package coderljxTitle.Dao;

import Pojo.DB.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ModuleDao {

    List<Module> queryModule();

    Integer addModule(@Param("module") Module module,
                      @Param("createBy") String createBy);
}
