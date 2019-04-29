package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 提供教师操作服务
 * @author lds
 *
 */
public interface TeacherService {
	/**
	 * 取号函数：获取最大编码
	 */
	String findTeacherMaxCode();
	List<?> findTeacher();
	JSONObject insertTeacher(String js_code, String js_name, String js_sex, String js_phone, String js_email);
	JSONObject updateTeacher(String js_id, String js_code, String js_name, String js_sex, String js_phone, String js_email);
	void deleteTeacher(String js_id);
}
