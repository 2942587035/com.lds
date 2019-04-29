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
 * 登录业务处理器 从表单中获取用户输入的用户编码和密码， 读取数据库查询是否存在该用户 并将结果返回给客户端
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
	 * 根据客户端传来的用户编码密码查询数据库，将结果返回给客户端
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
        //已经认证通过或者记住我
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

        // 创建token
        UsernamePasswordToken token = new UsernamePasswordToken(yh_code, yh_password);
        token.setRememberMe(true);
        // 认证
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {//用户不存在
            e.printStackTrace();
        } catch (LockedAccountException e) {//用户被锁定
			e.printStackTrace();
		} catch (IncorrectCredentialsException e) {//密码错误
        	e.printStackTrace();
		}  catch (AuthenticationException e) {//未知错误
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
	 * 登录检查
	 */
    private JSONObject checkLogin(String yh_code, String yh_password) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
    	/**********1.用户编码是否为空**********/
		//用户编码为空
		if (util.isBlank_String(yh_code)) {
			errorJson.put("message", UserCode.IsBlank.getMessage());
			//客户端识别用户编码还是密码的标识
			errorJson.put("flag", 0);
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		/**********2.密码是否为空**********/
		//密码为空
		if (util.isBlank_String(yh_password)) {
			errorJson.put("message", Passworld.IsBlank.getMessage());
			errorJson.put("flag", 1);
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
    
	/**
	 * 登录
	 * @param request
	 * @return
	 */
	private JSONObject loginExecute(String yh_code, String yh_password) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		//密码
	    yh_password = Md5Utils.stringMD5(yh_password);
	    
		//查询当前用户编码是否存在且唯一
		List<?> list = loginService.findUserByYH_Code(yh_code);
		
		//不存在，则提示用户编码不存在
		if (list == null || list.isEmpty()) {
			errorJson.put("message", UserCode.NotFound.getMessage());
			//客户端识别用户编码还是密码的标识
			errorJson.put("flag", 0);
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count = list.size();
		if (count == 1) {//存在，且只有一条数据则查询此用户的密码是否正确
			@SuppressWarnings("unchecked")
			Map<String, Object> user = (Map<String, Object>) list.get(0);
			String yh_passwordDB = (String) user.get("yh_password");
			if (yh_password.equals(yh_passwordDB)) {//密码正确
				successJson.put("message", Login.Success.getMessage());
				successJson.put("yh_id", user.get("yh_id"));
				successJson.put("yh_code", user.get("yh_code"));
				successJson.put("yh_name", user.get("yh_name"));
				successJson.put("yh_password", yh_passwordDB);
				successJson.put("yh_type", user.get("yh_type"));
				mainJson.put("success", successJson);
				
				return mainJson;
			} else {//密码不正确
				errorJson.put("message", Passworld.Error.getMessage());
				errorJson.put("flag", 1);
				mainJson.put("error", errorJson);
				
				return mainJson;
			}
		} else {//存在，数据重复,提示存在重复用户编码
			errorJson.put("message", UserCode.Repeat.getMessage());
			//客户端识别用户编码还是密码的标识
			errorJson.put("flag", 0);
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
	}
	
	/**
	 * 登出
	 */
	@RequestMapping("/logout.do")
	@ResponseBody
	public JSONObject logout() {
		SecurityUtils.getSubject().logout();
		JSONObject success = new JSONObject();
		success.put("msg", "登出成功");
		return success;
	}
}
