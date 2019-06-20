package cn.cxl.mapper;

import cn.cxl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.*;

@Mapper
public interface UserMapper {
    //用户注册
    public int register(User user);

    //用户登录
    public User login(@Param("username") String userName,@Param("pwd") String userPwd);

    //根据用户名查询是否已存在用户
    public int selUserByName(String name);

    //查询所有用户
    public List<User> selUserList(@Param("role") int role,@Param("name") String name,
                            @Param("begin") int begin,@Param("end") int end);

    //查询用户总数
    public int selUserCount(@Param("role") int role,@Param("name") String name);

}
