package com.curtis.talent_recruitment.school.service.impl;

import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.school.SchoolCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.school.dao.SchoolDao;
import com.curtis.talent_recruitment.school.entity.School;
import com.curtis.talent_recruitment.school.service.ISchoolService;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:50 PM 2/25/2021
 */
@Service
public class SchoolServiceImpl implements ISchoolService {

    @Autowired
    private SchoolDao schoolDao;

    /**
     * 查询所有学校信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<School> arrSchool = schoolDao.getList();
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrSchool,arrSchool.size()));
    }

    /**
     * 根据id查询一所学校
     * @param id
     * @return
     */
    @Override
    public QueryResponse getDetail(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        mpParam.put("id",id);
        School school = schoolDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(school)){
            return new QueryResponse(SchoolCode.SCHOOL_NOT_FOUND,null);
        }
        List<School> arrSchool = new ArrayList<>();
        arrSchool.add(school);
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrSchool,arrSchool.size()));
    }
}
