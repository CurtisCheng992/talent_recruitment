package com.curtis.talent_recruitment.position.service.impl;

import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.category.dao.CategoryDao;
import com.curtis.talent_recruitment.category.entity.Category;
import com.curtis.talent_recruitment.collection.dao.CollectionDao;
import com.curtis.talent_recruitment.company.dao.CompanyDao;
import com.curtis.talent_recruitment.company.entity.Company;
import com.curtis.talent_recruitment.department.dao.DepartmentDao;
import com.curtis.talent_recruitment.department.entity.Department;
import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.position.PositionCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.position.dao.PositionDao;
import com.curtis.talent_recruitment.position.entity.Position;
import com.curtis.talent_recruitment.position.service.IPositionService;
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
 * @Date: Created in 5:22 PM 3/1/2021
 */
@Service
public class PositionServiceImpl implements IPositionService {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private UserDao userDao;

    /**
     * 查询所有职位信息
     *
     * @return
     */
    @Override
    public QueryResponse getList(String sHRID) {
        Map<String, Object> mpParam = new HashMap<>();
        if (StringUtils.isNoneBlank(sHRID)) {
            mpParam.put("sHRID", sHRID);
        }
        List<Position> arrPosition = positionDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Position position : arrPosition) {
            mpGet.put("id", position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            position.setSCompanyType(company.getSCompanyType());
            position.setSCompanyDescription(company.getSDescription());
            position.setSCompanyName(company.getSCompanyName());
            position.setSCompanyLogo(company.getSCompanyLogo());
            position.setSIntroduction(company.getSIntroduction());
            mpGet.clear();
            mpGet.put("sPositionID", position.getId());
            int iCount = collectionDao.getCount(mpGet);
            position.setICollections(iCount);
            mpGet.clear();
            mpGet.put("id", position.getSDepartmentID());
            Department department = departmentDao.getDetail(mpGet);
            position.setSDepartmentName(department.getSDepartmentName());
            position.setSDepartmentDesc(department.getSDescription());
            mpGet.clear();
            mpGet.put("id", position.getSCategoryID());
            Category category = categoryDao.getDetail(mpGet);
            position.setSCategoryName(category.getSCategoryName());
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPosition, arrPosition.size()));
    }

    /**
     * 根据id查询一个职位信息
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
        //根据id查询职位信息
        mpParam.put("id", id);
        positionDao.updateViews(mpParam);
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)) {
            return new QueryResponse(PositionCode.POSITION_NOT_FOUND, null);
        }
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("id", position.getSCompanyID());
        Company company = companyDao.getDetail(mpGet);
        position.setSCompanyType(company.getSCompanyType());
        position.setSCompanyDescription(company.getSDescription());
        position.setSCompanyName(company.getSCompanyName());
        position.setSCompanyLogo(company.getSCompanyLogo());
        position.setSIntroduction(company.getSIntroduction());
        mpGet.clear();
        mpGet.put("sPositionID", position.getId());
        int iCount = collectionDao.getCount(mpGet);
        position.setICollections(iCount);
        mpGet.clear();
        mpGet.put("id", position.getSDepartmentID());
        Department department = departmentDao.getDetail(mpGet);
        position.setSDepartmentName(department.getSDepartmentName());
        position.setSDepartmentDesc(department.getSDescription());
        mpGet.clear();
        mpGet.put("id", position.getSCategoryID());
        Category category = categoryDao.getDetail(mpGet);
        position.setSCategoryName(category.getSCategoryName());
        List<Position> arrPosition = new ArrayList<>();
        arrPosition.add(position);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPosition, arrPosition.size()));
    }

    /**
     * 新增一个职位信息
     *
     * @param addPosition
     * @return
     */
    @Override
    public CommonResponse add(AddPosition addPosition) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addPosition)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCategoryName,sDepartmentName,sPositionName非空判断
        if (!StringUtils.isNoneBlank(addPosition.getSCategoryName()) ||
                !StringUtils.isNoneBlank(addPosition.getSDepartmentName()) ||
                !StringUtils.isNoneBlank(addPosition.getSPositionName())) {
            ExceptionThrowUtils.cast(PositionCode.INVALID_PARAM);
        }
        //根据HR获取公司id
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("sHRID", addPosition.getSHRID());
        Company company = companyDao.getDetail(mpGet);
        if (ObjectUtils.isEmpty(company)){
            return new CommonResponse(PositionCode.POSITION_ADD_FAIL_COMPANY_NOT_EXIST);
        }
        addPosition.setSCompanyID(company.getId());
        //根据sDepartmentName和sCompanyID获取sDepartmentID
        mpGet.clear();
        mpGet.put("sDepartmentName", addPosition.getSDepartmentName());
        mpGet.put("sCompanyID", addPosition.getSCompanyID());
        Department department = departmentDao.getDetail(mpGet);
        if (ObjectUtils.isEmpty(department)) {
            return new CommonResponse(PositionCode.DEPARTMENT_NOT_FOUND);
        } else {
            addPosition.setSDepartmentID(department.getId());
        }
        //根据sCategoryName获取sCategoryID
        mpGet.clear();
        mpGet.put("sCategoryName", addPosition.getSCategoryName());
        Category category = categoryDao.getDetail(mpGet);
        if (ObjectUtils.isEmpty(category)) {
            return new CommonResponse(PositionCode.CATEGORY_NOT_FOUND);
        } else {
            addPosition.setSCategoryID(category.getId());
        }
        //判断职位的招聘人数是否小于1
        if (addPosition.getIQuantity() < 1) {
            return new CommonResponse(PositionCode.QUANTITY_CANT_LESS_THAN_1);
        }
        //添加一个职位信息
        mpParam = ConvertUtils.objectToMap(addPosition);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        mpParam.put("iHot", 0);
        mpParam.put("iViews", 0);
        int iResult = positionDao.add(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(PositionCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个职位信息
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
        //判断该职位下是否存在申请，收藏
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sPositionID", id);
        int iCount = applicationDao.getCount(mpCheck);
        if (iCount >= 1) {
            return new CommonResponse(PositionCode.DELETE_FAIL_APPLICATION_EXIST);
        }
        iCount = collectionDao.getCount(mpCheck);
        if (iCount >= 1) {
            return new CommonResponse(PositionCode.DELETE_FAIL_COLLECTION_EXIST);
        }
        //根据id查询该职位是否存在
        mpParam.put("id", id);
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)) {
            return new CommonResponse(PositionCode.POSITION_NOT_FOUND);
        }
        //根据id删除一个职位信息
        int iResult = positionDao.delete(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(PositionCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个职位信息
     *
     * @param id
     * @param updatePosition
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdatePosition updatePosition) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updatePosition) && !StringUtils.isNoneBlank(id)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCategoryName,sDepartmentName,sPositionName非空判断
        if (!StringUtils.isNoneBlank(updatePosition.getSCategoryName()) ||
                !StringUtils.isNoneBlank(updatePosition.getSDepartmentName()) ||
                !StringUtils.isNoneBlank(updatePosition.getSPositionName())) {
            ExceptionThrowUtils.cast(PositionCode.INVALID_PARAM);
        }
        //根据id查询该职位是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id", id);
        Position position = positionDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(position)) {
            return new CommonResponse(PositionCode.POSITION_NOT_FOUND);
        }
        //根据sCategoryName获取sCategoryID
        mpParam.put("sCategoryName", updatePosition.getSCategoryName());
        Category category = categoryDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(category)) {
            return new CommonResponse(PositionCode.CATEGORY_NOT_FOUND);
        } else {
            updatePosition.setSCategoryID(category.getId());
        }
        mpParam.clear();
        //根据sDepartmentName和sCompanyID获取sDepartmentID
        mpParam.put("sDepartmentName", updatePosition.getSDepartmentName());
        mpParam.put("sCompanyID", updatePosition.getSCompanyID());
        Department department = departmentDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(department)) {
            return new CommonResponse(PositionCode.DEPARTMENT_NOT_FOUND);
        } else {
            updatePosition.setSDepartmentID(department.getId());
        }
        mpParam.clear();
        //判断职位的招聘人数是否小于1
        if (updatePosition.getIQuantity() < 1) {
            return new CommonResponse(PositionCode.QUANTITY_CANT_LESS_THAN_1);
        }
        //根据sCompanyID判断公司是否存
        mpParam.put("id", updatePosition.getSCompanyID());
        Company company = companyDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(company)) {
            return new CommonResponse(PositionCode.COMPANY_NOT_FOUND);
        }
        //根据id更新职位信息
        mpParam = ConvertUtils.objectToMap(updatePosition);
        mpParam.put("id", id);
        mpParam.put("dUpdateTime", new Date());
        int iResult = positionDao.update(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(PositionCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 查询热门职位
     *
     * @param iLimit 查询个数
     * @return
     */
    @Override
    public QueryResponse getHot(Integer iLimit) {
        //查询
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("iLimit", iLimit);
        List<Position> arrPosition = positionDao.getHot(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Position position : arrPosition) {
            mpGet.put("id", position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            position.setSCompanyType(company.getSCompanyType());
            position.setSCompanyDescription(company.getSDescription());
            position.setSCompanyName(company.getSCompanyName());
            position.setSCompanyLogo(company.getSCompanyLogo());
            position.setSIntroduction(company.getSIntroduction());
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPosition, arrPosition.size()));
    }

    @Override
    public QueryResponse getNew(Integer iLimit) {
        //查询
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("iLimit", iLimit);
        List<Position> arrPosition = positionDao.getNew(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Position position : arrPosition) {
            mpGet.put("id", position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            position.setSCompanyType(company.getSCompanyType());
            position.setSCompanyDescription(company.getSDescription());
            position.setSCompanyName(company.getSCompanyName());
            position.setSCompanyLogo(company.getSCompanyLogo());
            position.setSIntroduction(company.getSIntroduction());
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPosition, arrPosition.size()));
    }

    @Override
    public QueryResponse getSearch(String sPositionName) {
        //搜索查询
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sPositionName", sPositionName);
        List<Position> arrPosition = positionDao.getSearch(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Position position : arrPosition) {
            mpGet.put("id", position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            position.setSCompanyType(company.getSCompanyType());
            position.setSCompanyDescription(company.getSDescription());
            position.setSCompanyName(company.getSCompanyName());
            position.setSCompanyLogo(company.getSCompanyLogo());
            position.setSIntroduction(company.getSIntroduction());
            mpGet.clear();
            mpGet.put("sPositionID", position.getId());
            int iCount = collectionDao.getCount(mpGet);
            position.setICollections(iCount);
            mpGet.clear();
            mpGet.put("id", position.getSDepartmentID());
            Department department = departmentDao.getDetail(mpGet);
            position.setSDepartmentName(department.getSDepartmentName());
            position.setSDepartmentDesc(department.getSDescription());
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPosition, arrPosition.size()));
    }

    @Override
    public QueryResponse getCount(String sHRID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sHRID)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //HR判断
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id", sHRID);
        User HR = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(HR)) {
            return new QueryResponse(PositionCode.HR_NOT_FOUND, null);
        }
        if (HR.getIRoleType() != 2) {
            return new QueryResponse(PositionCode.USER_IS_NOT_HR, null);
        }
        //查询
        mpParam.clear();
        mpParam.put("sHRID", sHRID);
        int iCount = positionDao.getCount(mpParam);
        List<Integer> arrCount = Collections.singletonList(iCount);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCount, arrCount.size()));
    }

    @Override
    public QueryResponse getListByCategoryName(Long lCurrentpage, Long lPageSize, Map<String, Object> mpParam) {
        String sCategoryName = (String) mpParam.get("sCategoryName");
        //参数判断
        if (!StringUtils.isNoneBlank(sCategoryName)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //根据分类名获取分类信息
        Category category = categoryDao.getDetail(mpParam);
        //根据分类查询职位信息
        mpParam.clear();
        mpParam.put("sCategoryID", category.getId());
        //分页
        PageUtils.initPaging(mpParam);
        //查询列表
        List<Position> arrPosition = positionDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Position position : arrPosition) {
            mpGet.put("id", position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            position.setSCompanyType(company.getSCompanyType());
            position.setSCompanyDescription(company.getSDescription());
            position.setSCompanyName(company.getSCompanyName());
            position.setSCompanyLogo(company.getSCompanyLogo());
            position.setSIntroduction(company.getSIntroduction());
            mpGet.clear();
            mpGet.put("sPositionID", position.getId());
            int iCount = collectionDao.getCount(mpGet);
            position.setICollections(iCount);
            mpGet.clear();
            mpGet.put("id", position.getSDepartmentID());
            Department department = departmentDao.getDetail(mpGet);
            position.setSDepartmentName(department.getSDepartmentName());
            position.setSDepartmentDesc(department.getSDescription());
            position.setSCategoryName(sCategoryName);
        }
        //分页
        PageInfo<Position> page = new PageInfo<Position>(arrPosition);
        List<PageInfo<Position>> arrPage = Collections.singletonList(page);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPage, arrPage.size()));
    }

    @Override
    public QueryResponse getByPage(Long lCurrentpage, Long lPageSize, Map<String, Object> mpParam) {
        mpParam.put("pageNumber", lCurrentpage);
        mpParam.put("pageSize", lPageSize);
        //分页
        PageUtils.initPaging(mpParam);
        //查询列表
        List<Position> arrPosition;
        String sCompanyName = (String) mpParam.get("sCompanyName");
        if (StringUtils.isNoneBlank(sCompanyName)) {
            String sCompanyID = companyDao.getIdByName(sCompanyName);
            mpParam.put("sCompanyID", sCompanyID);
        }
        if (StringUtils.isNoneBlank((String) mpParam.get("sHRID"))) {
            //根据HRID查询
            arrPosition = positionDao.getList(mpParam);
        } else {
            //基本查询
            if ("hot".equals(mpParam.get("sType"))) {
                arrPosition = positionDao.getHot(mpParam);
            } else if ("search".equals(mpParam.get("sType"))) {
                arrPosition = positionDao.getSearch(mpParam);
            } else {
                arrPosition = positionDao.getList(mpParam);
            }
        }
        Map<String, Object> mpGet = new HashMap<>();
        for (Position position : arrPosition) {
            mpGet.put("id", position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            position.setSCompanyType(company.getSCompanyType());
            position.setSCompanyDescription(company.getSDescription());
            position.setSCompanyName(company.getSCompanyName());
            position.setSCompanyLogo(company.getSCompanyLogo());
            position.setSIntroduction(company.getSIntroduction());
            mpGet.clear();
            mpGet.put("sPositionID", position.getId());
            int iCount = collectionDao.getCount(mpGet);
            position.setICollections(iCount);
            mpGet.clear();
            mpGet.put("id", position.getSDepartmentID());
            Department department = departmentDao.getDetail(mpGet);
            position.setSDepartmentName(department.getSDepartmentName());
            position.setSDepartmentDesc(department.getSDescription());
            mpGet.clear();
            mpGet.put("id", position.getSCategoryID());
            Category category = categoryDao.getDetail(mpGet);
            position.setSCategoryName(category.getSCategoryName());
        }
        //分页
        PageInfo<Position> page = new PageInfo<Position>(arrPosition);
        List<PageInfo<Position>> arrPage = Collections.singletonList(page);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPage, arrPage.size()));
    }

    @Override
    public CommonResponse updatePositionHot(String sPositionID, Integer iHot) {
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sPositionID", sPositionID);
        mpParam.put("iHot", iHot);
        int iResult = positionDao.updatePositionHot(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(PositionCode.UPDATE_HOT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}
