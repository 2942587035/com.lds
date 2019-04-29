package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 提供课程操作服务
 * @author lds
 *
 */
public interface CourseService {
	/**
	 * 取号函数：获取最大编码
	 */
	String findCourseMaxCode();
	public List<?> findCourse();
	public JSONObject insertCourse(String kc_code, String kc_name, String nj_id);
	public JSONObject updateCourse(String kc_id, String kc_code, String kc_name, String nj_id);
	public void deleteCourse(String kc_id);
	public List<?> findCourseByNJ_Id(String nj_id);
}
