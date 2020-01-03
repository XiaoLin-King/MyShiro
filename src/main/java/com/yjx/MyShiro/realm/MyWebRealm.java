package com.yjx.MyShiro.realm;

import com.yjx.MyShiro.dao.UserDao;
import com.yjx.MyShiro.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class MyWebRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("===============开始授权==================");

        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        //根据身份信息去查角色，根据角色去查权限  user:add user:update
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<String> list = Arrays.asList("user:add", "user:update");

        authorizationInfo.addRole("super");

        authorizationInfo.addStringPermissions(list);

        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("===============开始认证==================");

        String principal = (String) authenticationToken.getPrincipal();

        User user = userDao.login(principal);

        if (user != null) {

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());

            return authenticationInfo;
        }

        return null;
    }
}
