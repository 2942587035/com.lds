package com.business.service.inter;

import java.util.List;
import net.sf.json.JSONObject;

/**
 * 提供学生操作服务
 * @author lds
 *
 */
public interface StudentService {
	/**
	 * 取号函数：获取最大编码
	 */
	String findStudentMaxCode();
	List<?> findStudent();
	JSONObject insertStudent(String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id);
	JSONObject updateStudent(String xs_id, String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id);
	void deleteStudent(String xs_id);
	List<?> findStudentByNJ_IdAndBJ_Id(String nj_id, String bj_id);
}
