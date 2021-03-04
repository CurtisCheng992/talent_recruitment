package com.curtis.talent_recruitment.company.service.impl;

import com.curtis.talent_recruitment.company.dao.CompanyDao;
import com.curtis.talent_recruitment.company.entity.Company;
import com.curtis.talent_recruitment.company.service.ICompanyService;
import com.curtis.talent_recruitment.department.dao.DepartmentDao;
import com.curtis.talent_recruitment.entity.request.company.AddCompany;
import com.curtis.talent_recruitment.entity.request.company.UpdateCompany;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.company.CompanyCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.hs.commons.utils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:48 PM 3/1/2021
 */
@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 查询所有公司信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<Company> arrCompany = companyDao.getList();
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCompany,arrCompany.size()));
    }

    /**
     * 根据id查询一个公司信息
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
        //根据id查询公司信息
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)){
            return new QueryResponse(CompanyCode.COMPANY_NOT_FOUND,null);
        }
        List<Company> arrCompany = new ArrayList<>();
        arrCompany.add(company);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCompany,arrCompany.size()));
    }

    /**
     * 添加一个公司信息
     * @param addCompany
     * @return
     */
    @Override
    public CommonResponse add(AddCompany addCompany) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addCompany)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCompanyName非空判断
        if (!StringUtils.isNoneBlank(addCompany.getSCompanyName())){
            ExceptionThrowUtils.cast(CompanyCode.INVALID_PARAM);
        }
        //添加一个公司
        mpParam = ConvertUtils.objectToMap(addCompany);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        int iResult = companyDao.add(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CompanyCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个公司信息
     * @param id
     * @return
     */
    @Override
    public CommonResponse delete(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断该公司下是否存在部门
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sCompanyID",id);
        int iCount = departmentDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(CompanyCode.DELETE_FAIL_DEPARTMENT_EXIST);
        }
        //根据id查询公司是否存在
        mpParam.put("id",id);
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)){
            return new CommonResponse(CompanyCode.COMPANY_NOT_FOUND);
        }
        int iResult = companyDao.delete(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CompanyCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个公司信息
     * @param id
     * @param updateCompany
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateCompany updateCompany) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if(ObjectUtils.isEmpty(updateCompany) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCompanyName非空判断
        if (!StringUtils.isNoneBlank(updateCompany.getSCompanyName())){
            ExceptionThrowUtils.cast(CompanyCode.INVALID_PARAM);
        }
        //根据id判断该公司信息是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Company company = companyDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(company)){
            return new CommonResponse(CompanyCode.COMPANY_NOT_FOUND);
        }
        //更新公司信息
        mpParam = ConvertUtils.objectToMap(updateCompany);
        mpParam.put("id",id);
        mpParam.put("dUpdateTime",new Date());
        int iResult = companyDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CompanyCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}