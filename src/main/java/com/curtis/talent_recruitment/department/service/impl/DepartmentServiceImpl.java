package com.curtis.talent_recruitment.department.service.impl;

import com.curtis.talent_recruitment.company.dao.CompanyDao;
import com.curtis.talent_recruitment.company.entity.Company;
import com.curtis.talent_recruitment.department.dao.DepartmentDao;
import com.curtis.talent_recruitment.department.dao.UserDepartmentDao;
import com.curtis.talent_recruitment.department.entity.Department;
import com.curtis.talent_recruitment.department.service.IDepartmentService;
import com.curtis.talent_recruitment.entity.request.department.AddDepartment;
import com.curtis.talent_recruitment.entity.request.department.UpdateDepartment;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.department.DepartmentCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.position.dao.PositionDao;
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
 * @Date: Created in 4:35 PM 3/1/2021
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private PositionDao positionDao;

    /**
     * 查询所有部门信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        Map<String, Object> mpParam = new HashMap<>();
        List<Department> arrDepartment = departmentDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Department department : arrDepartment) {
            mpGet.put("id",department.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            department.setSCompanyName(company.getSCompanyName());
        }
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrDepartment,arrDepartment.size()));
    }

    /**
     * 根据id查询一个部门信息
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
        //根据id查询部门信息
        mpParam.put("id",id);
        Department department = departmentDao.getDetail(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("id",department.getSCompanyID());
        Company company = companyDao.getDetail(mpGet);
        department.setSCompanyName(company.getSCompanyName());
        if (ObjectUtils.isEmpty(department)){
            return new QueryResponse(DepartmentCode.DEPARTMENT_NOT_FOUND,null);
        }
        List<Department> arrDepartment = new ArrayList<>();
        arrDepartment.add(department);
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrDepartment,arrDepartment.size()));
    }

    /**
     * 新增一个部门信息
     * @param addDepartment
     * @return
     */
    @Override
    public CommonResponse add(AddDepartment addDepartment) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addDepartment)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sDepartmentName,sCompanyID非空判断
        if (!StringUtils.isNoneBlank(addDepartment.getSDepartmentName()) ||
        !StringUtils.isNoneBlank(addDepartment.getSCompanyID())){
            ExceptionThrowUtils.cast(DepartmentCode.INVALID_PARAM);
        }
        //根据sCompanyID查询公司是否存在
        mpParam.put("id",addDepartment.getSCompanyID());
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)){
            return new CommonResponse(DepartmentCode.COMPANY_NOT_FOUND);
        }
        mpParam.clear();
        //添加一个部门信息
        mpParam = ConvertUtils.objectToMap(addDepartment);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        int iResult = departmentDao.add(mpParam);
        if (iResult <= 0){
            return new CommonResponse(DepartmentCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个部门信息
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
        //判断该部门下是否存在职位
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sDepartmentID",id);
        int iCount = positionDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(DepartmentCode.DELETE_FAIL_POSITION_EXIST);
        }
        //根据id查询部门是否存在
        mpParam.put("id",id);
        Department department = departmentDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(department)){
            return new CommonResponse(DepartmentCode.DEPARTMENT_NOT_FOUND);
        }
        //根据id删除一个部门信息
        int iResult = departmentDao.delete(mpParam);
        if (iResult <= 0){
            return new CommonResponse(DepartmentCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个部门信息
     * @param id
     * @param updateDepartment
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateDepartment updateDepartment) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updateDepartment) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sDepartmentName,sCompanyID非空判断
        if (!StringUtils.isNoneBlank(updateDepartment.getSDepartmentName()) ||
                !StringUtils.isNoneBlank(updateDepartment.getSCompanyID())){
            ExceptionThrowUtils.cast(DepartmentCode.INVALID_PARAM);
        }
        //根据id判断该部门信息是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Department department = departmentDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(department)){
            return new CommonResponse(DepartmentCode.DEPARTMENT_NOT_FOUND);
        }
        //根据sCompanyID查询公司是否存在
        mpParam.put("id",updateDepartment.getSCompanyID());
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)){
            return new CommonResponse(DepartmentCode.COMPANY_NOT_FOUND);
        }
        mpParam.clear();
        //更新部门信息
        mpParam = ConvertUtils.objectToMap(updateDepartment);
        mpParam.put("id",id);
        int iResult = departmentDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(DepartmentCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    @Override
    public QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam) {
        mpParam.put("pageNumber", lCurrentPage);
        mpParam.put("pageSize", lPageSize);
        //分页
        PageUtils.initPaging(mpParam);
        //查询列表
        String sCompanyName = (String) mpParam.get("sCompanyName");
        if (StringUtils.isNoneBlank(sCompanyName)){
            String sCompanyID = companyDao.getIdByName(sCompanyName);
            mpParam.put("sCompanyID",sCompanyID);
        }
        List<Department> arrDepartment = departmentDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Department department : arrDepartment) {
            mpGet.put("id",department.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            department.setSCompanyName(company.getSCompanyName());
        }
        //分页
        PageInfo<Department> page = new PageInfo<>(arrDepartment);
        List<PageInfo<Department>> arrPage = Collections.singletonList(page);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPage, arrPage.size()));
    }
}
