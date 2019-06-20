package cn.cxl.controller;

import cn.cxl.pojo.Link;
import cn.cxl.pojo.User;
import cn.cxl.service.LinkService;
import cn.cxl.service.UserService;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private LinkService linkService;

    @Value("${EXCEL_PATH}")
    public String EXCELPATH;

    //跳向后台
    @RequestMapping("/mgr/manager")
    public String toManager(String linkPage, String userPage, String title,
                            String name, String role, String type, HttpServletRequest request){
        int linkPager=1;
        int userPager=1;
        int count=10;
        int role1=-1;
        int type1=-1;
        if(userPage!=null && userPage!=""){
            userPager=Integer.parseInt(userPage);
        }
        if(linkPage!=null && linkPage!=""){
            linkPager=Integer.parseInt(linkPage);
        }
        if(role!=null && role!=""){
            role1=Integer.parseInt(role);
        }
        if(type!=null && type!=""){
            type1=Integer.parseInt(type);
        }
        List<Link> linkList = linkService.selLinkList(type1,title,linkPager,count);
        List<User> userList = userService.selUserList(role1,name,userPager,count);
        Map map=new HashMap();
        map.put("userPage",userPager);
        map.put("userPages",userService.selUserPages(count,role1,name));
        map.put("linkPage",linkPager);
        map.put("linkPages",linkService.selLinkPages(count,type1,title));
        request.setAttribute("linkList",linkList);
        request.setAttribute("userList",userList);
        request.setAttribute("page",map);
        return "back/manager";
    }

    //添加链接
    @RequestMapping("/mgr/addLink")
    @ResponseBody
    public String addLink(Link link){

        boolean flag=true;
        try{
            linkService.addLink(link);
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        Map map=new HashMap();
        map.put("result",flag);
        return JSONArray.toJSONString(map);
    }

    //删除链接
    @RequestMapping("/mgr/delLink")
    @ResponseBody
    public String delLink(Integer id){
        boolean flag=true;
        try{
            linkService.delLink(id);
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        Map map=new HashMap();
        map.put("result",flag);
        return JSONArray.toJSONString(map);
    }

    //查询链接
    @RequestMapping("/mgr/selLink")
    @ResponseBody
    public String selLink(String linkPage,String title,String type){
        int type1=-1;
        if(type!=null && type!=""){
            type1=Integer.parseInt(type);
        }
        int linkPager=Integer.parseInt(linkPage);
        List<Link> linkList = linkService.selLinkList(type1,title,linkPager,10);
        Map map=new HashMap();
        map.put("result",linkList);
        return JSONArray.toJSONString(map);
    }

    //跳向批量导入数据页面
    @RequestMapping("/mgr/toImportLinks")
    public String toAddManyLink(){
        return "back/importLinks";
    }

    @RequestMapping("/readExcel")
    @ResponseBody
    public String readExcel(){
        String path="D://导入数据.xls";
        String text=userService.readExcel(path);
        System.out.println(text);
        return text;
    }

    @RequestMapping("/writeExcel")
    @ResponseBody
    public String writeExcel(){
//        List<String> colList=new ArrayList<String>();
//        colList.add("作者");
//        colList.add("书名");
//        List<String> colList1=new ArrayList<String>();
//        colList1.add("唐家三少");
//        colList1.add("琴帝");
//        List<String> colList2=new ArrayList<String>();
//        colList2.add("辰东");
//        colList2.add("遮天");
//        List<String> colList3=new ArrayList<String>();
//        colList3.add("天蚕土豆");
//        colList3.add("斗破苍穹");
//        List<List<String>> rowList=new ArrayList<List<String>>();
//        rowList.add(colList);
//        rowList.add(colList1);
//        rowList.add(colList2);
//        rowList.add(colList3);
//
//        List<String> colList4=new ArrayList<String>();
//        colList4.add("歌曲名称");
//        colList4.add("歌手");
//        List<String> colList5=new ArrayList<String>();
//        colList5.add("想象之中");
//        colList5.add("许嵩");
//        List<String> colList6=new ArrayList<String>();
//        colList6.add("告白气球");
//        colList6.add("周杰伦");
//        List<List<String>> rowList2=new ArrayList<List<String>>();
//        rowList2.add(colList4);
//        rowList2.add(colList5);
//        rowList2.add(colList6);
//
//        Map<String,List<List<String>>> map=new HashMap<>();
//        map.put("小说表",rowList);
//        map.put("歌曲表",rowList2);
//
//        userService.writeExcel("D://test.xls",map);
        return "success";
    }

    //下载定时任务生成的链接的Excel表格
    @RequestMapping("/mgr/downloadLinksExcel")
    public String downloadLinkExcel(HttpServletResponse response){
        userService.fileDownload("Links.xls",response,EXCELPATH);
        return  null;
    }
}
