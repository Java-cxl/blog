package cn.cxl.service.impl;

import cn.cxl.mapper.LinkMapper;
import cn.cxl.pojo.Link;
import cn.cxl.service.LinkService;
import cn.cxl.util.DateChangeUtil;
import cn.cxl.util.ReadExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LinkServiceImpl implements LinkService {

//    private static Logger logger= Logger.getLogger(LinkServiceImpl.class);

    DateChangeUtil dcu=new DateChangeUtil();

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private ReadExcel readExcel;

    @Value("${EXCEL_PATH}")
    public String EXCELPATH;

    //查询链接
    public List<Link> selLinkList(int type ,String title,int page,int count) {
        int begin=(page-1)*count;
        return linkMapper.selLinkList(type,title,begin,count);
    }

    //新增链接
    public int addLink(Link link) {
        link.setCreateDate(new Date());
        return linkMapper.addLink(link);
    }

    //修改链接
    public int updateLink(Link link) {
        return linkMapper.updateLink(link);
    }

    //删除链接
    public int delLink(int id) {
        return linkMapper.delLink(id);
    }

    //计算链接总页数
    public int selLinkPages(int pageCount, int type, String title) {
        int count= linkMapper.selLinkCount(type,title);
        int pages=0;
        if(count%pageCount==0){
            pages=count/pageCount;
        }else{
            pages=count/pageCount+1;
        }
        return pages;
    }

    /**
     * 每天导出所有链接的定时任务
     */

    @Scheduled(cron = "30 05 00 * * *")
    public void exportExcelJob(){
//        logger.info("-----export LinksExcel job is running...! 乱码");
        long beginTime=System.currentTimeMillis();
        List<Link> lists=selLinkList(-1,null,-1,-1);
        Map<String,List<List<String>>> map=new HashMap<String,List<List<String>>>();
        List<List<String>> tableList=new ArrayList<List<String>>();
        //创建表格的首行并赋值
        List<String> firstRow=new ArrayList<>();
        firstRow.add("链接名称");
        firstRow.add("链接地址");
        firstRow.add("链接类型");
        firstRow.add("新建日期");
        tableList.add(firstRow);
        for (Link link:lists) {
            List<String> rowList=new ArrayList<>(); //相当于Excel的行
            rowList.add(link.getTitle());
            rowList.add(link.getLink());
            if(link.getType()==1){
                rowList.add("常用链接");
            }else {
                rowList.add("文档链接");
            }
            rowList.add(dcu.getStringByDate(link.getCreateDate()));
            tableList.add(rowList);
        }
        map.put("链接表",tableList);
        //设置列的宽度
        Map<Integer,Integer> colWidthMap=new HashMap<>();
        colWidthMap.put(0,256*26);
        colWidthMap.put(1,256*36);
        colWidthMap.put(2,256*12);
        colWidthMap.put(3,256*23);
        //调用写入Excel的方法
        readExcel.writeExcel(EXCELPATH+"Links.xls",map,colWidthMap);
//        logger.info("-----export LinksExcel job is end! elapsed time is "+String.valueOf(System.currentTimeMillis()-beginTime));
    }
}
