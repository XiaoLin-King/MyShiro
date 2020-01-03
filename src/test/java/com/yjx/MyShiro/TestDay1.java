package com.yjx.MyShiro;

import com.yjx.MyShiro.dao.UserDao;
import com.yjx.MyShiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyShiroApp.class)
public class TestDay1 {
    @Autowired
    private UserDao userDao;

    @Test
    public void test01() {
        User user = userDao.login("张三");
        System.out.println("user = " + user);
    }

    @Test
    public void TestShiro() {
        //获取安全管理器工厂
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //获取安全管理器实例
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        //给安全管理工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        //创建令牌
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "123");
        //主体拿令牌去认证
        subject.login(token);
    }
}
