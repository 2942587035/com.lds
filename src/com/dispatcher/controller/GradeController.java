package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.GradeService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/grade")
public class GradeController {
	//����ӿ�
	@Autowired
	private GradeService gradeService;
       
	/**
	 * ȡ��
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return gradeService.findGradeMaxCode();
	}
	
	/**
	 * ��ѯ
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search() {
		return gradeService.findGrade();
	}
	
	/**
	 * ����
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String nj_code, String nj_name) {
		/*1.У��*/
		JSONObject mainJson = checkAdd(nj_code, nj_name);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.����*/
		mainJson = gradeService.insertGrade(nj_code, nj_name);
		
		return mainJson;
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	private JSONObject checkAdd(String nj_code, String nj_name) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// ����
		if (util.isBlank_String(nj_code)) {
			errorJson.put("nullFlag", "nj_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(nj_name)) {
			errorJson.put("nullFlag", "nj_name");
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
	public Object remove(String nj_id) {
		/*У��*/
		JSONObject mainJson = checkDelete(nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*ɾ��*/
		gradeService.deleteGrade(nj_id);
		
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
	private JSONObject checkDelete(String nj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(nj_id)) {
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
	public Object edit(String nj_id, String nj_code, String nj_name) {
		JSONObject mainJson = checkEdit(nj_id, nj_code, nj_name);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = gradeService.updateGrade(nj_id, nj_code, nj_name);
		return mainJson;
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String nj_id, String nj_code, String nj_name) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(nj_code)) {
			errorJson.put("nullFlag", "nj_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(nj_name)) {
			errorJson.put("nullFlag", "nj_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
