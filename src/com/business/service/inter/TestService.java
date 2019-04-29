package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * �ṩ���Բ�������
 * @author lds
 *
 */
public interface TestService {
	/**
	 * ȡ�ź�������ȡ������
	 */
	String findTestMaxCode();
	List<?> findTest();
	JSONObject insertTest(String cs_code, String cs_name, String cs_type,String cs_remark, String nj_id);
	JSONObject updateTest(String cs_id, String cs_code, String cs_name, String cs_type,String cs_remark, String nj_id);
	void deleteTest(String cs_id);
	List<?> findTestByNJ_Id(String nj_id);
}
