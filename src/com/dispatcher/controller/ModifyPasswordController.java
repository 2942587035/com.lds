package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.ModifyPasswordService;

import enums.EnumPub.Passworld;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/modifyPassword")
public class ModifyPasswordController {
	@Autowired
	private ModifyPasswordService modifyPasswordService;
       

	/**
	 * 根据客户端传来的用户编码密码查询数据库，将结果返回给客户端
	 */
	@RequestMapping("/edit.do")
	@ResponseBody
	public Object edit(String yh_id, String newPassword) {
	    JSONObject result = checkModifyYH_Password(yh_id, newPassword);
	    if (!result.isEmpty()) {
			return result;
		}
	    
	    /*2.修改当前用户密码*/
	    modifyPasswordService.modifyYH_Password(yh_id, newPassword);
	    
	    result.put("msg", "密码修改成功！");
	    result.put("flag", 0);
		return result;
	}
	
	/**
	 * 修改密码前校验用户ID和新密码是否存在
	 * @return
	 */
	private JSONObject checkModifyYH_Password(String yh_id, String newPassword) {
		JSONObject result = new JSONObject();

	    if (util.isBlank_String(yh_id)) {
	    	result.put("msg", "当前用户不存在！");
			return result;
		}
	    if (util.isBlank_String(newPassword)) {
	    	result.put("msg", Passworld.IsBlank.getMessage());
			return result;
		}
	    
	    return result;
	}
}
