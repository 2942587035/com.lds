package com.business.service.inter;

import net.sf.json.JSONArray;

/**
 * 班级/年级 列表查询
 * @author lds
 *
 */
public interface GradeClassService {
    public JSONArray findClassByGrade();
}
