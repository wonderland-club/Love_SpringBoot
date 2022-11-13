package com.love.configShiro;


import com.love.Mapper.UserMapper;
import com.love.module.User;
import com.love.module.UserExample;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;
    UserExample userExample;
    UserExample.Criteria criteria;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
//        为登录用户添加权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();//拿到user对象
        //设置当前用户的角色
        info.addRole(currentUser.getRole());

        return info;
    }

    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        userExample = new UserExample();
        criteria = userExample.createCriteria();

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        criteria.andEmailEqualTo(token.getUsername());
        List<User> userList = userMapper.selectByExample(userExample);

        if (userList.size() > 0) {
            return new SimpleAuthenticationInfo(userList.get(0), userList.get(0).getPassword(), getName());
        }

        return null;
    }


}
