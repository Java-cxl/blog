package cn.cxl.mapper;

import cn.cxl.pojo.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LinkMapper {

    //查询首页右侧的链接
    public  List<Link> selLinkList(@Param("type") int type,@Param("title") String title,
                                   @Param("begin")int begin,@Param("end") int end);

    //新增链接
    public int addLink(Link link);

    //修改链接
    public int updateLink(Link link);

    //删除链接
    public int delLink(int id);

    //查询链接总数
    public int selLinkCount(@Param("type") int type,@Param("title") String title);
}
