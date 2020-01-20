package com.ash.io.the12thmanweb.shiro;

import com.ash.io.the12thmanweb.entity.User;
import com.ash.io.the12thmanweb.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Realm 负责从数据源中获取数据并加工后传给 SecurityManager
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-18
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 执行授权逻辑
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 执行认证逻辑，即根据 token 中的用户名从数据库中获取密码、盐等并返回
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        String username = token.getPrincipal().toString();
        UsernamePasswordToken loginToken = (UsernamePasswordToken) token;
        //通过账号找到用户
        User user = userService.getByUsername(loginToken.getUsername());

        // 如果用户存在，则校验密码
        if (user != null) {
            //账号
            String username = user.getUsername();
            //这里的密码是加密后的密码
            String password = user.getPassword();
            //realmName：当前realm对象的name，调用父类的getName()方法即可
            String realmName = this.getName();
            //salt: 盐值,用账号生成盐值
            ByteSource salt = ByteSource.Util.bytes(username);
            //这里的第一个参数，可以通过subject.getPrincipal()获取到
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, salt, realmName);

            return info;
        } else {
            return null;
        }
    }
}
