package cn.cxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication
//@ServletComponentScan("cn.cxl.util")
//public class BlogApplication extends SpringBootServletInitializer{
//
//	public static void main(String[] args) {
//		SpringApplication.run(BlogApplication.class, args);
//	}
//
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(BlogApplication.class);
//	}
//}

@SpringBootApplication
@ServletComponentScan("cn.cxl.util")
@EnableScheduling
public class BlogApplication{

	public static void main(String[] args) {
		System.out.println("123测试");
		SpringApplication.run(BlogApplication.class, args);
	}

}
