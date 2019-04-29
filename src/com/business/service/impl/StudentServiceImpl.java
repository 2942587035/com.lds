package com.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.StudentDao;
import com.business.dao.inter.UserDao;
import com.business.service.inter.StudentService;

import net.sf.json.JSONObject;
import utils.Md5Utils;



/**
 * 提供学生操作服务
 * @author lds
 *
 */
@Transactional
public class StudentServiceImpl implements StudentService {
	private StudentDao studentDao;
	private UserDao userDao;

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<?> findStudent() {
		return studentDao.findStudent();
	}

	@Override
	public JSONObject insertStudent(String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = studentDao.findCountStudentByXS_Code(xs_code);
		if (count1 > 0) {
			errorJson.put("repeat", "xs_code");
			errorJson.put("msg", "编码重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		studentDao.insertStudent(xs_code, xs_name, xs_sex, xs_phone, xs_email, nj_id, bj_id);
		List<?> list = studentDao.findStudentByXS_Code(xs_code);
		if (list.size() != 1) {
			errorJson.put("msg", "增加数据失败！");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		successJson.put("data", list.get(0));
		mainJson.put("success", successJson);
		
		/*插入学生成功，增加此用户*/
		userDao.insertUser(xs_code, xs_name, "2", Md5Utils.stringMD5("123456"));
		return mainJson;
	}

	@Override
	public JSONObject updateStudent(String xs_id, String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = studentDao.findCountStudentByXS_CodeAndId(xs_code, xs_id);
		if (count1 > 0) {
			errorJson.put("repeat", "xs_code");
			errorJson.put("msg", "编码重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		/*修改学生前先修改此用户*/
		Map<String, Object> map = (Map<String, Object>) studentDao.findStudentByXS_Id(xs_id);
		String yh_code = (String) map.get("xs_code");
		userDao.updateUserYH_CodeAndNameByYH_Code(yh_code, xs_code, xs_name);
		
		studentDao.updateStudent(xs_id, xs_code, xs_name, xs_sex, xs_phone, xs_email, nj_id, bj_id);
		
		successJson.put("msg", "修改数据成功！");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteStudent(String xs_id) {
		/*删除学生后删除此用户*/
		Map<String, Object> map = (Map<String, Object>) studentDao.findStudentByXS_Id(xs_id);
		String xs_code = (String) map.get("xs_code");
		userDao.deleteUserByYh_Code(xs_code);
		
		studentDao.deleteStudent(xs_id);		
	}

	@Override
	public List<?> findStudentByNJ_IdAndBJ_Id(String nj_id, String bj_id) {
		return studentDao.findStudentByNJ_IdAndBJ_Id(nj_id, bj_id);
	}

	@Override
	public String findStudentMaxCode() {
		return studentDao.findStudentMaxCode();
	}
}
