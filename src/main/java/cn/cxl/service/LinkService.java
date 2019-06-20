package cn.cxl.service;

import cn.cxl.pojo.Link;

import java.util.List;

public interface LinkService {

    //查询首页右侧的链接
    public List<Link> selLinkList(int type ,String title,int page,int count);

    //新增链接
    public int addLink(Link link);

    //修改链接
    public int updateLink(Link link);

    //删除链接
    public int delLink(int id);

    //计算链接总页数
    public int selLinkPages(int pageCount,int type,String title);
}
