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
	//分配教师
	@Autowired
	private ClassCourseTeacherService classCourseTeacherService;
	//查询年级，班级，课程服务
	@Autowired
	private GradeClassCourseService gradeClassCourseService;
       
	/**
	 * 查询班级和课程/年级
	 * @return
	 */
	@RequestMapping("/searchGradeClassCourse.do")
	@ResponseBody
	public Object searchGradeClassCourse() {
		return gradeClassCourseService.findClassAndCourseByGrade();
	}
	
	/**
	 * 查询
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search(String nj_id) {
		/*1.校验*/
		JSONObject mainJson = checkSearch(nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson.toString();
		}
		
		return classCourseTeacherService.findClassCourseTeacherByNJ_Id(nj_id);
	}
	
	/**
	 * 查询前校验
	 * @return
	 */
	private JSONObject checkSearch(String nj_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		/* 1.判空 nullFlag:告诉客户端谁为空 repeat:告诉客户端谁重复 */
		// 年级
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "年级为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}

		return mainJson;
	}
	
	/**
	 * 增加
	 * @return
	 */
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(String nj_id, String bj_id, String kc_id, String js_id) {
		/*1.校验*/
		JSONObject mainJson = checkAdd(nj_id, bj_id, kc_id, js_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.插入*/
		mainJson = classCourseTeacherService.insertClassCourseTeacher(nj_id, bj_id, kc_id, js_id);
		
		return mainJson;
	}
	
	/**
	 * 增加前校验
	 * @return
	 */
	private JSONObject checkAdd(String nj_id, String bj_id, String kc_id, String js_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		/* 1.判空 nullFlag:告诉客户端谁为空 repeat:告诉客户端谁重复 */
		// 年级
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "年级为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 班级
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "班级为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 课程
		if (util.isBlank_String(kc_id)) {
			errorJson.put("nullFlag", "kc_id");
			errorJson.put("msg", "课程为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 教师
		if (util.isBlank_String(js_id)) {
			errorJson.put("nullFlag", "js_id");
			errorJson.put("msg", "教师为空！");
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
	public Object remove(String nbkj_id) {
		/*校验*/
		JSONObject mainJson = checkDelete(nbkj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*删除*/
		classCourseTeacherService.deleteClassCourseTeacher(nbkj_id);
		
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
	private JSONObject checkDelete(String nbkj_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(nbkj_id)) {
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
	public Object edit(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id) {
		JSONObject mainJson = checkEdit(nbkj_id, nj_id, bj_id, kc_id, js_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = classCourseTeacherService.updateClassCourseTeacher(nbkj_id, nj_id, bj_id, kc_id, js_id);
		return mainJson;
	}
	
	/**
	 * 编辑前校验
	 * @return
	 */
	private JSONObject checkEdit(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		/*1.判空 nullFlag:告诉客户端谁为空  repeat:告诉客户端谁重复*/
		if (util.isBlank_String(nbkj_id)) {
			errorJson.put("nullFlag", "nbkj_id");
			errorJson.put("msg", "ID为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//年级
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "年级为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//班级
		if (util.isBlank_String(bj_id)) {
			errorJson.put("nullFlag", "bj_id");
			errorJson.put("msg", "班级为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//课程
		if (util.isBlank_String(kc_id)) {
			errorJson.put("nullFlag", "kc_id");
			errorJson.put("msg", "课程为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//教师
		if (util.isBlank_String(js_id)) {
			errorJson.put("nullFlag", "js_id");
			errorJson.put("msg", "教师为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
