package com.business.service.impl;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.ClassCourseTeacherDao;
import com.business.service.inter.ClassCourseTeacherService;

import net.sf.json.JSONObject;

/**
 * 提供班级课程教师操作服务
 * @author lds
 *
 */
@Transactional
public class ClassCourseTeacherServiceImpl implements ClassCourseTeacherService {
	private ClassCourseTeacherDao classCourseTeacherDao;
	
	public void setClassCourseTeacherDao(ClassCourseTeacherDao classCourseTeacherDao) {
		this.classCourseTeacherDao = classCourseTeacherDao;
	}

	@Override
	public List<?> findClassCourseTeacher() {
		return classCourseTeacherDao.findClassCourseTeacher();
	}

	@Override
	public JSONObject insertClassCourseTeacher(String nj_id, String bj_id, String kc_id, String js_id) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = classCourseTeacherDao.findCountClassCourseTeacherByNBKJ(nj_id, bj_id, kc_id, js_id);
		if (count1 > 0) {
			errorJson.put("repeat", "js_id");
			errorJson.put("msg", "教师重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		classCourseTeacherDao.insertClassCourseTeacher(nj_id, bj_id, kc_id, js_id);
		List<?> list = classCourseTeacherDao.findClassCourseTeacherByNBKJ(nj_id, bj_id, kc_id, js_id);
		if (list.size() != 1) {
			errorJson.put("msg", "增加数据失败！");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		successJson.put("data", list.get(0));
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public JSONObject updateClassCourseTeacher(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = classCourseTeacherDao.findCountClassCourseTeacherByNBKJAndId(nj_id, bj_id, kc_id, js_id, nbkj_id);
		if (count1 > 0) {
			errorJson.put("repeat", "js_id");
			errorJson.put("msg", "教师重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		classCourseTeacherDao.updateClassCourseTeacher(nbkj_id, nj_id, bj_id, kc_id, js_id);
		successJson.put("msg", JSONObject.fromObject(classCourseTeacherDao.findClassCourseTeacherByNBKJ_Id(nbkj_id)));
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteClassCourseTeacher(String nbkj_id) {
		classCourseTeacherDao.deleteClassCourseTeacher(nbkj_id);		
	}

	@Override
	public List<?> findClassCourseTeacherByNJ_Id(String nj_id) {
		return classCourseTeacherDao.findClassCourseTeacherByNJ_Id(nj_id);
	}
}
