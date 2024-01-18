package com.example.demo.common.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager ){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        bean.setSecurityManager(defaultWebSecurityManager);

        /*
            添加shiro的内置过滤器
            anon:无需认证即可访问
            authc: 认证了才能访问
            user: 拥有记住我功能才能用
            perms: 拥有对某个资源的权限才能访问
            role: 拥有某个角色才能访问
        */

        Map<String, String> filterMap = new LinkedHashMap<>();
        /*

        filterMap.put("/test","authc");
        filterMap.put("/abb","anon");
        filterMap.put("/login","anon");
        filterMap.put("http://localhost:8080/#/index","authc");
        bean.setFilterChainDefinitionMap(filterMap);

        bean.setLoginUrl("http://localhost:8080");
         */


        return bean;
    }

    @Bean(name="securityManager")
    public  DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;

    }

    @Bean
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }


    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
    }
}
