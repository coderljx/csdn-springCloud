package com.codeljxUser.Dao;


import Pojo.DB.Follow;
import Pojo.DB.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select id,name,phone from user where is_delete = 0")
    List<User> queryUser();

    @Select("select id,name,phone from user where is_delete = 0 and id = #{id}")
    User querUserById(@Param("id") Integer id);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    Integer insertUser(@Param("user") User user);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    Integer updateUser(@Param("user") User user,
                       @Param("modyfiBy") String modyfiBy);


    /**
     * 根据用户名和密码查询用户信息
     *
     * @param user
     * @return
     */
    User queryUserFormPwd(@Param("user") User user);


    /**
     * 根据手机号，查询用户的信息
     *
     * @param user
     * @return
     */
    User queryUserFormPhone(@Param("user") User user);


    /**
     * 删除一个用户
     *
     * @param userid
     * @param modyfiBy
     * @return
     */
    Integer deleteUser(@Param("userid") Integer userid,
                       @Param("modyfiBy") String modyfiBy);


    /**
     * 关注一个用户
     *
     * @param userId
     * @param followId
     * @param modyfiBy
     * @return
     */
    Integer followUser(@Param("userId") Integer userId,
                       @Param("followId") Integer followId,
                       @Param("modyfiBy") String modyfiBy);


    /**
     * 根据用户和关注人id， 查看当前该用户是否关注过他
     *
     * @param userid
     * @param followId
     * @return
     */
    Follow queryFollow(@Param("userid") Integer userid,
                       @Param("followId") Integer followId);


    /**
     * 取关用户
     *
     * @param userId
     * @param followIds
     * @return
     */
    Integer unFollowUser(@Param("userId") Integer userId,
                         @Param("list") List<String> followIds);


    /**
     * 获取当前用户的关注列表
     * @param userId
     */
    List<Follow> getUserFollow(@Param("userId") Integer userId);

}
