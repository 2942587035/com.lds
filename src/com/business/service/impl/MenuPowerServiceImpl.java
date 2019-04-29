package com.business.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.MenuPowerDao;
import com.business.service.inter.MenuPowerService;

import net.sf.json.JSONArray;
@Transactional
public class MenuPowerServiceImpl implements MenuPowerService {
    private MenuPowerDao menuPowerDao;
    
	public void setMenuPowerDao(MenuPowerDao menuPowerDao) {
		this.menuPowerDao = menuPowerDao;
	}

	@Override
	public JSONArray findMenuPowerByGN_Type(String gn_type) {
		return menuPowerDao.findMenuPowerByGN_Type(gn_type);
	}

}
