package com.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.business.service.inter.UserService;
import com.entity.User;
/**
 * 有缓存
 * @author 李东升
 *
 */
public class ShiroRealm extends AuthorizingRealm {
	private UserService userService;
    
    public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
     *  认证信息，主要针对用户登录， 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

        String yh_code = (String) authcToken.getPrincipal();
        User user = userService.getUserByYH_Code(yh_code);
        
        if (user == null) {
        	throw new UnknownAccountException("用户不存在");
		}
        
        SimpleAuthenticationInfo info = null;
        //盐值加密 begin
        //这里的参数要给个唯一的;
        /*ByteSource credentialsSalt = ByteSource.Util.bytes(yh_code);
        info = new SimpleAuthenticationInfo(yh_code, user.getYh_password(), credentialsSalt, getName());*/
        //盐值加密 end
        
        info = new SimpleAuthenticationInfo(user,user.getYh_password(), getName());
        //用户信息放入session
        SecurityUtils.getSubject().getSession().setAttribute("user", user);
        return info;
    }

     /** 
     * 授权 
     */  
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        User user = (User)principals.getPrimaryPrincipal();

        //根据用户ID查询角色（role），放入到Authorization里。
        Set<String> roles = new HashSet<String>();
        roles.add(user.getYh_type().toString());
        info.setRoles(roles);
        System.out.println(roles);
        //根据用户ID查询权限（permission），放入到Authorization里。
        Set<String> permissions = new HashSet<String>();
        permissions.add("admin:insert");
        permissions.add("admin:del");
        System.out.println(permissions.toString());
        info.setStringPermissions(permissions);
        return info;  
    }  
    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
