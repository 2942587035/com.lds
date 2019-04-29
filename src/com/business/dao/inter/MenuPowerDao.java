package com.business.dao.inter;

import net.sf.json.JSONArray;

/**
 * ²Ëµ¥
 * @author DELL
 *
 */
public interface MenuPowerDao {
	JSONArray findMenuPowerByGN_Type(String gn_type);
}
