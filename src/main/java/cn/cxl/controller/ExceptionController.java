package cn.cxl.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ExceptionController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH,produces = {MediaType.TEXT_HTML_VALUE})
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 400){
            return "/error/400";
        }else if(statusCode == 404){
            return "/error/404";
        }else if(statusCode == 500){
            return "/error/500";
        }else{
            return "";
        }
    }
}
