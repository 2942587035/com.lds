package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.ClassDao;
import com.business.dao.inter.ClassDao;
import com.business.service.inter.ClassService;

import net.sf.json.JSONObject;

/**
 * �ṩ�༶��������
 * @author lds
 *
 */
@Transactional
public class ClassServiceImpl implements ClassService {
	private ClassDao classDao;
	
	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	@Override
	public List<?> findClass() {
		return classDao.findClass();
	}

	@Override
	public JSONObject insertClass(String bj_code, String bj_name, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = classDao.findCountClassByBJ_Code(bj_code);
		if (count1 > 0) {
			errorJson.put("repeat", "bj_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = classDao.findCountClassByBJ_NameAndNJ_Id(bj_name, nj_id);
		if (count2 > 0) {
			errorJson.put("repeat", "bj_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		classDao.insertClass(bj_code, bj_name, nj_id);
		List<?> list = classDao.findClassByBJ_Code(bj_code);
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
	public JSONObject updateClass(String bj_id, String bj_code, String bj_name, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = classDao.findCountClassByBJ_CodeAndId(bj_code, bj_id);
		if (count1 > 0) {
			errorJson.put("repeat", "bj_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = classDao.findCountClassBJ_NameAndNJ_IdAndId(bj_name, nj_id, bj_id);
		if (count2 > 0) {
			errorJson.put("repeat", "bj_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		classDao.updateClass(bj_id, bj_code, bj_name, nj_id);
		
		successJson.put("msg", "�޸����ݳɹ���");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteClass(String bj_id) {
		classDao.deleteClass(bj_id);		
	}

	@Override
	public List<?> findClassByNJ_Id(String nj_id) {
		return classDao.findClassByNJ_Id(nj_id);
	}

	@Override
	public String findClassMaxCode() {
		return classDao.findClassMaxCode();
	}
}
