package com.business.service.inter;

import java.util.List;

import com.entity.User;

import net.sf.json.JSONObject;

/**
 * 提供用户操作服务
 * @author lds
 *
 */
public interface UserService {
	List<?> findUser();
	JSONObject insertUser(String yh_code, String yh_name, String yh_type, String yh_password);
	JSONObject updateUserYH_CodeAndName(String yh_id, String yh_code, String yh_name);
	void deleteUser(String yh_id);
	void deleteUserByYH_Code(String yh_code);
	List<?> findUserByYH_Code(String yh_code);
	User getUserByYH_Code(String yh_code);
	void updateUserYH_Password(String yh_id,String newPassword);
}
