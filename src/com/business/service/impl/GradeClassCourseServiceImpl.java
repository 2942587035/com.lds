package com.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.ClassDao;
import com.business.dao.inter.CourseDao;
import com.business.dao.inter.GradeDao;
import com.business.service.inter.GradeClassCourseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查询年级，班级，课程（树结构）
 * @author lds
 *
 */
@Transactional
public class GradeClassCourseServiceImpl implements GradeClassCourseService {
    private GradeDao gradeDao;
    private ClassDao classDao;
    private CourseDao courseDao;
    
	public void setGradeDao(GradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@Override
	public JSONArray findClassAndCourseByGrade() {
		JSONArray resultArray = new JSONArray();
		List<?> gradeList = gradeDao.findGrade();
		for (int i = 0; i < gradeList.size(); i++) {
			Map<String, Object> gradeMap = (Map<String, Object>) gradeList.get(i);
			JSONObject gradeObject = new JSONObject();
			//年级ID
			String nj_id = String.valueOf(gradeMap.get("nj_id"));
			gradeObject.put("id", nj_id);
			gradeObject.put("text", gradeMap.get("nj_name"));

			//班级
			JSONArray classArray = findClassByNJ_Id(nj_id);
			if (!classArray.isEmpty()) {
				gradeObject.put("classArray", classArray);
			}
			
			//课程
			JSONArray courseArray = findCourseByNJ_Id(nj_id);
			if (!courseArray.isEmpty()) {
				gradeObject.put("courseArray", courseArray);
			}
			
			resultArray.add(gradeObject);
		}
		return resultArray;
	}
	
	/**
	 * 根据年级查询班级
	 * @param nj_id
	 * @return
	 */
	public JSONArray findClassByNJ_Id(String nj_id) {
		JSONArray classArray = new JSONArray();
		List<?> classList = classDao.findClassByNJ_Id(nj_id);
		for (int i = 0; i < classList.size(); i++) {
			Map<String, Object> classMap = (Map<String, Object>) classList.get(i);
			JSONObject classObject = new JSONObject();
			classObject.put("id", classMap.get("bj_id"));
			classObject.put("text", classMap.get("bj_name"));
			
			classArray.add(classObject);
		}
		return classArray;
	}
	
	/**
	 * 根据年级查询课程
	 * @param nj_id
	 * @return
	 */
	public JSONArray findCourseByNJ_Id(String nj_id) {
		JSONArray courseArray = new JSONArray();
		List<?> courseList = courseDao.findCourseByNJ_Id(nj_id);
		for (int i = 0; i < courseList.size(); i++) {
			Map<String, Object> courseMap = (Map<String, Object>) courseList.get(i);
			JSONObject courseObject = new JSONObject();
			courseObject.put("id", courseMap.get("kc_id"));
			courseObject.put("text", courseMap.get("kc_name"));
			
			courseArray.add(courseObject);
		}
		return courseArray;
	}
}
