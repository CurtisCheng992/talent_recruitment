package com.curtis.talent_recruitment.user.service.impl;

import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.category.dao.CategoryDao;
import com.curtis.talent_recruitment.category.entity.Category;
import com.curtis.talent_recruitment.collection.dao.CollectionDao;
import com.curtis.talent_recruitment.comment.dao.CommentDao;
import com.curtis.talent_recruitment.department.dao.UserDepartmentDao;
import com.curtis.talent_recruitment.entity.request.user.AddHR;
import com.curtis.talent_recruitment.entity.request.user.AddUser;
import com.curtis.talent_recruitment.entity.request.user.UpdateUser;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.user.UserCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.resume.dao.ResumeDao;
import com.curtis.talent_recruitment.school.dao.SchoolDao;
import com.curtis.talent_recruitment.school.entity.School;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.user.entity.User;
import com.curtis.talent_recruitment.user.service.IUserService;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.hs.commons.utils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:11 PM 2/23/2021
 */
@Service("UserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private UserDepartmentDao userDepartmentDao;

    @Autowired
    private CommentDao commentDao;

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<User> arrUser = userDao.getList();
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrUser, arrUser.size()));
    }

    /**
     * 根据id查询一个用户
     *
     * @param id
     * @return
     */
    @Override
    public QueryResponse getDetail(String id) {
        //主键id非空判断
        if (!StringUtils.isNoneBlank(id)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id", id);
        User user = userDao.getDetail(mpParam);
        List<User> arrUser = Collections.singletonList(user);
        //找不到用户
        if (ObjectUtils.isEmpty(user)) {
            return new QueryResponse(UserCode.USER_NOT_FOUND, null);
        }
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrUser, arrUser.size()));

    }

    /**
     * 新增求职者用户
     *
     * @param addUser
     * @return
     */
    @Override
    public CommonResponse add(AddUser addUser) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数对象非空判断
        if (ObjectUtils.isEmpty(addUser)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断sUsername,sPassword,sRealName,sPhone,sEmail,iGender是否为空
        if (!StringUtils.isNoneBlank(addUser.getSUsername()) ||
            !StringUtils.isNoneBlank(addUser.getSPassword()) ||
            !StringUtils.isNoneBlank(addUser.getSRealName()) ||
            !StringUtils.isNoneBlank(addUser.getSPhone()) ||
            !StringUtils.isNoneBlank(addUser.getSEmail()) ||
            addUser.getIGender() == null ||
            (addUser.getIGender()!=0 && addUser.getIGender()!=1)
        ){
            ExceptionThrowUtils.cast(UserCode.INVALID_PARAM);
        }
        //判断用户名是否存在
        mpParam.put("sUsername", addUser.getSUsername());
        User user_username = userDao.getDetail(mpParam);
        mpParam.clear();
        if (ObjectUtils.isNotEmpty(user_username)) {
            return new CommonResponse(UserCode.USERNAME_ALREADY_EXIST);
        }
        //TODO 邮箱与电话号码判断
        //新增求职者用户
        User user = new User();
        BeanUtils.copyProperties(addUser, user);
        user.setId(com.hs.commons.utils.StringUtils.getUUIDString());
        user.setIStatus(1);
        user.setIRoleType(2);
        Date date = new Date();
        user.setDCreateTime(date);
        user.setDUpdateTime(date);
        mpParam = ConvertUtils.objectToMap(user);
        int iResult = userDao.add(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(UserCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 新增HR用户
     *
     * @param addHR
     * @return
     */
    @Override
    public CommonResponse addHR(AddHR addHR) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addHR)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断sUsername,sPassword,sRealName,sPhone,sEmail,iGender是否为空
        if (!StringUtils.isNoneBlank(addHR.getSUsername()) ||
                !StringUtils.isNoneBlank(addHR.getSPassword()) ||
                !StringUtils.isNoneBlank(addHR.getSRealName()) ||
                !StringUtils.isNoneBlank(addHR.getSPhone()) ||
                !StringUtils.isNoneBlank(addHR.getSEmail()) ||
                addHR.getIGender() == null ||
                (addHR.getIGender()!=0 && addHR.getIGender()!=1)
        ){
            ExceptionThrowUtils.cast(UserCode.INVALID_PARAM);
        }
        //判断用户名是否存在
        mpParam.put("sUsername", addHR.getSUsername());
        User user_username = userDao.getDetail(mpParam);
        mpParam.clear();
        if (ObjectUtils.isNotEmpty(user_username)) {
            return new CommonResponse(UserCode.USERNAME_ALREADY_EXIST);
        }
        //TODO 邮箱与电话号码判断
        //新增HR用户
        User user = new User();
        BeanUtils.copyProperties(addHR, user);
        user.setId(com.hs.commons.utils.StringUtils.getUUIDString());
        user.setIStatus(1);
        user.setIRoleType(1);
        Date date = new Date();
        user.setDCreateTime(date);
        user.setDUpdateTime(date);
        mpParam = ConvertUtils.objectToMap(user);
        int iResult = userDao.add(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(UserCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个用户
     *
     * @param id
     * @return
     */
    @Override
    public CommonResponse delete(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!org.apache.commons.lang3.StringUtils.isNoneBlank()) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断该用户下是否存在简历，申请，收藏，评论，用户-部门
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sUserID",id);
        mpCheck.put("sHRID",id);
        int iCount = resumeDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(UserCode.DELETE_FAIL_RESUME_EXIST);
        }
        iCount = applicationDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(UserCode.DELETE_FAIL_APPLICATION_EXIST);
        }
        iCount = collectionDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(UserCode.DELETE_FAIL_COLLECTION_EXIST);
        }
        iCount = commentDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(UserCode.DELETE_FAIL_COMMENT_EXIST);
        }
        iCount = userDepartmentDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(UserCode.DELETE_FAIL_USER_DEPARTMENT_EXIST);
        }
        //先查询该用户是否存在
        mpParam.put("id", id);
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)) {
            return new CommonResponse(UserCode.USER_NOT_FOUND);
        }
        //删除用户
        int iResult = userDao.delete(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(UserCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 更新用户信息
     *
     * @param id
     * @param updateUser
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateUser updateUser) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id) && ObjectUtils.isEmpty(updateUser)) {
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sUsername,sPassword,sRealName,sPhone,sEmail,iGender,iStatus非空
        if (!StringUtils.isNoneBlank(updateUser.getSUsername()) ||
            !StringUtils.isNoneBlank(updateUser.getSPassword()) ||
            !StringUtils.isNoneBlank(updateUser.getSRealName()) ||
            !StringUtils.isNoneBlank(updateUser.getSPhone()) ||
            !StringUtils.isNoneBlank(updateUser.getSEmail()) ||
            updateUser.getIGender() == null || updateUser.getIStatus() == null ||
            (updateUser.getIGender()!=0 && updateUser.getIGender()!=1) ||
            (updateUser.getIStatus()!=0 && updateUser.getIStatus()!=1)
        ){
            ExceptionThrowUtils.cast(UserCode.INVALID_PARAM);
        }
        mpParam.put("id", id);
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)) {
            return new CommonResponse(UserCode.USER_NOT_FOUND);
        }
        mpParam.clear();
        //判断用户名是否重复
        //根据id查询用户是否存在,排除目前的用户名
        if (!user.getSUsername().equals(updateUser.getSUsername())){
            mpParam.put("sUsername",updateUser.getSUsername());
            int iCount = userDao.getCount(mpParam);
            if (iCount >0 ){
                return new CommonResponse(UserCode.USERNAME_ALREADY_EXIST);
            }
            mpParam.clear();
        }
        //判断sSchoolID是否在t_school里
        mpParam.put("id",updateUser.getSSchoolID());
        School school = schoolDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(school)){
            return new CommonResponse(UserCode.SCHOOL_NOT_FOUND);
        }
        mpParam.clear();
        //判断sDirection是否在t_Category里
        mpParam.put("id",updateUser.getSDirection());
        Category category = categoryDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(category)){
            return new CommonResponse(UserCode.CATEGORY_NOT_FOUND);
        }
        mpParam.clear();
        //更新用户信息
        mpParam = ConvertUtils.objectToMap(updateUser);
        mpParam.put("dUpdateTime", new Date());
        mpParam.put("id", id);
        int iResult = userDao.update(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(UserCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}
