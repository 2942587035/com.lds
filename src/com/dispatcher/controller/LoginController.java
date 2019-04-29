package com.dispatcher.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.LoginService;
import com.business.service.inter.UserService;
import com.entity.User;

import enums.EnumPub.Login;
import enums.EnumPub.Passworld;
import enums.EnumPub.UserCode;
import net.sf.json.JSONObject;
import utils.Md5Utils;
import utils.util;

/**
 * ��¼ҵ������ �ӱ��л�ȡ�û�������û���������룬 ��ȡ���ݿ��ѯ�Ƿ���ڸ��û� ����������ظ��ͻ���
 * 
 * @author lds
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;

	/**
	 * ���ݿͻ��˴������û����������ѯ���ݿ⣬��������ظ��ͻ���
	 * @param request
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public JSONObject login(String yh_code, String yh_password) {
        JSONObject mainJson = checkLogin(yh_code, yh_password);
        if (!mainJson.isEmpty()) {
			return mainJson;
		}
        
        /*mainJson = loginExecute(yh_code, yh_password);*/
        
        Subject subject = SecurityUtils.getSubject();
        //�Ѿ���֤ͨ�����߼�ס��
        if (subject.isAuthenticated() || subject.isRemembered()) {
        	User user = userService.getUserByYH_Code(yh_code);
            JSONObject successJson = new JSONObject();
            successJson.put("message", Login.Success.getMessage());
     		successJson.put("yh_id", user.getYh_id());
     		successJson.put("yh_code", user.getYh_code());
     		successJson.put("yh_name", user.getYh_name());
     		successJson.put("yh_password", user.getYh_password());
     		successJson.put("yh_type", user.getYh_type());
     		mainJson.put("success", successJson);
     		return mainJson;
		}

        // ����token
        UsernamePasswordToken token = new UsernamePasswordToken(yh_code, yh_password);
        token.setRememberMe(true);
        // ��֤
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {//�û�������
            e.printStackTrace();
        } catch (LockedAccountException e) {//�û�������
			e.printStackTrace();
		} catch (IncorrectCredentialsException e) {//�������
        	e.printStackTrace();
		}  catch (AuthenticationException e) {//δ֪����
        	e.printStackTrace();
		} 
		
        System.out.println(subject.isAuthenticated());
        User user = userService.getUserByYH_Code(yh_code);
        JSONObject successJson = new JSONObject();
        successJson.put("message", Login.Success.getMessage());
		successJson.put("yh_id", user.getYh_id());
		successJson.put("yh_code", user.getYh_code());
		successJson.put("yh_name", user.getYh_name());
		successJson.put("yh_password", user.getYh_password());
		successJson.put("yh_type", user.getYh_type());
		mainJson.put("success", successJson);
		return mainJson;
	}
	
	/**
	 * ��¼���
	 */
    private JSONObject checkLogin(String yh_code, String yh_password) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
    	/**********1.�û������Ƿ�Ϊ��**********/
		//�û�����Ϊ��
		if (util.isBlank_String(yh_code)) {
			errorJson.put("message", UserCode.IsBlank.getMessage());
			//�ͻ���ʶ���û����뻹������ı�ʶ
			errorJson.put("flag", 0);
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		/**********2.�����Ƿ�Ϊ��**********/
		//����Ϊ��
		if (util.isBlank_String(yh_password)) {
			errorJson.put("message", Passworld.IsBlank.getMessage());
			errorJson.put("flag", 1);
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
    
	/**
	 * ��¼
	 * @param request
	 * @return
	 */
	private JSONObject loginExecute(String yh_code, String yh_password) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		//����
	    yh_password = Md5Utils.stringMD5(yh_password);
	    
		//��ѯ��ǰ�û������Ƿ������Ψһ
		List<?> list = loginService.findUserByYH_Code(yh_code);
		
		//�����ڣ�����ʾ�û����벻����
		if (list == null || list.isEmpty()) {
			errorJson.put("message", UserCode.NotFound.getMessage());
			//�ͻ���ʶ���û����뻹������ı�ʶ
			errorJson.put("flag", 0);
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count = list.size();
		if (count == 1) {//���ڣ���ֻ��һ���������ѯ���û��������Ƿ���ȷ
			@SuppressWarnings("unchecked")
			Map<String, Object> user = (Map<String, Object>) list.get(0);
			String yh_passwordDB = (String) user.get("yh_password");
			if (yh_password.equals(yh_passwordDB)) {//������ȷ
				successJson.put("message", Login.Success.getMessage());
				successJson.put("yh_id", user.get("yh_id"));
				successJson.put("yh_code", user.get("yh_code"));
				successJson.put("yh_name", user.get("yh_name"));
				successJson.put("yh_password", yh_passwordDB);
				successJson.put("yh_type", user.get("yh_type"));
				mainJson.put("success", successJson);
				
				return mainJson;
			} else {//���벻��ȷ
				errorJson.put("message", Passworld.Error.getMessage());
				errorJson.put("flag", 1);
				mainJson.put("error", errorJson);
				
				return mainJson;
			}
		} else {//���ڣ������ظ�,��ʾ�����ظ��û�����
			errorJson.put("message", UserCode.Repeat.getMessage());
			//�ͻ���ʶ���û����뻹������ı�ʶ
			errorJson.put("flag", 0);
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
	}
	
	/**
	 * �ǳ�
	 */
	@RequestMapping("/logout.do")
	@ResponseBody
	public JSONObject logout() {
		SecurityUtils.getSubject().logout();
		JSONObject success = new JSONObject();
		success.put("msg", "�ǳ��ɹ�");
		return success;
	}
}
