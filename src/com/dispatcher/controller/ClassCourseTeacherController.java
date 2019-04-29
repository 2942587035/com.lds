package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.ClassCourseTeacherService;
import com.business.service.inter.GradeClassCourseService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;
@Controller
@RequestMapping("/classCourseTeacher")
public class ClassCourseTeacherController {
	//�����ʦ
	@Autowired
	private ClassCourseTeacherService classCourseTeacherService;
	//��ѯ�꼶���༶���γ̷���
	@Autowired
	private GradeClassCourseService gradeClassCourseService;
       
	/**
	 * ��ѯ�༶�Ϳγ�/�꼶
	 * @return
	 */
	@RequestMapping("/searchGradeClassCourse.do")
	@ResponseBody
	public Object searchGradeClassCourse() {
		return gradeClassCourseService.findClassAndCourseByGrade();
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
			return mainJson.toString();
		}
		
		return classCourseTeacherService.findClassCourseTeacherByNJ_Id(nj_id);
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
	 * ����
	 * @return
	 */
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(String nj_id, String bj_id, String kc_id, String js_id) {
		/*1.У��*/
		JSONObject mainJson = checkAdd(nj_id, bj_id, kc_id, js_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.����*/
		mainJson = classCourseTeacherService.insertClassCourseTeacher(nj_id, bj_id, kc_id, js_id);
		
		return mainJson;
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	private JSONObject checkAdd(String nj_id, String bj_id, String kc_id, String js_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// �꼶
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "�꼶Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// �༶
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "�༶Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// �γ�
		if (util.isBlank_String(kc_id)) {
			errorJson.put("nullFlag", "kc_id");
			errorJson.put("msg", "�γ�Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ��ʦ
		if (util.isBlank_String(js_id)) {
			errorJson.put("nullFlag", "js_id");
			errorJson.put("msg", "��ʦΪ�գ�");
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
	public Object remove(String nbkj_id) {
		/*У��*/
		JSONObject mainJson = checkDelete(nbkj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*ɾ��*/
		classCourseTeacherService.deleteClassCourseTeacher(nbkj_id);
		
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
	private JSONObject checkDelete(String nbkj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(nbkj_id)) {
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
	public Object edit(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id) {
		JSONObject mainJson = checkEdit(nbkj_id, nj_id, bj_id, kc_id, js_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = classCourseTeacherService.updateClassCourseTeacher(nbkj_id, nj_id, bj_id, kc_id, js_id);
		return mainJson;
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(nbkj_id)) {
			errorJson.put("nullFlag", "nbkj_id");
			errorJson.put("msg", "IDΪ�գ�");
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
		//�༶
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "�༶Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//�γ�
		if (util.isBlank_String(kc_id)) {
			errorJson.put("nullFlag", "kc_id");
			errorJson.put("msg", "�γ�Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//��ʦ
		if (util.isBlank_String(js_id)) {
			errorJson.put("nullFlag", "js_id");
			errorJson.put("msg", "��ʦΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
