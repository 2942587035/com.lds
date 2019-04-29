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
	 * ���ݿͻ��˴������û����������ѯ���ݿ⣬��������ظ��ͻ���
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
	 * У��
	 */
    private JSONObject checkMenuPower(String yh_type) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		if (util.isBlank_String(yh_type)) {
			errorJson.put("message", "�û�����Ϊ��");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
    
    private JSONArray menuPower(String yh_type) {
		//��ѯ��ǰ�û������Ƿ������Ψһ
		return menuPowerService.findMenuPowerByGN_Type(yh_type);
	}
}
