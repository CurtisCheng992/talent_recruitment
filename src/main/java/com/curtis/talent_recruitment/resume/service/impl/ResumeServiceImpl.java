package com.curtis.talent_recruitment.resume.service.impl;

import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.entity.request.resume.AddResume;
import com.curtis.talent_recruitment.entity.request.resume.UpdateResume;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.resume.ResumeCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.resume.dao.ResumeDao;
import com.curtis.talent_recruitment.resume.entity.Resume;
import com.curtis.talent_recruitment.resume.service.IResumeService;
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
 * @Date: Created in 6:03 PM 3/1/2021
 */
@Service
public class ResumeServiceImpl implements IResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationDao applicationDao;

    /**
     * 查询所有简历信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        Map<String, Object> mpParam = new HashMap<>();
        List<Resume> arrResume = resumeDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Resume resume : arrResume) {
            mpGet.put("id",resume.getSUserID());
            User user = userDao.getDetail(mpGet);
            resume.setSRealName(user.getSRealName());
            resume.setSPhone(user.getSPhone());
            resume.setSEmail(user.getSEmail());
            resume.setIGender(user.getIGender());
            resume.setIAge(user.getIAge());
            resume.setSProvince(user.getSProvince());
            resume.setSCity(user.getSCity());
            resume.setIGraduationYear(user.getIGraduationYear());
            resume.setSMajor(user.getSMajor());
            resume.setSEducation(user.getSEducation());
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrResume,arrResume.size()));
    }

    /**
     * 根据id查询一个简历信息
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
        //根据id查询简历信息
        mpParam.put("id",id);
        Resume resume = resumeDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(resume)){
            return new QueryResponse(ResumeCode.RESUME_NOT_FOUND,null);
        }
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("id",resume.getSUserID());
        User user = userDao.getDetail(mpGet);
        resume.setSRealName(user.getSRealName());
        resume.setSPhone(user.getSPhone());
        resume.setSEmail(user.getSEmail());
        resume.setIGender(user.getIGender());
        resume.setIAge(user.getIAge());
        resume.setSProvince(user.getSProvince());
        resume.setSCity(user.getSCity());
        resume.setIGraduationYear(user.getIGraduationYear());
        resume.setSMajor(user.getSMajor());
        resume.setSEducation(user.getSEducation());
        List<Resume> arrResume = new ArrayList<>();
        arrResume.add(resume);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrResume, arrResume.size()));
    }

    /**
     * 新增一个简历信息
     * @param addResume
     * @return
     */
    @Override
    public CommonResponse add(AddResume addResume) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addResume)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数用户id非空判断
        if (!StringUtils.isNoneBlank(addResume.getSUserID())){
            return new CommonResponse(ResumeCode.USERID_CANT_BE_NULL);
        }
        //根据sUserID判断该用户是否存在
        mpParam.put("id",addResume.getSUserID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(ResumeCode.USER_NOT_FOUND);
        }
        mpParam.clear();
        //添加一个简历信息
        mpParam = ConvertUtils.objectToMap(addResume);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        int iResult = resumeDao.add(mpParam);
        if (iResult <= 0){
            return new CommonResponse(ResumeCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个简历信息
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
        //判断该简历下是否存在申请
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sResumeID",id);
        int iCount = applicationDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(ResumeCode.DELETE_FAIL_APPLICATION_EXIST);
        }
        //根据id查询该简历是否存在
        mpParam.put("id",id);
        Resume resume = resumeDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(resume)){
            return new CommonResponse(ResumeCode.RESUME_NOT_FOUND);
        }
        //根据id删除简历
        int iResult = resumeDao.delete(mpParam);
        if (iResult <= 0){
            return new CommonResponse(ResumeCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个简历信息
     * @param id
     * @param updateResume
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateResume updateResume) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updateResume) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数用户id非空判断
        if (!StringUtils.isNoneBlank(updateResume.getSUserID())){
            return new CommonResponse(ResumeCode.USERID_CANT_BE_NULL);
        }
        //根据sUserID判断该用户是否存在
        mpParam.put("id",updateResume.getSUserID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(ResumeCode.USER_NOT_FOUND);
        }
        //根据id查询该简历是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Resume resume = resumeDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(resume)){
            return new CommonResponse(ResumeCode.RESUME_NOT_FOUND);
        }
        //根据id更新简历
        mpParam = ConvertUtils.objectToMap(updateResume);
        mpParam.put("id",id);
        mpParam.put("dUpdateTime", new Date());
        int iResult = resumeDao.update(mpParam);
        if (iResult <=0 ){
            return new CommonResponse(ResumeCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据用户id查询一个简历信息
     *
     * @param sUserID
     * @return
     */
    @Override
    public QueryResponse getByUserID(String sUserID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sUserID)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断该用户id是否合法
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",sUserID);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new QueryResponse(ResumeCode.USER_NOT_FOUND,null);
        }
        //查询简历信息
        Map<String, Object> mpGet = new HashMap<>();
        mpGet.put("sUserID",sUserID);
        Resume resume = resumeDao.getByUserID(mpGet);
        if (ObjectUtils.isEmpty(resume)){
            return new QueryResponse(ResumeCode.RESUME_NOT_FOUND, null);
        }
        mpGet.put("id",resume.getSUserID());
        resume.setSRealName(user.getSRealName());
        resume.setSPhone(user.getSPhone());
        resume.setSEmail(user.getSEmail());
        resume.setIGender(user.getIGender());
        resume.setIAge(user.getIAge());
        resume.setSProvince(user.getSProvince());
        resume.setSCity(user.getSCity());
        resume.setIGraduationYear(user.getIGraduationYear());
        resume.setSMajor(user.getSMajor());
        resume.setSEducation(user.getSEducation());
        List<Resume> arrResume = Collections.singletonList(resume);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrResume, arrResume.size()));
    }

    @Override
    public CommonResponse updatePicture(String id, String sPicture) {
        //参数非空判断
        if (StringUtils.isBlank(sPicture)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        sPicture = sPicture.split("\"")[3];
        //判断简历
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id",id);
        Resume resume = resumeDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(resume)){
            return new CommonResponse(ResumeCode.RESUME_NOT_FOUND);
        }
        //更新人脸图片
        mpParam.clear();
        mpParam.put("sPicture",sPicture);
        mpParam.put("id",id);
        int iCount = resumeDao.updatePicture(mpParam);
        if (iCount <= 0){
            return new CommonResponse(ResumeCode.UPDATE_FAIL);
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
        List<Resume> arrResume = resumeDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (Resume resume : arrResume) {
            mpGet.put("id",resume.getSUserID());
            User user = userDao.getDetail(mpGet);
            resume.setSRealName(user.getSRealName());
            resume.setSPhone(user.getSPhone());
            resume.setSEmail(user.getSEmail());
            resume.setIGender(user.getIGender());
            resume.setIAge(user.getIAge());
            resume.setSProvince(user.getSProvince());
            resume.setSCity(user.getSCity());
            resume.setIGraduationYear(user.getIGraduationYear());
            resume.setSMajor(user.getSMajor());
            resume.setSEducation(user.getSEducation());
        }
        //分页
        PageInfo<Resume> page = new PageInfo<>(arrResume);
        List<PageInfo<Resume>> arrPage = Collections.singletonList(page);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPage, arrPage.size()));
    }

    @Override
    public QueryResponse getCount(String sUserID) {
        //参数判断
        if (StringUtils.isBlank(sUserID)){
            ExceptionThrowUtils.cast(CommonCode.SUCCESS);
        }
        //用户判断
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id",sUserID);
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new QueryResponse(ResumeCode.USER_NOT_FOUND, null);
        }
        //查询统计
        mpParam.put("sUserID",sUserID);
        int iCount = resumeDao.getCount(mpParam);
        List<Integer> arrCount = Collections.singletonList(iCount);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCount, arrCount.size()));
    }
}
