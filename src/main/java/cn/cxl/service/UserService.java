package cn.cxl.service;

import cn.cxl.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

public interface UserService {

    //用户注册
    public void register(MultipartFile file,User user,String path);

    //用户登录
    public User login(String userName,String userPwd);

    //根据用户名查询是否已存在用户
    public int selUserByName(String name);

    //查询所有用户
    public List<User> selUserList(int role,String name,int page,int count);

    //查询用户总数
    public int selUserPages(int pageCount,int role, String name);

    //读取excel表格
    public String readExcel(String path);

    //下载文件
    public void fileDownload(String fileLink, HttpServletResponse response, String image_path);
}
