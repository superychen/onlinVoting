package com.cqyc.shixun.config;

import com.cqyc.shixun.domain.SysUser;
import com.cqyc.shixun.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author:
 * @Date:
 */
@Slf4j
public class ShiroRleam  extends AuthorizingRealm {


    @Autowired
    private ISysUserService userService;

    @Autowired
    private HttpSession session;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1:把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;

        //2:从UsernamePasswordToken中取出username
        String loginName = utoken.getUsername();
        String password = new String(utoken.getPassword());
        //3:调用数据库的方法，从数据库查询username对应的用户记录
        SysUser user = userService.login(loginName);

        log.debug("用户姓名：{}", user.getLoginName());

        if(!user.getLoginName().equals(loginName)){
            throw new AuthenticationException("账号输入错误！！！");
        }

        //Principal ：认证的实体信息，可以是username，也可以是数据表对应的用户的实体类队形
        Object principal = loginName;
        //Credential:密码
        Object Credentials=user.getPasswrod();
        String realmName = getName();
        //给MD5加密的算法加盐
        ByteSource credentialsSalt = ByteSource.Util.bytes(loginName);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,Credentials,credentialsSalt,realmName);
        session.setAttribute("user",user);
        return info;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1:从PrincipalCollection中获取登录用户的信息
        String name = (String) principalCollection.getPrimaryPrincipal();
        //2:利用登录的用户的信息来显示用户当前用户的角色或权限(可能要查询数据库)
        Set<String> roles = new HashSet<>();
        roles.add("user");

        if("admin".equals(name)){
            roles.add("admin");
        }
        //3：创建SimpleAuthorizationInfo,并设置roles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        //4:返回SimpleAuthorizationInfo对象
        return null;
    }




}
