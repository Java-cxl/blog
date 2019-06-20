package cn.cxl.controller;

import cn.cxl.service.LinkService;
import cn.cxl.service.UserService;
import cn.cxl.service.impl.LinkServiceImpl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
public class HelloController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserService userService;

    @Value("${IMG_PATH}")
    private String IMAGE_PATH;

    //首页展示
    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        //设置从第�?页开始查�?20条数�?
        System.out.println("000成都市");
        List list1=linkService.selLinkList(1,null,1,20);
        List list2=linkService.selLinkList(2,null,1,20);
        request.setAttribute("linkListTop",list1);
        request.setAttribute("linkListBottom",list2);
        return "pre/main";
    }

    //跳向注册页面
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "pre/register";
    }

    //跳向登录页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "pre/login";
    }

    //�?出登�?
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/index";
    }
    

    
    //跳转nginx教程的详情页
    @RequestMapping("/sys/nginx.html")
    public String toNginx(){
    	return "jiaocheng/nginx.html";
    }
    
    //跳转dubbo教程的详情页
    @RequestMapping("/sys/dubbo.html")
    public String toDubbo(){
    	return "jiaocheng/dubbo.html";
    }
    
    //跳转redis教程的详情页
    @RequestMapping("/sys/redis.html")
    public String toRedis(){
    	return "jiaocheng/redis.html";
    }
    
    //跳转zookeeper教程的详情页
    @RequestMapping("/sys/zookeeper.html")
    public String toZookeeper(){
    	return "jiaocheng/zookeeper.html";
    }
    
    //MyLover跳转页面
    @RequestMapping("/toLover")
    public String toMyLover(){
    	return "pre/lover";
    }


}
