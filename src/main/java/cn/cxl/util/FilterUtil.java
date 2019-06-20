package cn.cxl.util;

import cn.cxl.pojo.User;
import org.springframework.boot.web.servlet.server.Session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * 过滤器工具类
 */

public class FilterUtil implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponseWrapper wrapper=new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        User user=(User)request.getSession().getAttribute("user");
        if((request.getRequestURI().indexOf("/mgr")!=-1 ||
                request.getRequestURI().indexOf("/usr")!=-1 ||
                request.getRequestURI().indexOf("/sys")!=-1) &&
                user==null){
            wrapper.sendRedirect("/toLogin");
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
