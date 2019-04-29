package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * �ṩ�༶��������
 * @author lds
 *
 */
public interface ClassService {
	/**
	 * ȡ�ź�������ȡ������
	 */
	String findClassMaxCode();
	List<?> findClass();
	JSONObject insertClass(String bj_code, String bj_name, String nj_id);
	JSONObject updateClass(String bj_id, String bj_code, String bj_name, String nj_id);
	void deleteClass(String bj_id);
	List<?> findClassByNJ_Id(String nj_id);
}
