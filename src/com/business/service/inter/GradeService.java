package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 提供年级操作服务
 * @author lds
 *
 */
public interface GradeService {
	/**
	 * 取号函数：获取最大编码
	 */
	String findGradeMaxCode();
	public List<?> findGrade();
	public JSONObject insertGrade(String nj_code, String nj_name);
	public JSONObject updateGrade(String nj_id, String nj_code, String nj_name);
	public void deleteGrade(String nj_id);
	public List<?> findGrade_NameAndId();
}
