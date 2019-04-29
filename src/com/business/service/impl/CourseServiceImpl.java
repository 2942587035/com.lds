package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.CourseDao;
import com.business.service.inter.CourseService;

import net.sf.json.JSONObject;

/**
 * �ṩ�γ̲�������
 * @author lds
 *
 */
@Transactional
public class CourseServiceImpl implements CourseService {
	private CourseDao courseDao;
	
	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@Override
	public List<?> findCourse() {
		return courseDao.findCourse();
	}

	@Override
	public JSONObject insertCourse(String kc_code, String kc_name, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = courseDao.findCountCourseByBJ_Code(kc_code);
		if (count1 > 0) {
			errorJson.put("repeat", "kc_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = courseDao.findCountCourseByBJ_NameAndNJ_Id(kc_name, nj_id);
		if (count2 > 0) {
			errorJson.put("repeat", "kc_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		courseDao.insertCourse(kc_code, kc_name, nj_id);
		List<?> list = courseDao.findCourseByBJ_Code(kc_code);
		if (list.size() != 1) {
			errorJson.put("msg", "��������ʧ�ܣ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		successJson.put("data", list.get(0));
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public JSONObject updateCourse(String kc_id, String kc_code, String kc_name, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = courseDao.findCountCourseByBJ_CodeAndId(kc_code, kc_id);
		if (count1 > 0) {
			errorJson.put("repeat", "kc_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = courseDao.findCountCourseBJ_NameAndNJ_IdAndId(kc_name, nj_id, kc_id);
		if (count2 > 0) {
			errorJson.put("repeat", "kc_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		courseDao.updateCourse(kc_id, kc_code, kc_name, nj_id);
		
		successJson.put("msg", "�޸����ݳɹ���");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteCourse(String kc_id) {
		courseDao.deleteCourse(kc_id);		
	}

	@Override
	public List<?> findCourseByNJ_Id(String nj_id) {
		return courseDao.findCourseByNJ_Id(nj_id);
	}

	@Override
	public String findCourseMaxCode() {
		return courseDao.findCourseMaxCode();
	}
}
