package com.yjx.MyShiro.shiroConf;

import com.yjx.MyShiro.realm.MyWebRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroWenFilter {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        //获取安全管理器工厂
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/img/*", "anon");
        map.put("/user/login", "anon");
        map.put("/index.jsp", "anon");
        map.put("/index1.jsp", "user");
        map.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(map);
        factoryBean.setLoginUrl("/main/login.jsp");
        return factoryBean;
    }

    //安全管理器
    @Bean
    public DefaultWebSecurityManager securityManager(MyWebRealm myWebRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myWebRealm);
        //记住我管理器
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        //定义cookie
        SimpleCookie cookie = new SimpleCookie("aaa");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        //将cookie设置进记住我管理器
        rememberMeManager.setCookie(cookie);
        //定义会话管理器

        //rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUsOKTA3Kprsdg=="));


        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1 * 60 * 60 * 1000);
        //将cookie交给会话管理器
        sessionManager.setSessionIdCookie(cookie);
        securityManager.setSessionManager(sessionManager);
        //将记住我管理器摄者进安全管理器
        securityManager.setRememberMeManager(rememberMeManager);
        MemoryConstrainedCacheManager cache = new MemoryConstrainedCacheManager();
        securityManager.setCacheManager(cache);
        return securityManager;
    }

    @Bean
    public MyWebRealm myWebRealm(CredentialsMatcher hashedCredentialsMatcher) {
        MyWebRealm myWebRealm = new MyWebRealm();
        myWebRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myWebRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
}
