package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.GradeDao;
import com.business.dao.inter.GradeDao;
import com.business.service.inter.GradeService;

import net.sf.json.JSONObject;

/**
 * �ṩ�꼶��������
 * @author lds
 *
 */
@Transactional
public class GradeServiceImpl implements GradeService {
	private GradeDao gradeDao;
	
	public void setGradeDao(GradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	@Override
	public List<?> findGrade() {
		return gradeDao.findGrade();
	}

	@Override
	public JSONObject insertGrade(String nj_code, String nj_name) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = gradeDao.findCountGradeByNJ_Code(nj_code);
		if (count1 > 0) {
			errorJson.put("repeat", "nj_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = gradeDao.findCountGradeByNJ_Name(nj_name);
		if (count2 > 0) {
			errorJson.put("repeat", "nj_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		gradeDao.insertGrade(nj_code, nj_name);
		List<?> list = gradeDao.findGradeByNJ_Code(nj_code);
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
	public JSONObject updateGrade(String nj_id, String nj_code, String nj_name) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = gradeDao.findCountGradeByNJ_CodeAndId(nj_code, nj_id);
		if (count1 > 0) {
			errorJson.put("repeat", "nj_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = gradeDao.findCountGradeByNJ_NameAndId(nj_name, nj_id);
		if (count2 > 0) {
			errorJson.put("repeat", "nj_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		gradeDao.updateGrade(nj_id, nj_code, nj_name);
		
		successJson.put("msg", "�޸����ݳɹ���");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteGrade(String nj_id) {
		gradeDao.deleteGrade(nj_id);		
	}

	@Override
	public List<?> findGrade_NameAndId() {
		return gradeDao.findGrade_NameAndId();	
	}

	@Override
	public String findGradeMaxCode() {
		return gradeDao.findGradeMaxCode();
	}
}
