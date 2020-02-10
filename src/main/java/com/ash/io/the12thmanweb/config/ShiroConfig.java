package com.ash.io.the12thmanweb.config;

import com.ash.io.the12thmanweb.shiro.ShiroFilter;
import com.ash.io.the12thmanweb.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-18
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

//        Map<String, String > filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        Map<String, Filter> customizedFilter = new HashMap<>();
//        // 设置自定义过滤器名称为 url
//        customizedFilter.put("url", getShiroFilter());

//        filterChainDefinitionMap.put("/api/authentication", "authc");
//        filterChainDefinitionMap.put("/api/menu", "authc");
//        filterChainDefinitionMap.put("/api/admin/**", "authc");
//        // 对管理接口的访问启用自定义拦截（url 规则），即执行 ShiroFilter 中定义的过滤方法
//        filterChainDefinitionMap.put("/api/**", "url");

        // 启用自定义过滤器
//        shiroFilterFactoryBean.setFilters(customizedFilter);
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 创建securityManager,注意方法返回的是DefaultWebSecurityManager类型
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        //设置密码校验规则
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 增加获取过滤器的方法，注意这里不能使用 @Bean
     * 这个也是过滤器，ShiroFilterFactoryBean 也是过滤器，当他们都出现的时候，默认的什么 anno,authc 过滤器就失效了。
     * @return
     */
    public ShiroFilter getShiroFilter(){
        return new ShiroFilter();
    }

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        //credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
}
