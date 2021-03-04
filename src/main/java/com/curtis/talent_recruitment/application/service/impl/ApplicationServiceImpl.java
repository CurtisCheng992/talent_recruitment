package com.curtis.talent_recruitment.application.service.impl;

import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.application.entity.Application;
import com.curtis.talent_recruitment.application.service.IApplicationService;
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

    /**
     * 查询所有申请
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<Application> arrApplication = applicationDao.getList();
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
            addApplication.getIStatus()!=null ||
            (addApplication.getIStatus()!=0 && addApplication.getIStatus()!=1)
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
}
