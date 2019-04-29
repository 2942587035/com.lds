package com.dispatcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.ClassService;
import com.business.service.inter.GradeService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;
@Controller
@RequestMapping("/class")
public class ClassController {
	//�༶����ӿ�
	@Autowired
	private ClassService classService;
	//�꼶����ӿ�
	@Autowired
	private GradeService gradeService;
	/**
	 * ȡ��
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public String getMaxCode() {
		return classService.findClassMaxCode();
	}
	
	/**
	 * ��ѯ�꼶
	 * @return
	 */
	@RequestMapping("/searchGrade.do")
	@ResponseBody
	public List<?> searchGrade() {
		return gradeService.findGrade_NameAndId();
	}
	
	/**
	 * ��ѯ
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search(String nj_id) {
		/*1.У��*/
		JSONObject mainJson = checkSearch(nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*2��ѯ*/
		return classService.findClassByNJ_Id(nj_id);
	}
	
	/**
	 * ��ѯǰУ��
	 * @return
	 */
	private JSONObject checkSearch(String nj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// �꼶ID
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
	
	/**
	 * ����
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String bj_code, String bj_name, String nj_id) {
		/*1.У��*/
		JSONObject mainJson = checkAdd(bj_code, bj_name, nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.����*/
		return classService.insertClass(bj_code, bj_name, nj_id);
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	private JSONObject checkAdd(String bj_code, String bj_name, String nj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// ����
		if (util.isBlank_String(bj_code)) {
			errorJson.put("nullFlag", "bj_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(bj_name)) {
			errorJson.put("nullFlag", "bj_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// �꼶
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "�꼶Ϊ�գ�");
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
	public Object remove(String bj_id) {
		/*У��*/
		JSONObject mainJson = checkDelete(bj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*ɾ��*/
		classService.deleteClass(bj_id);
		
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
	private JSONObject checkDelete(String bj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(bj_id)) {
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
	public Object edit(String bj_id, String bj_code, String bj_name, String nj_id) {
		JSONObject mainJson = checkEdit(bj_id, bj_code, bj_name, nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = classService.updateClass(bj_id, bj_code, bj_name, nj_id);
		return mainJson;
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String bj_id, String bj_code, String bj_name, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(bj_code)) {
			errorJson.put("nullFlag", "bj_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(bj_name)) {
			errorJson.put("nullFlag", "bj_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//�꼶
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "�꼶Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
