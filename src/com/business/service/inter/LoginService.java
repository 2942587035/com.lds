package com.business.service.inter;

import java.util.List;

/**
 * ��½�ӿ�
 * @author DELL
 *
 */
public interface LoginService {
    /**
     * ��ѯ�û���ǰ�û��������ڵ�����
     * @param sql sql���
     * @param yh_code �û�����
     */
    public List<?> findUserByYH_Code(String yh_code);
}
