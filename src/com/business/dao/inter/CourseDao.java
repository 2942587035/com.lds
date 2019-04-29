package com.business.dao.inter;

import java.util.List;

/**
 * 提供课程操作服务
 * @author lds
 *
 */
public interface CourseDao {
	public List<?> findCourse();
	public int findCountCourseByBJ_Code(String kc_code);
	public int findCountCourseByBJ_NameAndNJ_Id(String kc_name, String nj_id);
	public int findCountCourseByBJ_CodeAndId(String kc_code, String kc_id);
	public int findCountCourseBJ_NameAndNJ_IdAndId(String kc_name, String nj_id, String kc_id);
	public void insertCourse(String kc_code, String kc_name, String nj_id);
	public void updateCourse(String kc_id, String kc_code, String kc_name, String nj_id);
	public void deleteCourse(String kc_id);
	public List<?> findCourseByBJ_Code(String kc_code);
	public List<?> findCourseByNJ_Id(String nj_id);
	/**
	 * 取号函数：获取最大编码
	 */
	String findCourseMaxCode();
}
