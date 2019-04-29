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
	 * ���ݿͻ��˴������û����������ѯ���ݿ⣬��������ظ��ͻ���
	 */
	@RequestMapping("/edit.do")
	@ResponseBody
	public Object edit(String yh_id, String newPassword) {
	    JSONObject result = checkModifyYH_Password(yh_id, newPassword);
	    if (!result.isEmpty()) {
			return result;
		}
	    
	    /*2.�޸ĵ�ǰ�û�����*/
	    modifyPasswordService.modifyYH_Password(yh_id, newPassword);
	    
	    result.put("msg", "�����޸ĳɹ���");
	    result.put("flag", 0);
		return result;
	}
	
	/**
	 * �޸�����ǰУ���û�ID���������Ƿ����
	 * @return
	 */
	private JSONObject checkModifyYH_Password(String yh_id, String newPassword) {
		JSONObject result = new JSONObject();

	    if (util.isBlank_String(yh_id)) {
	    	result.put("msg", "��ǰ�û������ڣ�");
			return result;
		}
	    if (util.isBlank_String(newPassword)) {
	    	result.put("msg", Passworld.IsBlank.getMessage());
			return result;
		}
	    
	    return result;
	}
}
