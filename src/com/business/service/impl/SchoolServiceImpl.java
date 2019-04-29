package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.SchoolDao;
import com.business.service.inter.SchoolService;

import net.sf.json.JSONObject;

/**
 * �ṩѧУ��������
 * @author lds
 *
 */
@Transactional
public class SchoolServiceImpl implements SchoolService {
	private SchoolDao schoolDao;

	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}
	
	@Override
	public List<?> findSchool() {
		return schoolDao.findSchool();
	}

	@Override
	public JSONObject insertSchool(String xx_code, String xx_name, String xx_type, String xx_remark) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = schoolDao.findCountSchoolByXX_Code(xx_code);
		if (count1 > 0) {
			errorJson.put("repeat", "xx_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = schoolDao.findCountSchoolByXX_Name(xx_name);
		if (count2 > 0) {
			errorJson.put("repeat", "xx_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		schoolDao.insertSchool(xx_code, xx_name, xx_type, xx_remark);
		List<?> list = schoolDao.findSchoolByXX_Code(xx_code);
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
	public JSONObject updateSchool(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = schoolDao.findCountSchoolByXX_CodeAndId(xx_code, xx_id);
		if (count1 > 0) {
			errorJson.put("repeat", "xx_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = schoolDao.findCountSchoolByXX_NameAndId(xx_name, xx_id);
		if (count2 > 0) {
			errorJson.put("repeat", "xx_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		schoolDao.updateSchool(xx_id, xx_code, xx_name, xx_type, xx_remark);
		
		successJson.put("msg", "�޸����ݳɹ���");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteSchool(String xx_id) {
		schoolDao.deleteSchool(xx_id);		
	}

	@Override
	public String findSchoolMaxCode() {
		return schoolDao.findSchoolMaxCode();
	}
}
