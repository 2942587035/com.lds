package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 提供测试操作服务
 * @author lds
 *
 */
public interface TestService {
	/**
	 * 取号函数：获取最大编码
	 */
	String findTestMaxCode();
	List<?> findTest();
	JSONObject insertTest(String cs_code, String cs_name, String cs_type,String cs_remark, String nj_id);
	JSONObject updateTest(String cs_id, String cs_code, String cs_name, String cs_type,String cs_remark, String nj_id);
	void deleteTest(String cs_id);
	List<?> findTestByNJ_Id(String nj_id);
}
