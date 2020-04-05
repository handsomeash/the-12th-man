package com.ash.io.the12thmanweb.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义shiro过滤器
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-18
 */
@Slf4j
public class ShiroFilter extends PathMatchingFilter {
//    @Autowired
//    AdminPermissionService adminPermissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 放行 options 请求
        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

//        if (null==adminPermissionService) {
//            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);
//        }

        String requestAPI = getPathWithinApplication(request);
        log.info("访问接口：" + requestAPI);

        Subject subject = SecurityUtils.getSubject();


        //判断当前会话对应的用户是否登录，如果未登录直接 false
        log.info("是否记住我登陆：" + subject.isRemembered());
        log.info("是否普通登陆：" + subject.isAuthenticated());
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            log.info("用户需要登录");
            return false;
        } else {
            log.info("用户已登录");
            return true;
        }

    }

}
