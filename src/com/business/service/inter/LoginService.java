package com.business.service.inter;

import java.util.List;

/**
 * 登陆接口
 * @author DELL
 *
 */
public interface LoginService {
    /**
     * 查询用户表当前用户编码所在的数据
     * @param sql sql语句
     * @param yh_code 用户编码
     */
    public List<?> findUserByYH_Code(String yh_code);
}
