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
	//班级服务接口
	@Autowired
	private CourseService courseService;
	@Autowired
	//年级服务接口
	private GradeService gradeService;
       
	/**
	 * 取号
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return courseService.findCourseMaxCode();
	}
	
	/**
	 * 查询年级
	 * @return
	 */
	@RequestMapping("/searchGrade.do")
	@ResponseBody
	public Object searchGrade() {
		return gradeService.findGrade_NameAndId();
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
			return mainJson;
		}
		/*2查询*/
		return courseService.findCourseByNJ_Id(nj_id);
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
		// 年级ID
		if (util.isBlank_String(nj_id)) {
			errorJson.put("nullFlag", "nj_id");
			errorJson.put("msg", "ID为空！");
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
	public Object add(String kc_code, String kc_name, String nj_id) {
		/*1.校验*/
		JSONObject mainJson = checkAdd(kc_code, kc_name, nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.插入*/
		mainJson = courseService.insertCourse(kc_code, kc_name, nj_id);
		
		return mainJson;
	}
	
	/**
	 * 增加前校验
	 * @return
	 */
	private JSONObject checkAdd(String kc_code, String kc_name, String nj_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		/* 1.判空 nullFlag:告诉客户端谁为空 repeat:告诉客户端谁重复 */
		// 编码
		if (util.isBlank_String(kc_code)) {
			errorJson.put("nullFlag", "kc_code");
			errorJson.put("msg", "编码为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 名称
		if (util.isBlank_String(kc_name)) {
			errorJson.put("nullFlag", "kc_name");
			errorJson.put("msg", "名称为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
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
	 * 删除
	 * @return
	 */
	@RequestMapping("/remove.do")
	@ResponseBody
	public Object remove(String kc_id) {
		/*校验*/
		JSONObject mainJson = checkDelete(kc_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*删除*/
		courseService.deleteCourse(kc_id);
		
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
	private JSONObject checkDelete(String kc_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(kc_id)) {
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
	public Object edit(String kc_id, String kc_code, String kc_name, String nj_id) {
		JSONObject mainJson = checkEdit(kc_id, kc_code, kc_name, nj_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = courseService.updateCourse(kc_id, kc_code, kc_name, nj_id);
		return mainJson;
	}
	
	/**
	 * 编辑前校验
	 * @return
	 */
	private JSONObject checkEdit(String kc_id, String kc_code, String kc_name, String nj_id) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		/*1.判空 nullFlag:告诉客户端谁为空  repeat:告诉客户端谁重复*/
		if (util.isBlank_String(kc_id)) {
			errorJson.put("nullFlag", "kc_id");
			errorJson.put("msg", "ID为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//编码
		if (util.isBlank_String(kc_code)) {
			errorJson.put("nullFlag", "kc_code");
			errorJson.put("msg", "编码为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//名称
		if (util.isBlank_String(kc_name)) {
			errorJson.put("nullFlag", "kc_name");
			errorJson.put("msg", "名称为空！");
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
		
		return mainJson;
	}
}
