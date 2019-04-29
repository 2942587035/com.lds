package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.TestDao;
import com.business.service.inter.TestService;

import net.sf.json.JSONObject;

/**
 * �ṩ���Բ�������
 * @author lds
 *
 */
@Transactional
public class TestServiceImpl implements TestService {
	private TestDao testDao;
	
	public void setTestDao(TestDao testDao) {
		this.testDao = testDao;
	}

	@Override
	public List<?> findTest() {
		return testDao.findTest();
	}

	@Override
	public JSONObject insertTest(String cs_code, String cs_name, String cs_type,String cs_remark, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = testDao.findCountTestByCS_Code(cs_code);
		if (count1 > 0) {
			errorJson.put("repeat", "cs_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = testDao.findCountTestByCS_NameAndNJ_Id(cs_name, nj_id);
		if (count2 > 0) {
			errorJson.put("repeat", "cs_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		testDao.insertTest(cs_code, cs_name, cs_type, cs_remark, nj_id);
		List<?> list = testDao.findTestByCS_Code(cs_code);
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
	public JSONObject updateTest(String cs_id, String cs_code, String cs_name, String cs_type,String cs_remark, String nj_id) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = testDao.findCountTestByCS_CodeAndId(cs_code, cs_id);
		if (count1 > 0) {
			errorJson.put("repeat", "cs_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = testDao.findCountTestCS_NameAndNJ_IdAndId(cs_name, nj_id, cs_id);
		if (count2 > 0) {
			errorJson.put("repeat", "cs_name");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		testDao.updateTest(cs_id, cs_code, cs_name, cs_type, cs_remark, nj_id);
		
		successJson.put("msg", "�޸����ݳɹ���");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteTest(String cs_id) {
		testDao.deleteTest(cs_id);		
	}

	@Override
	public List<?> findTestByNJ_Id(String nj_id) {
		return testDao.findTestByNJ_Id(nj_id);
	}

	@Override
	public String findTestMaxCode() {
		return testDao.findTestMaxCode();
	}
}
