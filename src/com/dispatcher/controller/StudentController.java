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
	//学生服务接口
	@Autowired
	private StudentService studentService;
	@Autowired
	private GradeClassService gradeClassService;
       
	/**
	 * 取号
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return studentService.findStudentMaxCode();
	}
	
	/**
	 * 查询班级/年级
	 * @return
	 */
	@RequestMapping("/searchGradeClass.do")
	@ResponseBody
	public Object searchGradeClass() {
		return gradeClassService.findClassByGrade();
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search(String nj_id, String bj_id) {
		/*1.校验*/
		JSONObject mainJson = checkSearch(nj_id, bj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		return studentService.findStudentByNJ_IdAndBJ_Id(nj_id, bj_id);
	}
	
	/**
	 * 查询前校验
	 * @return
	 */
	private JSONObject checkSearch(String nj_id, String bj_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		/* 1.判空 nullFlag:告诉客户端谁为空 repeat:告诉客户端谁重复 */
		// 年级ID
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "年级ID为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		// 班级ID
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "班级ID为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
	
	/**
	 * 增加
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id) {
		/*1.校验*/
		JSONObject mainJson = checkAdd(xs_code, xs_name, xs_sex, nj_id, bj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.插入*/
		mainJson = studentService.insertStudent(xs_code, xs_name, xs_sex, xs_phone, xs_email, nj_id, bj_id);
		
		return mainJson;
	}
	
	/**
	 * 增加前校验
	 * @return
	 */
	private JSONObject checkAdd(String xs_code, String xs_name, String xs_sex, String nj_id, String bj_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		/* 1.判空 nullFlag:告诉客户端谁为空 repeat:告诉客户端谁重复 */
		// 编码
		if (util.isBlank_String(xs_code)) {
			errorJson.put("nullFlag", "xs_code");
			errorJson.put("msg", "编码为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 名称
		if (util.isBlank_String(xs_name)) {
			errorJson.put("nullFlag", "xs_name");
			errorJson.put("msg", "名称为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 性别
		if (util.isBlank_String(xs_sex)) {
			errorJson.put("nullFlag", "xs_sex");
			errorJson.put("msg", "性别为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 年级id
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "年级id为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 班级id
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "班级id为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}

		return mainJson;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/remove.do")
	@ResponseBody
	public Object remove(String xs_id) {
		/*校验*/
		JSONObject mainJson = checkDelete(xs_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*删除*/
		studentService.deleteStudent(xs_id);
		
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		successJson.put("msg", Message.RemoveSuccess.getMessage());
		mainJson.put("success", successJson);
		return mainJson;
	}
	
	/**
	 * 删除前校验
	 * @return
	 */
	private JSONObject checkDelete(String xs_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(xs_id)) {
			errorJson.put("msg", "您要删除的数据不存在！请刷新数据！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
	
	/**
	 * 修改
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
	 * 编辑前校验
	 * @return
	 */
	private JSONObject checkEdit(String xs_id, String xs_code, String xs_name, String xs_sex, String nj_id, String bj_id) {
		/*1.判空 nullFlag:告诉客户端谁为空  repeat:告诉客户端谁重复*/
		if (util.isBlank_String(xs_id)) {
			//Json容器
			JSONObject mainJson = new JSONObject();
			//失败数据Json容器
			JSONObject errorJson = new JSONObject();
			errorJson.put("nullFlag", "xs_id");
			errorJson.put("msg", "ID为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return checkAdd(xs_code, xs_name, xs_sex, nj_id, bj_id);
	}
}
