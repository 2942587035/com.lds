package com.business.dao.inter;

import java.util.List;

/**
 * �ṩ��ʦ��������
 * @author lds
 *
 */
public interface TeacherDao {
	public List<?> findTeacher();
	public int findCountTeacherByJS_Code(String js_code);
	public int findCountTeacherByJS_CodeAndId(String js_code, String js_id);
	public void insertTeacher(String js_code, String js_name, String js_sex, String js_phone, String js_email);
	public void updateTeacher(String js_id, String js_code, String js_name, String js_sex, String js_phone, String js_email);
	public void deleteTeacher(String js_id);
	public List<?> findTeacherByJS_Code(String js_code);
	public Object findTeacherByJS_Id(String js_id);
	/**
	 * ȡ�ź�������ȡ������
	 */
	String findTeacherMaxCode();
}
