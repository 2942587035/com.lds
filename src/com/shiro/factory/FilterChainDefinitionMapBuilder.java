package com.shiro.factory;

import java.util.LinkedHashMap;
/**
 * Ȩ������
 * @author ���
 *
 */
public class FilterChainDefinitionMapBuilder {
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/project/module/login/**", "anon");
		//authc��֤   user��֤���߼�ס��
		map.put("/project/module/**", "user");
		return map;
	}
}
