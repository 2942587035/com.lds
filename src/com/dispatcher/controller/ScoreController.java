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
	//ѧУ����ӿ�
	private ScoreService scoreService;
       
    public ScoreController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	scoreService = (ScoreService) ApplicationContextUtil.getBean("scoreServiceImpl");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ָ�����صĸ�ʽΪJSON��ʽ
		response.setContentType("text/json;charset=utf-8");
		// setContentType��setCharacterEncoding��˳���ܵ������������޷�����������������
		response.setCharacterEncoding("UTF-8");

		/********** ҵ���� **********/
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
	 * ҵ����������ɾ�Ĳ�
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
	 * ��ѯ
	 * @param request
	 * @param response
	 * @return
	 */
	private String search(HttpServletRequest request, HttpServletResponse response) {
		return JSONArray.fromObject(scoreService.findScore()).toString();
	}
	
	/**
	 * ����
	 * @param request
	 * @param response
	 * @return
	 */
	private String add(HttpServletRequest request, HttpServletResponse response) {
		//����
		String xx_code = request.getParameter("xx_code");
		//����
		String xx_name = request.getParameter("xx_name");
		//����
		String xx_type = request.getParameter("xx_type");
		//��ע
		String xx_remark = request.getParameter("xx_remark");
		
		/*1.У��*/
		JSONObject mainJson = checkAdd(xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson.toString();
		}
		
		/*2.����*/
		mainJson = scoreService.insertScore(xx_code, xx_name, xx_type, xx_remark);
		
		return mainJson.toString();
	}
	
	/**
	 * ����ǰУ��
	 * @return
	 */
	public JSONObject checkAdd(String xx_code, String xx_name, String xx_type) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		/* 1.�п� nullFlag:���߿ͻ���˭Ϊ�� repeat:���߿ͻ���˭�ظ� */
		// ����
		if (util.isBlank_String(xx_code)) {
			errorJson.put("nullFlag", "xx_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(xx_name)) {
			errorJson.put("nullFlag", "xx_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		// ����
		if (util.isBlank_String(xx_type)) {
			errorJson.put("nullFlag", "xx_type");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}

		return mainJson;
	}
	
	/**
	 * ɾ��
	 * @param request
	 * @param response
	 * @return
	 */
	private String remove(HttpServletRequest request, HttpServletResponse response) {
		//ѧУID
		String xx_id = request.getParameter("xx_id");
		/*У��*/
		JSONObject mainJson = checkDelete(xx_id);
		if (!mainJson.isEmpty()) {
			return mainJson.toString();
		}
		/*ɾ��*/
		scoreService.deleteScore(xx_id);
		
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		successJson.put("msg", Message.RemoveSuccess.getMessage());
		mainJson.put("success", successJson);
		return mainJson.toString();
	}
	
	/**
	 * ɾ��ǰУ��
	 * @return
	 */
	private JSONObject checkDelete(String xx_id) {
		// Json����
		JSONObject mainJson = new JSONObject();
		// ʧ������Json����
		JSONObject errorJson = new JSONObject();

		if (util.isBlank_String(xx_id)) {
			errorJson.put("msg", "��Ҫɾ�������ݲ����ڣ���ˢ�����ݣ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
	
	/**
	 * �޸�
	 * @param request
	 * @param response
	 * @return
	 */
	private String edit(HttpServletRequest request, HttpServletResponse response) {
		//ID
		String xx_id = request.getParameter("xx_id");
		//����
		String xx_code = request.getParameter("xx_code");
		//����
		String xx_name = request.getParameter("xx_name");
		//����
		String xx_type = request.getParameter("xx_type");
		//��ע
		String xx_remark = request.getParameter("xx_remark");
		
		JSONObject mainJson = checkEdit(xx_id, xx_code, xx_name, xx_type);
		if (!mainJson.isEmpty()) {
			return mainJson.toString();
		}

		mainJson = scoreService.updateScore(xx_id, xx_code, xx_name, xx_type, xx_remark);
		return mainJson.toString();
	}
	
	/**
	 * �༭ǰУ��
	 * @return
	 */
	private JSONObject checkEdit(String xx_id, String xx_code, String xx_name, String xx_type) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		/*1.�п� nullFlag:���߿ͻ���˭Ϊ��  repeat:���߿ͻ���˭�ظ�*/
		if (util.isBlank_String(xx_id)) {
			errorJson.put("nullFlag", "xx_id");
			errorJson.put("msg", "IDΪ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(xx_code)) {
			errorJson.put("nullFlag", "xx_code");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(xx_name)) {
			errorJson.put("nullFlag", "xx_name");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		//����
		if (util.isBlank_String(xx_type)) {
			errorJson.put("nullFlag", "xx_type");
			errorJson.put("msg", "����Ϊ�գ�");
			mainJson.put("error", errorJson);
			return mainJson;
		}
		
		return mainJson;
	}
}
