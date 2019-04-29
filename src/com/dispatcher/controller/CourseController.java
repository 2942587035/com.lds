package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.CourseService;
import com.business.service.inter.GradeService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/course")
public class CourseController {
	//�༶����ӿ�
	@Autowired
	private CourseService courseService;
	@Autowired
	//�꼶����ӿ�
	private GradeService gradeService;
       
	/**
	 * ȡ��
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return courseService.findCourseMaxCode();
	}
	
	/**
	 * ��ѯ�꼶
	 * @return
	 */
	@RequestMapping("/searchGrade.do")
	@ResponseBody
	public Object searchGrade() {
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
		return courseService.findCourseByNJ_Id(nj_id);
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
	public Object add(String kc_code, String kc_name, String nj_id) {
		/*1.У��*/
		JSONObject mainJson = checkAdd(kc_code, kc_name, nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.����*/
		mainJson = courseService.insertCourse(kc_code, kc_name, nj_id);
		
		return mainJson;
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	private JSONObject checkAdd(String kc_code, String kc_name, String nj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// ����
		if (util.isBlank_String(kc_code)) {
			errorJson.put("nullFlag", "kc_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(kc_name)) {
			errorJson.put("nullFlag", "kc_name");
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
	public Object remove(String kc_id) {
		/*У��*/
		JSONObject mainJson = checkDelete(kc_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*ɾ��*/
		courseService.deleteCourse(kc_id);
		
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
	private JSONObject checkDelete(String kc_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(kc_id)) {
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
	public Object edit(String kc_id, String kc_code, String kc_name, String nj_id) {
		JSONObject mainJson = checkEdit(kc_id, kc_code, kc_name, nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = courseService.updateCourse(kc_id, kc_code, kc_name, nj_id);
		return mainJson;
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String kc_id, String kc_code, String kc_name, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(kc_id)) {
			errorJson.put("nullFlag", "kc_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(kc_code)) {
			errorJson.put("nullFlag", "kc_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(kc_name)) {
			errorJson.put("nullFlag", "kc_name");
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
