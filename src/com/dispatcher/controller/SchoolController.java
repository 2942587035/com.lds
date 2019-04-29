package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.SchoolService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/school")
public class SchoolController {
	//ѧУ����ӿ�
	@Autowired
	private SchoolService schoolService;
       
	/**
	 * ȡ��
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return schoolService.findSchoolMaxCode();
	}
	
	/**
	 * ��ѯ
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search() {
		return schoolService.findSchool();
	}
	
	/**
	 * ����
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String xx_code, String xx_name, String xx_type, String xx_remark) {
		/*1.У��*/
		JSONObject mainJson = checkAdd(xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.����*/
		mainJson = schoolService.insertSchool(xx_code, xx_name, xx_type, xx_remark);
		
		return mainJson;
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	private JSONObject checkAdd(String xx_code, String xx_name, String xx_type) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// ����
		if (util.isBlank_String(xx_code)) {
			errorJson.put("nullFlag", "xx_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(xx_name)) {
			errorJson.put("nullFlag", "xx_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(xx_type)) {
			errorJson.put("nullFlag", "xx_type");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}

		return mainJson;
	}
	
	/**
	 * ɾ��
	 * @return
	 */
	@RequestMapping("/remove.do")
	@ResponseBody
	public Object remove(String xx_id) {
		/*У��*/
		JSONObject mainJson = checkDelete(xx_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*ɾ��*/
		schoolService.deleteSchool(xx_id);
		
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		successJson.put("msg", Message.RemoveSuccess.getMessage());
		mainJson.put("success", successJson);
		return mainJson;
	}
	
	/**
	 * ɾ��ǰУ��
	 * @return
	 */
	private JSONObject checkDelete(String xx_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(xx_id)) {
			errorJson.put("msg", "��Ҫɾ�������ݲ����ڣ���ˢ�����ݣ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
	
	/**
	 * �޸�
	 * @return
	 */
	@RequestMapping("/edit.do")
	@ResponseBody
	public Object edit(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark) {
		JSONObject mainJson = checkEdit(xx_id, xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = schoolService.updateSchool(xx_id, xx_code, xx_name, xx_type, xx_remark);
		return mainJson;
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String xx_id, String xx_code, String xx_name, String xx_type) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(xx_id)) {
			errorJson.put("nullFlag", "xx_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(xx_code)) {
			errorJson.put("nullFlag", "xx_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(xx_name)) {
			errorJson.put("nullFlag", "xx_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(xx_type)) {
			errorJson.put("nullFlag", "xx_type");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
