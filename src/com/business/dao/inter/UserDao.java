package com.business.dao.inter;

import java.util.List;

import com.entity.User;

/**
 * 提供用户操作服务
 * @author lds
 *
 */
public interface UserDao {
	List<?> findUser();
	int findCountUserByYH_Code(String yh_code);
	int findCountUserByYH_CodeAndId(String yh_code, String yh_id);
	void insertUser(String yh_code, String yh_name, String yh_type, String yh_password);
	void updateUserYH_CodeAndName(String yh_id, String yh_code, String yh_name);
	void updateUserYH_CodeAndNameByYH_Code(String yh_code, String yh_codeNew, String yh_name);
	void deleteUser(String yh_id);
	void deleteUserByYh_Code(String yh_code);
	List<?> findUserByYH_Code(String yh_code);
	User getUserByYH_Code(String yh_code);
	void updateUserYH_Password(String yh_id,String newPassword);
}
