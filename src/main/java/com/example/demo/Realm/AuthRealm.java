package com.example.demo.Realm;

import com.example.demo.dao.demo_userMapper;
import com.example.demo.entry.demo_user;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class AuthRealm extends AuthorizingRealm{

    @Autowired
    demo_userMapper userMapper;

    @Autowired
    SessionDAO sessionDAO;

    /*
     授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        demo_user user=(demo_user) principalCollection.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
        List<String> permissions=new ArrayList<>();
        String name= user.getName();
        String url=userMapper.selectPowerByName(name).getUrl();
        System.out.println("url:"+url);
        permissions.add(url);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
    }

    /*
    认证登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken utoken=(UsernamePasswordToken) authenticationToken;//获取用户输入的token
        String username = utoken.getUsername();
        String password=String.valueOf(utoken.getPassword());
        demo_user user=userMapper.selectByName(username);

        if(!user.getPws().equals(password)){
            user=null;
        }
        try {
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            //System.out.println("sessions:"+sessions.toString());
            for(Session session:sessions) {
               // System.out.println("session:"+session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"));
                SimplePrincipalCollection collection= (SimplePrincipalCollection) session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
                Iterator iterator=collection.iterator();
                while (iterator.hasNext()){
                    demo_user u= (demo_user) iterator.next();
                    //System.out.println(u.getName());
                    if(u.getName().equals(username)){
                        System.out.println("该用户已经登陆");
                        user=null;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new SimpleAuthenticationInfo(user, user.getPws(), getName());
            }
}
