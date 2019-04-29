package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * �ṩ�༶�γ̽�ʦ��������
 * @author lds
 *
 */
public interface ClassCourseTeacherService {
	public List<?> findClassCourseTeacher();
	public List<?> findClassCourseTeacherByNJ_Id(String nj_id);
	public JSONObject insertClassCourseTeacher(String nj_id, String bj_id, String kc_id, String js_id);
	public JSONObject updateClassCourseTeacher(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id);
	public void deleteClassCourseTeacher(String nbkj_id);
}
