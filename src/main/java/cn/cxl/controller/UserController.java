package cn.cxl.controller;

import cn.cxl.pojo.User;
import cn.cxl.service.UserService;
import cn.cxl.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${IMG_PATH}")
    private String IMAGE_PATH;

    @Value("${IMG_URL}")
    private String IMAGE_URL;

    //定义一个user对象用来写入session信息
    private static User userSession;

    //注册
    @RequestMapping("/user/register")
    public String register(MultipartFile file, User user){
        userService.register(file,user,IMAGE_PATH);
        return "redirect:/toLogin";
    }

    //验证是否登陆成功
    @RequestMapping("/user/login")
    @ResponseBody
    public String login(String name, String pwd, HttpSession session){
        User user=userService.login(name,pwd);
        userSession=user;
        return JSON.toJSONString(user);
    }

    //登陆成功，写入session会话并跳向主页
    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpSession session){
        session.setAttribute("user",userSession);
        session.setAttribute("image_url",IMAGE_URL);
        return "redirect:/index";
    }

    //注册的时候验证用户名是否已经存在
    @RequestMapping("/selUserByName")
    @ResponseBody
    public String selUserByName(String name){
        System.out.println(name);
        int num=userService.selUserByName(name);
        Map map= new HashMap();
        map.put("result",num);
        return JSON.toJSONString(map);
    }

    //跳向查看大图片页面
    @RequestMapping("/sys/toPicDetail")
    public String toPicDetail(String picLink,HttpServletRequest request){
        request.setAttribute("picLink", picLink);
        return "pre/picDetail";
    }

    //下载图片
    @RequestMapping("/sys/picDownload")
    public String picDownload(String fileLink,HttpServletResponse response){
        String fileName=fileLink.substring(fileLink.lastIndexOf('/')+1);
        userService.fileDownload(fileName,response,IMAGE_PATH);
        return null;
    }


}
