package com.codeljxUser.Dao;


import Pojo.DB.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from user where is_delete = 0")
    List<User> queryUser();

    @Select("select * from user where is_delete = 0 and id = #{id}")
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


    User queryUserFormPhone(@Param("user") User user);


    Integer deleteUser(@Param("userid") Integer userid,
                         @Param("modyfiBy") String modyfiBy);

}
