package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.MenuPowerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/menuPower")
public class MenuPowerController {
	@Autowired
	private MenuPowerService menuPowerService;
	
	/**
	 * 根据客户端传来的用户编码密码查询数据库，将结果返回给客户端
	 * @param request
	 */
	@RequestMapping("/menuPower.do")
	@ResponseBody
	public Object menuPowerSearch(String yh_type) {
        JSONObject mainJson = checkMenuPower(yh_type);
        if (!mainJson.isEmpty()) {
			return mainJson;
		}
        
		return menuPower(yh_type);
	}
	
	/**
	 * 校验
	 */
    private JSONObject checkMenuPower(String yh_type) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		if (util.isBlank_String(yh_type)) {
			errorJson.put("message", "用户类型为空");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
    
    private JSONArray menuPower(String yh_type) {
		//查询当前用户编码是否存在且唯一
		return menuPowerService.findMenuPowerByGN_Type(yh_type);
	}
}
