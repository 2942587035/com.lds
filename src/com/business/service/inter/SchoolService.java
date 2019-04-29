package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 提供用户操作服务
 * @author lds
 *
 */
public interface SchoolService {
	/**
	 * 取号函数：获取最大编码
	 */
	String findSchoolMaxCode();
	public List<?> findSchool();
	public JSONObject insertSchool(String xx_code, String xx_name, String xx_type, String xx_remark);
	public JSONObject updateSchool(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark);
	public void deleteSchool(String xx_id);
}
