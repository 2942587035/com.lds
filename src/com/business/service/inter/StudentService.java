package com.business.service.inter;

import java.util.List;
import net.sf.json.JSONObject;

/**
 * �ṩѧ����������
 * @author lds
 *
 */
public interface StudentService {
	/**
	 * ȡ�ź�������ȡ������
	 */
	String findStudentMaxCode();
	List<?> findStudent();
	JSONObject insertStudent(String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id);
	JSONObject updateStudent(String xs_id, String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id);
	void deleteStudent(String xs_id);
	List<?> findStudentByNJ_IdAndBJ_Id(String nj_id, String bj_id);
}
