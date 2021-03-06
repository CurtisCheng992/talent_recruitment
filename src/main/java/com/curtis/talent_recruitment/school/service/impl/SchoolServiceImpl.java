package com.curtis.talent_recruitment.school.service.impl;

import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.school.SchoolCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.school.dao.SchoolDao;
import com.curtis.talent_recruitment.school.entity.School;
import com.curtis.talent_recruitment.school.service.ISchoolService;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.github.pagehelper.PageInfo;
import com.hs.commons.utils.PageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Map<String, Object> mpParam = new HashMap<>();
        List<School> arrSchool = schoolDao.getList(mpParam);
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

    @Override
    public QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam) {
        mpParam.put("pageNumber", lCurrentPage);
        mpParam.put("pageSize", lPageSize);
        //分页
        PageUtils.initPaging(mpParam);
        //查询列表
        List<School> arrSchool = schoolDao.getList(mpParam);
        //分页
        PageInfo<School> page = new PageInfo<>(arrSchool);
        List<PageInfo<School>> arrPage = Collections.singletonList(page);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPage, arrPage.size()));
    }
}
