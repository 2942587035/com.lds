package com.business.dao.inter;

import net.sf.json.JSONArray;

/**
 * �˵�
 * @author DELL
 *
 */
public interface MenuPowerDao {
	JSONArray findMenuPowerByGN_Type(String gn_type);
}
