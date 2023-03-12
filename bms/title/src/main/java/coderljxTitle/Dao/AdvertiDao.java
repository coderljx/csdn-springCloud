package coderljxTitle.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdvertiDao {

    List<String> getAdvLinks();

}
