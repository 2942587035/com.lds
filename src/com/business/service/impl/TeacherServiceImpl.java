package com.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.TeacherDao;
import com.business.dao.inter.UserDao;
import com.business.service.inter.TeacherService;

import net.sf.json.JSONObject;
import utils.Md5Utils;

/**
 * �ṩ��ʦ��������
 * @author lds
 *
 */
@Transactional
public class TeacherServiceImpl implements TeacherService {
	private TeacherDao teacherDao;
	private UserDao userDao;

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<?> findTeacher() {
		return teacherDao.findTeacher();
	}

	@Override
	public JSONObject insertTeacher(String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = teacherDao.findCountTeacherByJS_Code(js_code);
		if (count1 > 0) {
			errorJson.put("repeat", "js_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		teacherDao.insertTeacher(js_code, js_name, js_sex, js_phone, js_email);
		List<?> list = teacherDao.findTeacherByJS_Code(js_code);
		if (list.size() != 1) {
			errorJson.put("msg", "��������ʧ�ܣ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		successJson.put("data", list.get(0));
		mainJson.put("success", successJson);
		
		/*�����ʦ�ɹ������Ӵ��û�*/
		userDao.insertUser(js_code, js_name, "3", Md5Utils.stringMD5("123456"));
		return mainJson;
	}

	@Override
	public JSONObject updateTeacher(String js_id, String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		//Json����
		JSONObject mainJson = new JSONObject();
		//�ɹ�����Json����
		JSONObject successJson = new JSONObject();
		//ʧ������Json����
		JSONObject errorJson = new JSONObject();
		
		int count1 = teacherDao.findCountTeacherByJS_CodeAndId(js_code, js_id);
		if (count1 > 0) {
			errorJson.put("repeat", "js_code");
			errorJson.put("msg", "�����ظ�");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		/*�޸Ľ�ʦǰ���޸Ĵ��û�*/
		Map<String, Object> map = (Map<String, Object>) teacherDao.findTeacherByJS_Id(js_id);
		String yh_code = (String) map.get("js_code");
		userDao.updateUserYH_CodeAndNameByYH_Code(yh_code, js_code, js_name);
		
		teacherDao.updateTeacher(js_id, js_code, js_name, js_sex, js_phone, js_email);
		
		successJson.put("msg", "�޸����ݳɹ���");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteTeacher(String js_id) {
		/*ɾ����ʦ��ɾ�����û�*/
		Map<String, Object> map = (Map<String, Object>) teacherDao.findTeacherByJS_Id(js_id);
		String js_code = (String) map.get("js_code");
		userDao.deleteUserByYh_Code(js_code);
		
		teacherDao.deleteTeacher(js_id);		
	}

	@Override
	public String findTeacherMaxCode() {
		return teacherDao.findTeacherMaxCode();
	}
}
