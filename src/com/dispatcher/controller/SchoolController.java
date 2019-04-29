package com.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.service.inter.SchoolService;

import enums.EnumPub.Message;
import net.sf.json.JSONObject;
import utils.util;

@Controller
@RequestMapping("/school")
public class SchoolController {
	//学校服务接口
	@Autowired
	private SchoolService schoolService;
       
	/**
	 * 取号
	 * @return
	 */
	@RequestMapping("/getMaxCode.do")
	@ResponseBody
	public Object getMaxCode() {
		return schoolService.findSchoolMaxCode();
	}
	
	/**
	 * 查询
	 * @return
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search() {
		return schoolService.findSchool();
	}
	
	/**
	 * 增加
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String xx_code, String xx_name, String xx_type, String xx_remark) {
		/*1.校验*/
		JSONObject mainJson = checkAdd(xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		
		/*2.插入*/
		mainJson = schoolService.insertSchool(xx_code, xx_name, xx_type, xx_remark);
		
		return mainJson;
	}
	
	/**
	 * 增加前校验
	 * @return
	 */
	private JSONObject checkAdd(String xx_code, String xx_name, String xx_type) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		/* 1.判空 nullFlag:告诉客户端谁为空 repeat:告诉客户端谁重复 */
		// 编码
		if (util.isBlank_String(xx_code)) {
			errorJson.put("nullFlag", "xx_code");
			errorJson.put("msg", "编码为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 名称
		if (util.isBlank_String(xx_name)) {
			errorJson.put("nullFlag", "xx_name");
			errorJson.put("msg", "名称为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// 类型
		if (util.isBlank_String(xx_type)) {
			errorJson.put("nullFlag", "xx_type");
			errorJson.put("msg", "类型为空！");
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
	public Object remove(String xx_id) {
		/*校验*/
		JSONObject mainJson = checkDelete(xx_id);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}
		/*删除*/
		schoolService.deleteSchool(xx_id);
		
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
	private JSONObject checkDelete(String xx_id) {
		// Json容器
		JSONObject mainJson = new JSONObject();
		// 失败数据Json容器
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(xx_id)) {
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
	public Object edit(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark) {
		JSONObject mainJson = checkEdit(xx_id, xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson;
		}

		mainJson = schoolService.updateSchool(xx_id, xx_code, xx_name, xx_type, xx_remark);
		return mainJson;
	}
	
	/**
	 * 编辑前校验
	 * @return
	 */
	private JSONObject checkEdit(String xx_id, String xx_code, String xx_name, String xx_type) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		/*1.判空 nullFlag:告诉客户端谁为空  repeat:告诉客户端谁重复*/
		if (util.isBlank_String(xx_id)) {
			errorJson.put("nullFlag", "xx_id");
			errorJson.put("msg", "ID为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//编码
		if (util.isBlank_String(xx_code)) {
			errorJson.put("nullFlag", "xx_code");
			errorJson.put("msg", "编码为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//名称
		if (util.isBlank_String(xx_name)) {
			errorJson.put("nullFlag", "xx_name");
			errorJson.put("msg", "名称为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//类型
		if (util.isBlank_String(xx_type)) {
			errorJson.put("nullFlag", "xx_type");
			errorJson.put("msg", "类型为空！");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
