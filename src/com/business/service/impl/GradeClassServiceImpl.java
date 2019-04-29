package com.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.ClassDao;
import com.business.dao.inter.GradeDao;
import com.business.service.inter.GradeClassService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
public class GradeClassServiceImpl implements GradeClassService {
    private GradeDao gradeDao;
    private ClassDao classDao;
    
	public void setGradeDao(GradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	@Override
	public JSONArray findClassByGrade() {
		JSONArray resultArray = new JSONArray();
		List<?> gradeList = gradeDao.findGrade();
		for (int i = 0; i < gradeList.size(); i++) {
			Map<String, Object> gradeMap = (Map<String, Object>) gradeList.get(i);
			JSONObject gradeObject = new JSONObject();
			//Äê¼¶ID
			String nj_id = String.valueOf(gradeMap.get("nj_id"));
			gradeObject.put("id", nj_id);
			gradeObject.put("text", gradeMap.get("nj_name"));
			gradeObject.put("type", "nj");
			
			JSONArray classArray = findClassByNJ_Id(nj_id);
			if (!classArray.isEmpty()) {
				gradeObject.put("children", classArray);
				resultArray.add(gradeObject);
			}
		}
		return resultArray;
	}
	
	public JSONArray findClassByNJ_Id(String nj_id) {
		JSONArray classArray = new JSONArray();
		List<?> classList = classDao.findClassByNJ_Id(nj_id);
		for (int i = 0; i < classList.size(); i++) {
			Map<String, Object> classMap = (Map<String, Object>) classList.get(i);
			JSONObject classObject = new JSONObject();
			classObject.put("id", classMap.get("bj_id"));
			classObject.put("text", classMap.get("bj_name"));
			classObject.put("type", "bj");
			
			classArray.add(classObject);
		}
		return classArray;
	}
}
