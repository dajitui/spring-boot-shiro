package com.example.demo;

import com.example.demo.Realm.AuthRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

// 等价于beans
@Configuration
public class ShiroConfig {
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter( DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager((org.apache.shiro.mgt.SecurityManager) manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/jsp/login.jsp*", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/loginuser", "anon");
        filterChainDefinitionMap.put("/getGifCode", "anon");
        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean
    public AuthRealm myShiroRealm(){
        AuthRealm anthRealm = new AuthRealm();
        return anthRealm;
    }


    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(manager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * cookie对象;
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public EnterpriseCacheSessionDAO sessionDao(){
        EnterpriseCacheSessionDAO sessionDAO=new EnterpriseCacheSessionDAO();
        JavaUuidSessionIdGenerator generator=new JavaUuidSessionIdGenerator();
        sessionDAO.setSessionIdGenerator(generator);
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return sessionDAO;
    }

    @Bean
    public DefaultWebSessionManager manager(){
        DefaultWebSessionManager manager=new DefaultWebSessionManager();
        manager.setSessionValidationInterval(1800000);
        manager.setGlobalSessionTimeout(1800000);
        manager.setSessionDAO(sessionDao());
        manager.setDeleteInvalidSessions(true);
        manager.setSessionValidationSchedulerEnabled(true);
        SimpleCookie cookie=new SimpleCookie();
        cookie.setName("v_v-s-dajitui");
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(true);
        manager.setSessionIdCookie(cookie);
        return manager;
    }


}
