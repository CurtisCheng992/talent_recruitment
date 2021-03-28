package com.curtis.talent_recruitment.application.service.impl;

import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.application.entity.Application;
import com.curtis.talent_recruitment.application.service.IApplicationService;
import com.curtis.talent_recruitment.company.dao.CompanyDao;
import com.curtis.talent_recruitment.company.entity.Company;
import com.curtis.talent_recruitment.entity.request.application.AddApplication;
import com.curtis.talent_recruitment.entity.request.application.UpdateApplication;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.application.ApplicationCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.position.dao.PositionDao;
import com.curtis.talent_recruitment.position.entity.Position;
import com.curtis.talent_recruitment.resume.dao.ResumeDao;
import com.curtis.talent_recruitment.resume.entity.Resume;
import com.curtis.talent_recruitment.school.dao.SchoolDao;
import com.curtis.talent_recruitment.school.entity.School;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.user.entity.User;
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
 * @Date: Created in 7:12 PM 3/1/2021
 */
@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private SchoolDao schoolDao;

    /**
     * 查询所有申请
     * @return
     */
    @Override
    public QueryResponse getList(String sUserID, String sHRID) {
        Map<String, Object> mpParam = new HashMap<>();
        if (StringUtils.isNoneBlank(sUserID)){
            mpParam.put("sUserID",sUserID);
        }
        if (StringUtils.isNoneBlank(sHRID)){
            mpParam.put("sHRID",sHRID);
        }
        List<Application> arrApplication = applicationDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Application application : arrApplication) {
            mpGet.put("id",application.getSPositionID());
            Position position = positionDao.getDetail(mpGet);
            application.setSPositionName(position.getSPositionName());
            application.setSSalary(position.getSSalary());
            application.setSWorkCity(position.getSWorkCity());
            mpGet.put("id",application.getSHRID());
            User HR = userDao.getDetail(mpGet);
            application.setSHRName(HR.getSRealName());
            application.setSHREmail(HR.getSEmail());
            mpGet.put("id",position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            application.setSCompanyName(company.getSCompanyName());
            mpGet.put("id",application.getSUserID());
            User user = userDao.getDetail(mpGet);
            application.setSUserRealName(user.getSRealName());
            application.setSUserPhone(user.getSPhone());
            application.setSUserEmail(user.getSEmail());
            application.setIUserGender(user.getIGender());
            application.setSUserProvince(user.getSProvince());
            application.setSUserCity(user.getSCity());
            application.setIUserGraduationYear(user.getIGraduationYear());
            application.setSUserEducation(user.getSEducation());
            mpGet.put("id",user.getSSchoolID());
            School school = schoolDao.getDetail(mpGet);
            application.setSUserSchoolName(school.getSSchoolName());
        }
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrApplication, arrApplication.size()));
    }

    /**
     * 根据id查询一个申请信息
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
        //根据id查询一个申请信息
        mpParam.put("id",id);
        Application application = applicationDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(application)){
            return new QueryResponse(ApplicationCode.APPLICATION_NOT_FOUND,null);
        }
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("id",application.getSPositionID());
        Position position = positionDao.getDetail(mpGet);
        application.setSPositionName(position.getSPositionName());
        application.setSSalary(position.getSSalary());
        application.setSWorkCity(position.getSWorkCity());
        mpGet.put("id",application.getSHRID());
        User HR = userDao.getDetail(mpGet);
        application.setSHRName(HR.getSRealName());
        application.setSHREmail(HR.getSEmail());
        mpGet.put("id",position.getSCompanyID());
        Company company = companyDao.getDetail(mpGet);
        application.setSCompanyName(company.getSCompanyName());
        mpGet.put("id",application.getSUserID());
        User user = userDao.getDetail(mpGet);
        application.setSUserRealName(user.getSRealName());
        application.setSUserPhone(user.getSPhone());
        application.setSUserEmail(user.getSEmail());
        application.setIUserGender(user.getIGender());
        application.setSUserProvince(user.getSProvince());
        application.setSUserCity(user.getSCity());
        application.setIUserGraduationYear(user.getIGraduationYear());
        application.setSUserEducation(user.getSEducation());
        mpGet.put("id",user.getSSchoolID());
        School school = schoolDao.getDetail(mpGet);
        application.setSUserSchoolName(school.getSSchoolName());
        List<Application> arrApplication = new ArrayList<>();
        arrApplication.add(application);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrApplication, arrApplication.size()));
    }

    /**
     * 新增一个申请信息
     * @param addApplication
     * @return
     */
    @Override
    public CommonResponse add(AddApplication addApplication) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addApplication)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sResumeID,sPositionID,sHRID, iStatus非空判断
        if (
            !StringUtils.isNoneBlank(addApplication.getSResumeID()) ||
            !StringUtils.isNoneBlank(addApplication.getSPositionID()) ||
            !StringUtils.isNoneBlank(addApplication.getSHRID()) ||
            addApplication.getIStatus() == null ||
            (addApplication.getIStatus() !=0 && addApplication.getIStatus()!=1)
        ){
            ExceptionThrowUtils.cast(ApplicationCode.INVALID_PARAM);
        }
        //根据sResumeID判断该简历是否存在
        mpParam.put("id",addApplication.getSResumeID());
        Resume resume = resumeDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(resume)){
            return new CommonResponse(ApplicationCode.RESUME_NOT_FOUND);
        }
        mpParam.clear();
        //根据sPositionID判断该职位是否存在
        mpParam.put("id",addApplication.getSPositionID());
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(ApplicationCode.POSITION_NOT_FOUND);
        }
        mpParam.clear();
        //根据sHRID判断该HR用户是否存在
        mpParam.put("id",addApplication.getSHRID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(ApplicationCode.HR_NOT_FOUND);
        }
        mpParam.clear();
        //添加一个申请信息
        mpParam = ConvertUtils.objectToMap(addApplication);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        int iResult = applicationDao.add(mpParam);
        if (iResult <= 0){
            return new CommonResponse(ApplicationCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个申请信息
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
        //根据id查询该申请是否存在
        mpParam.put("id",id);
        Application application = applicationDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(application)){
            return new CommonResponse(ApplicationCode.APPLICATION_NOT_FOUND);
        }
        //根据id删除一个申请
        int iResult = applicationDao.delete(mpParam);
        if (iResult <= 0){
            return new CommonResponse(ApplicationCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个申请信息
     * @param id
     * @param updateApplication
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateApplication updateApplication) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updateApplication) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sResumeID,sPositionID,sHRID, iStatus非空判断
        if (
                !StringUtils.isNoneBlank(updateApplication.getSResumeID()) ||
                !StringUtils.isNoneBlank(updateApplication.getSPositionID()) ||
                !StringUtils.isNoneBlank(updateApplication.getSHRID()) ||
                updateApplication.getIStatus()!=null ||
                (updateApplication.getIStatus()!=0 && updateApplication.getIStatus()!=1)
        ){
            ExceptionThrowUtils.cast(ApplicationCode.INVALID_PARAM);
        }
        //根据sResumeID判断该简历是否存在
        mpParam.put("id",updateApplication.getSResumeID());
        Resume resume = resumeDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(resume)){
            return new CommonResponse(ApplicationCode.RESUME_NOT_FOUND);
        }
        mpParam.clear();
        //根据sPositionID判断该职位是否存在
        mpParam.put("id",updateApplication.getSPositionID());
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(ApplicationCode.POSITION_NOT_FOUND);
        }
        mpParam.clear();
        //根据sHRID判断该HR用户是否存在
        mpParam.put("id",updateApplication.getSHRID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(ApplicationCode.HR_NOT_FOUND);
        }
        mpParam.clear();
        //根据id查询判断该申请是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Application application = applicationDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(application)){
            return new CommonResponse(ApplicationCode.APPLICATION_NOT_FOUND);
        }
        //根据id修改申请信息
        mpParam = ConvertUtils.objectToMap(updateApplication);
        mpParam.put("id",id);
        mpParam.put("dUpdateTime",new Date());
        int iResult = applicationDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(ApplicationCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据用户id查询一个申请信息
     *
     * @param sUserID
     * @return
     */
    @Override
    public QueryResponse getListByUserID(String sUserID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sUserID)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //用户判断
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",sUserID);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new QueryResponse(ApplicationCode.USER_NOT_FOUND,null);
        }
        //查询
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("sUserID",sUserID);
        List<Application> arrApplication = applicationDao.getListByUserID(mpGet);
        for (Application application : arrApplication) {
            mpGet.clear();
            mpGet.put("id",application.getSPositionID());
            Position position = positionDao.getDetail(mpGet);
            application.setSPositionName(position.getSPositionName());
            application.setSSalary(position.getSSalary());
            application.setSWorkCity(position.getSWorkCity());
            mpGet.put("id",application.getSHRID());
            User HR = userDao.getDetail(mpGet);
            application.setSHRName(HR.getSRealName());
            application.setSHREmail(HR.getSEmail());
            mpGet.put("id",position.getSCompanyID());
            Company company = companyDao.getDetail(mpGet);
            application.setSCompanyName(company.getSCompanyName());
            mpGet.put("id",application.getSUserID());
            application.setSUserRealName(user.getSRealName());
            application.setSUserPhone(user.getSPhone());
            application.setSUserEmail(user.getSEmail());
            application.setIUserGender(user.getIGender());
            application.setSUserProvince(user.getSProvince());
            application.setSUserCity(user.getSCity());
            application.setIUserGraduationYear(user.getIGraduationYear());
            application.setSUserEducation(user.getSEducation());
            mpGet.put("id",user.getSSchoolID());
            School school = schoolDao.getDetail(mpGet);
            application.setSUserSchoolName(school.getSSchoolName());
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrApplication, arrApplication.size()));
    }

    /**
     * 根据用户id查询申请信息记录条数
     *
     * @param sUserID
     * @return
     */
    @Override
    public QueryResponse getCountByUserID(String sUserID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sUserID)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //用户判断
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",sUserID);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new QueryResponse(ApplicationCode.USER_NOT_FOUND,null);
        }
        //根据用户id查询申请信息记录条数
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("sUserID",sUserID);
        int iCount = applicationDao.getCount(mpGet);
        List<Integer> arrCount = Collections.singletonList(iCount);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCount, arrCount.size()));
    }

    @Override
    public QueryResponse getCount(String sPositionID, String sUserID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sPositionID) ||
            !StringUtils.isNoneBlank(sUserID)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //职位判断
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",sPositionID);
        Position position = positionDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(position)){
            return new QueryResponse(ApplicationCode.POSITION_NOT_FOUND, null);
        }
        //用户判断
        mpCheck.put("id",sUserID);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new QueryResponse(ApplicationCode.USER_NOT_FOUND, null);
        }
        //判断该用户是否申请过此职位
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sPositionID",sPositionID);
        mpParam.put("sUserID",sUserID);
        int iCount = applicationDao.getCount(mpParam);
        List<Integer> arrCount = Collections.singletonList(iCount);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCount, arrCount.size()));
    }

    @Override
    public CommonResponse deleteByCondition(String sPositionID, String sUserID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sPositionID) ||
            !StringUtils.isNoneBlank(sUserID)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //职位判断
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",sPositionID);
        Position position = positionDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(position)){
            return new QueryResponse(ApplicationCode.POSITION_NOT_FOUND, null);
        }
        //用户判断
        mpCheck.put("id",sUserID);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new QueryResponse(ApplicationCode.USER_NOT_FOUND, null);
        }
        //删除
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sPositionID",sPositionID);
        mpParam.put("sUserID",sUserID);
        int iResult = applicationDao.deleteByCondition(mpParam);
        if (iResult <= 0 ){
            return new CommonResponse(ApplicationCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * HR根据申请id完成审核
     *
     * @param sHRID
     * @param id
     * @return
     */
    @Override
    public CommonResponse verify(String sHRID, String id) {
        //参数非空
        if (!StringUtils.isNoneBlank(sHRID) ||
            !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //id验证
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Application application = applicationDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(application)){
            return new CommonResponse(ApplicationCode.APPLICATION_NOT_FOUND);
        }
        //hr验证
        if (!application.getSHRID().equals(sHRID)){
            return new CommonResponse(ApplicationCode.NOT_THE_HR_OF_APPLICATION);
        }
        //审核申请
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id",id);
        int iResult = applicationDao.verify(mpParam);
        if (iResult <= 0){
            return new CommonResponse(ApplicationCode.VERIFY_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    @Override
    public QueryResponse getCountByHR(String sHRID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sHRID)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //HR判断
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id",sHRID);
        User HR = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(HR)){
            return new QueryResponse(ApplicationCode.HR_NOT_FOUND, null);
        }
        if (HR.getIRoleType() != 2){
            return new QueryResponse(ApplicationCode.USER_IS_NOT_HR,null);
        }
        mpParam.clear();
        mpParam.put("sHRID",sHRID);
        int iCount = applicationDao.getCount(mpParam);
        List<Integer> arrCount = Collections.singletonList(iCount);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCount, arrCount.size()));
    }
}
