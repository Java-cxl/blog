package cn.cxl.service.impl;

import cn.cxl.mapper.UserMapper;
import cn.cxl.pojo.Link;
import cn.cxl.pojo.User;
import cn.cxl.service.LinkService;
import cn.cxl.service.UserService;
import cn.cxl.util.MD5Util;
import cn.cxl.util.RandomUtil;
import cn.cxl.util.ReadExcel;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

//    private static Logger logger= Logger.getLogger(LinkServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReadExcel readExcel;

    @Autowired
    private LinkService linkService;

    //用户注册
    public void register(MultipartFile file,User user,String path) {
        String fileName=fileUpload(file,path);
        if(fileName==null){
            user.setPicPath("head.png");
        }else {
            user.setPicPath(fileName);
        }
        String pwd= MD5Util.getPwd(user.getPassword());
        user.setPassword(pwd);
        user.setCreateDate(new Date());
        user.setRole(0);
        userMapper.register(user);
    }

    //用户登录
    public User login(String userName, String userPwd) {
        String pwd=MD5Util.getPwd(userPwd);
        return userMapper.login(userName,pwd);
    }

    //根据用户名查询是否已存在用户
    public int selUserByName(String name) {
        return userMapper.selUserByName(name);
    }

    //查询所有用户
    public List<User> selUserList(int role, String name, int page, int count) {
        int begin=(page-1)*count;
        return userMapper.selUserList(role,name,begin,count);
    }

    //计算用户总页数
    public int selUserPages(int pageCount,int role, String name){
        int count= userMapper.selUserCount(role,name);
        int pages=0;
        if(count%pageCount==0){
            pages=count/pageCount;
        }else{
            pages=count/pageCount+1;
        }
        return pages;
    }

    /**
     * 读取Excel
     * @param path
     * @return
     */
    public String readExcel(String path) {
        Map<String,List<List<String>>> map=readExcel.readExcel(path);
        List<List<String>> lists=map.get("链接");
        for(int i=1;i<lists.size();i++){
            List<String> list=lists.get(i);
            Link link=new Link();
            int row=0;
            for (String col:list){
                switch (row){
                    case 0:
                        link.setTitle(col);
                        break;
                    case 1:
                        link.setLink(col);
                        break;
                    case 2:
                        if(col.equals("文档链接")) {
                            link.setType(2);
                        }else {
                            link.setType(1);
                        }
                        break;
                }
                row++;
            }
            linkService.addLink(link);
        }
        return "success";
    }

    /**
     * 读取Excel
     * @param path
     * @param map
     */
    public void writeExcel(String path, Map<String,List<List<String>>> map, Map<Integer,Integer> colWidthMap){

        readExcel.writeExcel(path,map,colWidthMap);
    }

    //上传文件
    public String fileUpload(MultipartFile file, String path){
        float size=file.getSize();
        //获取文件名
        if(size!=0){
            String fileName=file.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            fileName= RandomUtil.getName()+suffix;
            try {
                file.transferTo(new File(path+fileName));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return fileName;
        }else{
            return null;
        }
    }

    //下载文件
    public void fileDownload(String fileName, HttpServletResponse response,String file_path){

        File file=new File(file_path+fileName);
        if(file.exists()){
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer=new byte[1024];
            FileInputStream fis=null; //文件输入流
            BufferedInputStream bis=null;
            OutputStream os=null; //输出流
            try {
                os=response.getOutputStream();
                fis=new FileInputStream(file);
                bis=new BufferedInputStream(fis);
                int i=bis.read(buffer);
                while(i!=-1){
                    os.write(buffer);
                    i=bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

    /**
     * 导出所有用户数据的定时任务
     */
    @Scheduled(cron = "0 23 22 * * *")
    public void exportExcelJob(){
//        logger.info("-----export LinksExcel job is running...！");
    }

}
