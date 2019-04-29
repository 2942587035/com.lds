package com.business.dao.inter;

import java.util.List;

/**
 * 提供班级课程教师操作服务
 * @author lds
 *
 */
public interface ClassCourseTeacherDao {
	public List<?> findClassCourseTeacher();
	public Object findClassCourseTeacherByNBKJ_Id(String nbkj_id);
	public List<?> findClassCourseTeacherByNBKJ(String nj_id, String bj_id, String kc_id, String js_id);
	public List<?> findClassCourseTeacherByNJ_Id(String nj_id);
	public int findCountClassCourseTeacherByNBKJ(String nj_id, String bj_id, String kc_id, String js_id);
	public int findCountClassCourseTeacherByNBKJAndId(String nj_id, String bj_id, String kc_id, String js_id, String nbkj_id);
	public void insertClassCourseTeacher(String nj_id, String bj_id, String kc_id, String js_id);
	public void updateClassCourseTeacher(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id);
	public void deleteClassCourseTeacher(String nbkj_id);
}
