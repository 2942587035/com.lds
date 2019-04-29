package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.UserDao;
import com.business.service.inter.LoginService;
/**
 * 登陆服务
 * @author DELL
 *
 */
@Transactional
public class LoginServiceImpl implements LoginService {
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	};
	
    /**
     * 查询用户表当前用户编码所在的数据
     * @param sql sql语句
     * @param yh_code 用户编码
     */
    public List<?> findUserByYH_Code(String yh_code) {
    	return userDao.findUserByYH_Code(yh_code);
    }
}
