package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.UserDao;
import com.business.service.inter.UserService;
import com.entity.User;

import net.sf.json.JSONObject;

/**
 * 提供用户操作服务
 * @author lds
 *
 */
@Transactional
public class UserServiceImpl implements UserService {
    private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<?> findUser() {
		return userDao.findUser();
	}

	@Override
	public JSONObject insertUser(String yh_code, String yh_name, String yh_type, String yh_password) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = userDao.findCountUserByYH_Code(yh_code);
		if (count1 > 0) {
			errorJson.put("repeat", "yh_code");
			errorJson.put("msg", "编码重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		userDao.insertUser(yh_code, yh_name, yh_type, yh_password);
		List<?> list = userDao.findUserByYH_Code(yh_code);
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
	public JSONObject updateUserYH_CodeAndName(String yh_id, String yh_code, String yh_name) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = userDao.findCountUserByYH_CodeAndId(yh_code, yh_id);
		if (count1 > 0) {
			errorJson.put("repeat", "yh_code");
			errorJson.put("msg", "编码重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		userDao.updateUserYH_CodeAndName(yh_id, yh_code, yh_name);
		
		successJson.put("msg", "修改数据成功！");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteUser(String yh_id) {
		userDao.deleteUser(yh_id);		
	}

	@Override
	public void deleteUserByYH_Code(String yh_code) {
		userDao.deleteUserByYh_Code(yh_code);
	}

	@Override
	public List<?> findUserByYH_Code(String yh_code) {
		List<?> list = userDao.findUserByYH_Code(yh_code);
    	return list;
    }

	@Override
	public void updateUserYH_Password(String yh_id, String newPassword) {
		userDao.updateUserYH_Password(yh_id, newPassword);
		
	}

	@Override
	public User getUserByYH_Code(String yh_code) {
		return userDao.getUserByYH_Code(yh_code);
	}
}
