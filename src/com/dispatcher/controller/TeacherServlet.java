package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.TeacherService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/teacher")
public class TeacherServlet {
	//ѧУ����ӿ�
	@Autowired
	private TeacherService teacherService;
       
	/**
	 * ȡ��
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return teacherService.findTeacherMaxCode();
	}
	
	/**
	 * ��ѯ
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search() {
		return teacherService.findTeacher();
	}
	
	/**
	 * ����
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		/*1.У��*/
		JSONObject mainJson = checkAdd(js_code, js_name, js_sex);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.����*/
		mainJson = teacherService.insertTeacher(js_code, js_name, js_sex, js_phone, js_email);
		
		return mainJson;
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	private JSONObject checkAdd(String js_code, String js_name, String js_sex) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// ����
		if (util.isBlank_String(js_code)) {
			errorJson.put("nullFlag", "js_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(js_name)) {
			errorJson.put("nullFlag", "js_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// �Ա�
		if (util.isBlank_String(js_sex)) {
			errorJson.put("nullFlag", "js_sex");
			errorJson.put("msg", "�Ա�Ϊ�գ�");
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
	public Object remove(String js_id) {
		/*У��*/
		JSONObject mainJson = checkDelete(js_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*ɾ��*/
		teacherService.deleteTeacher(js_id);
		
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
	private JSONObject checkDelete(String js_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(js_id)) {
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
	public Object edit(String js_id, String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		JSONObject mainJson = checkEdit(js_id, js_code, js_name, js_sex);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = teacherService.updateTeacher(js_id, js_code, js_name, js_sex, js_phone, js_email);
		return mainJson;
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String js_id, String js_code, String js_name, String js_sex) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(js_id)) {
			errorJson.put("nullFlag", "js_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(js_code)) {
			errorJson.put("nullFlag", "js_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(js_name)) {
			errorJson.put("nullFlag", "js_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//�Ա�
		if (util.isBlank_String(js_sex)) {
			errorJson.put("nullFlag", "js_sex");
			errorJson.put("msg", "�Ա�Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
