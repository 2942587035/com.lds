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
 * �л���
 * @author ���
 *
 */
public class ShiroRealm extends AuthorizingRealm {
	private UserService userService;
    
    public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
     *  ��֤��Ϣ����Ҫ����û���¼�� 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

        String yh_code = (String) authcToken.getPrincipal();
        User user = userService.getUserByYH_Code(yh_code);
        
        if (user == null) {
        	throw new UnknownAccountException("�û�������");
		}
        
        SimpleAuthenticationInfo info = null;
        //��ֵ���� begin
        //����Ĳ���Ҫ����Ψһ��;
        /*ByteSource credentialsSalt = ByteSource.Util.bytes(yh_code);
        info = new SimpleAuthenticationInfo(yh_code, user.getYh_password(), credentialsSalt, getName());*/
        //��ֵ���� end
        
        info = new SimpleAuthenticationInfo(user,user.getYh_password(), getName());
        //�û���Ϣ����session
        SecurityUtils.getSubject().getSession().setAttribute("user", user);
        return info;
    }

     /** 
     * ��Ȩ 
     */  
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        User user = (User)principals.getPrimaryPrincipal();

        //�����û�ID��ѯ��ɫ��role�������뵽Authorization�
        Set<String> roles = new HashSet<String>();
        roles.add(user.getYh_type().toString());
        info.setRoles(roles);
        System.out.println(roles);
        //�����û�ID��ѯȨ�ޣ�permission�������뵽Authorization�
        Set<String> permissions = new HashSet<String>();
        permissions.add("admin:insert");
        permissions.add("admin:del");
        System.out.println(permissions.toString());
        info.setStringPermissions(permissions);
        return info;  
    }  
    /**
     * ��յ�ǰ�û�Ȩ����Ϣ
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * ָ��principalCollection ���
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
