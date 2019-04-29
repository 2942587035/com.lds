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
	//学校服务接口
	@Autowired
	private TeacherService teacherService;
       
	/**
	 * 取号
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return teacherService.findTeacherMaxCode();
	}
	
	/**
	 * 查询
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
	 * 增加
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		/*1.校验*/
		JSONObject mainJson = checkAdd(js_code, js_name, js_sex);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.插入*/
		mainJson = teacherService.insertTeacher(js_code, js_name, js_sex, js_phone, js_email);
		
		return mainJson;
	}
	
	/**
	 * 增加前校验
	 * @return
	 */
	private JSONObject checkAdd(String js_code, String js_name, String js_sex) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		/* 1.判空 nullFlag:告诉客户端谁为空 repeat:告诉客户端谁重复 */
		// 编码
		if (util.isBlank_String(js_code)) {
			errorJson.put("nullFlag", "js_code");
			errorJson.put("msg", "编码为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 名称
		if (util.isBlank_String(js_name)) {
			errorJson.put("nullFlag", "js_name");
			errorJson.put("msg", "名称为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 性别
		if (util.isBlank_String(js_sex)) {
			errorJson.put("nullFlag", "js_sex");
			errorJson.put("msg", "性别为空！");
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
	public Object remove(String js_id) {
		/*校验*/
		JSONObject mainJson = checkDelete(js_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*删除*/
		teacherService.deleteTeacher(js_id);
		
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
	private JSONObject checkDelete(String js_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(js_id)) {
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
	public Object edit(String js_id, String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		JSONObject mainJson = checkEdit(js_id, js_code, js_name, js_sex);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = teacherService.updateTeacher(js_id, js_code, js_name, js_sex, js_phone, js_email);
		return mainJson;
	}
	
	/**
	 * 编辑前校验
	 * @return
	 */
	private JSONObject checkEdit(String js_id, String js_code, String js_name, String js_sex) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		/*1.判空 nullFlag:告诉客户端谁为空  repeat:告诉客户端谁重复*/
		if (util.isBlank_String(js_id)) {
			errorJson.put("nullFlag", "js_id");
			errorJson.put("msg", "ID为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//编码
		if (util.isBlank_String(js_code)) {
			errorJson.put("nullFlag", "js_code");
			errorJson.put("msg", "编码为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//名称
		if (util.isBlank_String(js_name)) {
			errorJson.put("nullFlag", "js_name");
			errorJson.put("msg", "名称为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//性别
		if (util.isBlank_String(js_sex)) {
			errorJson.put("nullFlag", "js_sex");
			errorJson.put("msg", "性别为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
