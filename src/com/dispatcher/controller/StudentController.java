package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.GradeClassService;
import com.business.service.inter.StudentService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/student")
public class StudentController {
	//ѧ������ӿ�
	@Autowired
	private StudentService studentService;
	@Autowired
	private GradeClassService gradeClassService;
       
	/**
	 * ȡ��
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return studentService.findStudentMaxCode();
	}
	
	/**
	 * ��ѯ�༶/�꼶
	 * @return
	 */
	@RequestMapping("/searchGradeClass.do")
	@ResponseBody
	public Object searchGradeClass() {
		return gradeClassService.findClassByGrade();
	}
	
	/**
	 * ��ѯ
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search(String nj_id, String bj_id) {
		/*1.У��*/
		JSONObject mainJson = checkSearch(nj_id, bj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		return studentService.findStudentByNJ_IdAndBJ_Id(nj_id, bj_id);
	}
	
	/**
	 * ��ѯǰУ��
	 * @return
	 */
	private JSONObject checkSearch(String nj_id, String bj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// �꼶ID
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "�꼶IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		// �༶ID
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "�༶IDΪ�գ�");
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
	public Object add(String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id) {
		/*1.У��*/
		JSONObject mainJson = checkAdd(xs_code, xs_name, xs_sex, nj_id, bj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.����*/
		mainJson = studentService.insertStudent(xs_code, xs_name, xs_sex, xs_phone, xs_email, nj_id, bj_id);
		
		return mainJson;
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	private JSONObject checkAdd(String xs_code, String xs_name, String xs_sex, String nj_id, String bj_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// ����
		if (util.isBlank_String(xs_code)) {
			errorJson.put("nullFlag", "xs_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(xs_name)) {
			errorJson.put("nullFlag", "xs_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// �Ա�
		if (util.isBlank_String(xs_sex)) {
			errorJson.put("nullFlag", "xs_sex");
			errorJson.put("msg", "�Ա�Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// �꼶id
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "�꼶idΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// �༶id
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "�༶idΪ�գ�");
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
	public Object remove(String xs_id) {
		/*У��*/
		JSONObject mainJson = checkDelete(xs_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*ɾ��*/
		studentService.deleteStudent(xs_id);
		
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
	private JSONObject checkDelete(String xs_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(xs_id)) {
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
	public Object edit(String xs_id, String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id) {
		JSONObject mainJson = checkEdit(xs_id, xs_code, xs_name, xs_sex, nj_id, bj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = studentService.updateStudent(xs_id, xs_code, xs_name, xs_sex, xs_phone, xs_email, nj_id, bj_id);
		return mainJson;
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String xs_id, String xs_code, String xs_name, String xs_sex, String nj_id, String bj_id) {
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(xs_id)) {
			//Json����
			JSONObject mainJson = new JSONObject();
			//ʧ������Json����
			JSONObject errorJson = new JSONObject();
			errorJson.put("nullFlag", "xs_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return checkAdd(xs_code, xs_name, xs_sex, nj_id, bj_id);
	}
}
