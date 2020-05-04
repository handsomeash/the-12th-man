package com.ash.io.the12thmanweb.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-28
 */

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {

    /**
     * 允许跨域的 cookie
     * 注意在 allowCredentials(true) ，即允许跨域使用 cookie 的情况下，allowedOrigins() 不能使用通配符 *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    /**
     * 文件上传路径拦截
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:" + "f:/img/");
    }
}
