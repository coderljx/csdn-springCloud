package com.codeljx.Dao;

import DB.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * form user where is_delete = 0")
    List<User> queryUser();

    @Select("select * form user where is_delete = 0 and id = #{id}")
    User querUserById(@Param("id") Integer id);

    /**
     * 新增用户
     * @param user
     * @return
     */
    Integer insertUser(@Param("user") User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Integer updateUser(@Param("user") User user,
                       @Param("modyfiBy") String modyfiBy);


    User queryUserFormPwd(@Param("user") User user);


}
