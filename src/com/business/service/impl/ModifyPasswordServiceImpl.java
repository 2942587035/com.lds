package com.business.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.UserDao;
import com.business.service.inter.ModifyPasswordService;

/**
 * ÐÞ¸ÄÃÜÂë
 * @author DELL
 *
 */
@Transactional
public class ModifyPasswordServiceImpl implements ModifyPasswordService {
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void modifyYH_Password(String yh_id, String newPassword) {
		userDao.updateUserYH_Password(yh_id, newPassword);
	}
}
