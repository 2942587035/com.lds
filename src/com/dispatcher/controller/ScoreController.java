package com.dispatcher.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.business.service.inter.ScoreService;

import enums.EnumPub.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.ApplicationContextUtil;
import utils.util;

@Controller
@RequestMapping("/score")
public class ScoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//学校服务接口
	private ScoreService scoreService;
       
    public ScoreController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	scoreService = (ScoreService) ApplicationContextUtil.getBean("scoreServiceImpl");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 指定返回的格式为JSON格式
		response.setContentType("text/json;charset=utf-8");
		// setContentType与setCharacterEncoding的顺序不能调换，否则还是无法解决中文乱码的问题
		response.setCharacterEncoding("UTF-8");

		/********** 业务处理 **********/
		String serviceHandler = serviceHandler(request, response);

		PrintWriter out = response.getWriter();
		out.write(serviceHandler);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 业务处理：区分增删改查
	 * @param request
	 */
	private String serviceHandler(HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		
		String type = request.getParameter("type");
		switch (type) {
		case "search":
			result = search(request, response);
			break;
		case "add":
			result = add(request, response);
			break;
		case "edit":
			result = edit(request, response);
			break;
		case "remove":
			result = remove(request, response);
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	private String search(HttpServletRequest request, HttpServletResponse response) {
		return JSONArray.fromObject(scoreService.findScore()).toString();
	}
	
	/**
	 * 增加
	 * @param request
	 * @param response
	 * @return
	 */
	private String add(HttpServletRequest request, HttpServletResponse response) {
		//编码
		String xx_code = request.getParameter("xx_code");
		//名称
		String xx_name = request.getParameter("xx_name");
		//类型
		String xx_type = request.getParameter("xx_type");
		//备注
		String xx_remark = request.getParameter("xx_remark");
		
		/*1.校验*/
		JSONObject mainJson = checkAdd(xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson.toString();
		}
		
		/*2.插入*/
		mainJson = scoreService.insertScore(xx_code, xx_name, xx_type, xx_remark);
		
		return mainJson.toString();
	}
	
	/**
	 * 增加前校验
	 * @return
	 */
	public JSONObject checkAdd(String xx_code, String xx_name, String xx_type) {
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
	 * @param request
	 * @param response
	 * @return
	 */
	private String remove(HttpServletRequest request, HttpServletResponse response) {
		//学校ID
		String xx_id = request.getParameter("xx_id");
		/*校验*/
		JSONObject mainJson = checkDelete(xx_id);
		if (!mainJson.isEmpty()) {
			return mainJson.toString();
		}
		/*删除*/
		scoreService.deleteScore(xx_id);
		
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		successJson.put("msg", Message.RemoveSuccess.getMessage());
		mainJson.put("success", successJson);
		return mainJson.toString();
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
	 * @param request
	 * @param response
	 * @return
	 */
	private String edit(HttpServletRequest request, HttpServletResponse response) {
		//ID
		String xx_id = request.getParameter("xx_id");
		//编码
		String xx_code = request.getParameter("xx_code");
		//名称
		String xx_name = request.getParameter("xx_name");
		//类型
		String xx_type = request.getParameter("xx_type");
		//备注
		String xx_remark = request.getParameter("xx_remark");
		
		JSONObject mainJson = checkEdit(xx_id, xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson.toString();
		}

		mainJson = scoreService.updateScore(xx_id, xx_code, xx_name, xx_type, xx_remark);
		return mainJson.toString();
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
