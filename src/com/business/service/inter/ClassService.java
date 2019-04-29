package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 提供班级操作服务
 * @author lds
 *
 */
public interface ClassService {
	/**
	 * 取号函数：获取最大编码
	 */
	String findClassMaxCode();
	List<?> findClass();
	JSONObject insertClass(String bj_code, String bj_name, String nj_id);
	JSONObject updateClass(String bj_id, String bj_code, String bj_name, String nj_id);
	void deleteClass(String bj_id);
	List<?> findClassByNJ_Id(String nj_id);
}
