package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * �ṩ�γ̲�������
 * @author lds
 *
 */
public interface CourseService {
	/**
	 * ȡ�ź�������ȡ������
	 */
	String findCourseMaxCode();
	public List<?> findCourse();
	public JSONObject insertCourse(String kc_code, String kc_name, String nj_id);
	public JSONObject updateCourse(String kc_id, String kc_code, String kc_name, String nj_id);
	public void deleteCourse(String kc_id);
	public List<?> findCourseByNJ_Id(String nj_id);
}
