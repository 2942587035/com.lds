package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.UserDao;
import com.business.service.inter.LoginService;
/**
 * ��½����
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
     * ��ѯ�û���ǰ�û��������ڵ�����
     * @param sql sql���
     * @param yh_code �û�����
     */
    public List<?> findUserByYH_Code(String yh_code) {
    	return userDao.findUserByYH_Code(yh_code);
    }
}
