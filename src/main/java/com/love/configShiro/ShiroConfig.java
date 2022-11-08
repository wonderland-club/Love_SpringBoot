package com.love.configShiro;


import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ShiroConfig {
    // Shiro自带的过滤器，可以在这里配置拦截页面
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired DefaultWebSecurityManager securityManager) {

        //初始化roFilter工程类
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 通过SecurityManager认证和授权，初始化SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 管理权限（有权限才能访问）
        Map<String, String> filterMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();

        // Shiro过滤器
        filterMap.put("/api/**", "anon");
        // 拦截
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 当用户没权限时访问接口
        shiroFilterFactoryBean.setUnauthorizedUrl("api/me");
        return shiroFilterFactoryBean;
    }


    // 认证、授权
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Autowired UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // Realm类这个类来帮securityManager查数据库的信息
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    // 自定义Realm，需要查询相关权限信息的时候，Realm帮我们处理
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }


    //    配置支持注解
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager); // 这里需要注入 SecurityManger 安全管理器
        return authorizationAttributeSourceAdvisor;
    }


}
