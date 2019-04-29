package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * �ṩ�꼶��������
 * @author lds
 *
 */
public interface GradeService {
	/**
	 * ȡ�ź�������ȡ������
	 */
	String findGradeMaxCode();
	public List<?> findGrade();
	public JSONObject insertGrade(String nj_code, String nj_name);
	public JSONObject updateGrade(String nj_id, String nj_code, String nj_name);
	public void deleteGrade(String nj_id);
	public List<?> findGrade_NameAndId();
}
