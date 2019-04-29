package com.business.dao.inter;

import java.util.List;

/**
 * 提供学生操作服务
 * @author lds
 *
 */
public interface StudentDao {
	public List<?> findStudent();
	public int findCountStudentByXS_Code(String xs_code);
	public int findCountStudentByXS_CodeAndId(String xs_code, String xs_id);
	public void insertStudent(String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id);
	public void updateStudent(String xs_id, String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id);
	public void deleteStudent(String xs_id);
	public List<?> findStudentByXS_Code(String xs_code);
	public Object findStudentByXS_Id(String xs_id);
	public List<?> findStudentByNJ_IdAndBJ_Id(String nj_id, String bj_id);
	/**
	 * 取号函数：获取最大编码
	 */
	String findStudentMaxCode();
}
