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
import com.curtis.talent_recruitment.position.dao.PositionDao;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.user.entity.User;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.github.pagehelper.PageInfo;
import com.hs.commons.utils.ConvertUtils;
import com.hs.commons.utils.PageUtils;
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

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private UserDao userDao;

    /**
     * 查询所有公司信息
     *
     * @return
     */
    @Override
    public QueryResponse getList() {
        Map<String, Object> mpParam = new HashMap<>();
        List<Company> arrCompany = companyDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Company company : arrCompany) {
            mpGet.put("sCompanyID", company.getId());
            Integer iSum = positionDao.getSumByCompanyID(mpGet);
            company.setITotalQuantity(iSum);
            mpGet.clear();
            mpGet.put("id",company.getSHRID());
            User HR = userDao.getDetail(mpGet);
            company.setSHRUsername(HR.getSUsername());
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCompany, arrCompany.size()));
    }

    /**
     * 根据id查询一个公司信息
     *
     * @param id
     * @return
     */
    @Override
    public QueryResponse getDetail(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        mpParam.put("id", id);
        //根据id查询公司信息
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)) {
            return new QueryResponse(CompanyCode.COMPANY_NOT_FOUND, null);
        }
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("sCompanyID", company.getId());
        Integer iSum = positionDao.getSumByCompanyID(mpGet);
        company.setITotalQuantity(iSum);
        mpGet.clear();
        mpGet.put("id",company.getSHRID());
        User HR = userDao.getDetail(mpGet);
        company.setSHRUsername(HR.getSUsername());
        List<Company> arrCompany = new ArrayList<>();
        arrCompany.add(company);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCompany, arrCompany.size()));
    }

    /**
     * 添加一个公司信息
     *
     * @param addCompany
     * @return
     */
    @Override
    public CommonResponse add(AddCompany addCompany) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addCompany)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCompanyName非空判断
        if (!StringUtils.isNoneBlank(addCompany.getSCompanyName())) {
            ExceptionThrowUtils.cast(CompanyCode.INVALID_PARAM);
        }
        //添加一个公司
        mpParam = ConvertUtils.objectToMap(addCompany);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        mpParam.put("iHot",0);
        mpParam.put("iStatus", 0); //默认状态为 0待审核
        if (StringUtils.isBlank(addCompany.getSHRID())){
            mpParam.put("sHRID",null);
        }
        if (StringUtils.isNoneBlank(addCompany.getSHRUsername())){
            //获取sHRID
            Map<String, Object> mpGet = new HashMap<>();
            mpGet.put("sUsername",addCompany.getSHRUsername());
            User HR = userDao.getDetail(mpGet);
            if (ObjectUtils.isEmpty(HR)){
                return new CommonResponse(CompanyCode.HR_NOT_FOUNT);
            }
            mpParam.put("sHRID",HR.getId());
        }
        int iResult = companyDao.add(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(CompanyCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个公司信息
     *
     * @param id
     * @return
     */
    @Override
    public CommonResponse delete(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断该公司下是否存在部门
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sCompanyID", id);
        int iCount = departmentDao.getCount(mpCheck);
        if (iCount >= 1) {
            return new CommonResponse(CompanyCode.DELETE_FAIL_DEPARTMENT_EXIST);
        }
        //根据id查询公司是否存在
        mpParam.put("id", id);
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)) {
            return new CommonResponse(CompanyCode.COMPANY_NOT_FOUND);
        }
        int iResult = companyDao.delete(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(CompanyCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个公司信息
     *
     * @param id
     * @param updateCompany
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateCompany updateCompany) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updateCompany) && !StringUtils.isNoneBlank(id)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCompanyName非空判断
        if (!StringUtils.isNoneBlank(updateCompany.getSCompanyName())) {
            ExceptionThrowUtils.cast(CompanyCode.INVALID_PARAM);
        }
        //根据id判断该公司信息是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id", id);
        Company company = companyDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(company)) {
            return new CommonResponse(CompanyCode.COMPANY_NOT_FOUND);
        }
        //更新公司信息
        mpParam = ConvertUtils.objectToMap(updateCompany);
        mpParam.put("id", id);
        mpParam.put("dUpdateTime", new Date());
        mpParam.put("iStatus",0);
        int iResult = companyDao.update(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(CompanyCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 可限制数量查询热门企业
     *
     * @param iLimit
     * @return
     */
    @Override
    public QueryResponse getHot(Integer iLimit) {
        //查询
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("iLimit", iLimit);
        List<Company> arrCompany = companyDao.getHot(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Company company : arrCompany) {
            mpGet.put("sCompanyID", company.getId());
            Integer iSum = positionDao.getSumByCompanyID(mpGet);
            company.setITotalQuantity(iSum);
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCompany, arrCompany.size()));
    }

    /**
     * 根据HRID查询公司信息
     *
     * @param sHRID
     * @return
     */
    @Override
    public QueryResponse getDetailByHRID(String sHRID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sHRID)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //HR用户判断
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id", sHRID);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)) {
            return new QueryResponse(CompanyCode.HR_NOT_FOUNT, null);
        }
        //查询
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sHRID", sHRID);
        Company company = companyDao.getDetail(mpParam);
        List<Company> arrCompany = Collections.singletonList(company);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCompany, arrCompany.size()));
    }

    @Override
    public QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam) {
        mpParam.put("pageNumber", lCurrentPage);
        mpParam.put("pageSize", lPageSize);
        //分页
        PageUtils.initPaging(mpParam);
        //查询列表
        List<Company> arrCompany = companyDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Company company : arrCompany) {
            mpGet.put("sCompanyID", company.getId());
            Integer iSum = positionDao.getSumByCompanyID(mpGet);
            company.setITotalQuantity(iSum);
            mpGet.clear();
            if (StringUtils.isNoneBlank(company.getSHRID())){
                mpGet.put("id",company.getSHRID());
                User HR = userDao.getDetail(mpGet);
                company.setSHRUsername(HR.getSUsername());
            }
        }
        PageInfo<Company> page = new PageInfo<>(arrCompany);
        List<PageInfo<Company>> arrPage = Collections.singletonList(page);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPage, arrPage.size()));
    }

    /**
     * 根据公司id更新状态
     *
     * @param id
     * @return
     */
    @Override
    public CommonResponse updateStatus(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id", id);
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)) {
            return new CommonResponse(CompanyCode.COMPANY_NOT_FOUND);
        }
        if (company.getIStatus() == 1) { //已审核，改为已注销
            mpParam.put("iStatus", 2);
        } else if (company.getIStatus() == 0) { //待审核，改为已审核
            mpParam.put("iStatus", 1);
        } else if (company.getIStatus() == 2){ //已注销，改为以神恶化
            mpParam.put("iStatus",1);
        }
        int iResult = companyDao.updateStatus(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(CompanyCode.UPDATE_STATUS_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    @Override
    public CommonResponse updatePicture(String id, String sPicture) {
        //参数非空判断
        if (StringUtils.isBlank(sPicture)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        sPicture = sPicture.split("\"")[3];
        //判断公司
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id", id);
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)) {
            return new CommonResponse(CompanyCode.COMPANY_NOT_FOUND);
        }
        //更新公司logo图片
        mpParam.clear();
        mpParam.put("sPicture", sPicture);
        mpParam.put("id", id);
        int iCount = companyDao.updatePicture(mpParam);
        if (iCount <= 0) {
            return new CommonResponse(CompanyCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}
