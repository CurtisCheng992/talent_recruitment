package com.curtis.talent_recruitment.user.service;

import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.request.user.*;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:11 PM 2/23/2021
 */
public interface IUserService {

    /**
     * 查询所有用户
     *
     * @return
     */
    QueryResponse getList(Integer iRoleType);

    /**
     * 根据id查询一个用户
     *
     * @param id
     * @return
     */
    QueryResponse getDetail(String id);

    /**
     * 新增求职者用户
     *
     * @param addUser
     * @return
     */
    CommonResponse add(AddUser addUser);

    /**
     * 新增HR用户
     *
     * @param addHR
     * @return
     */
    CommonResponse addHR(AddHR addHR);

    /**
     * 根据id删除一个用户
     *
     * @param id
     * @return
     */
    CommonResponse delete(String id);

    /**
     * 更新用户信息
     *
     * @param id
     * @param updateUser
     * @return
     */
    CommonResponse update(String id, UpdateUser updateUser);

    /**
     * 发送验证码
     *
     * @param loginUser
     * @param sendType
     * @param codeType
     * @return
     */
    CommonResponse sendCode(LoginUser loginUser, int sendType, int codeType);

    /**
     * 注册用户
     *
     * @param registerUser
     * @return
     */
    CommonResponse register(RegisterUser registerUser);

    /**
     * 用户更新头像
     *
     * @param id
     * @param sAvatar
     * @param request
     * @param response
     * @return
     */
    CommonResponse updateAvatar(String id, String sAvatar, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据条件分页查询
     *
     * @param lCurrentPage
     * @param lPageSize
     * @param mpParam
     * @return
     */
    QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam);

    /**
     * 修改邮箱
     *
     * @param id
     * @param emailUser
     * @return
     */
    CommonResponse updateEmail(String id, EmailUser emailUser);

    /**
     * 修改手机号码
     *
     * @param id
     * @param phoneUser
     * @return
     */
    CommonResponse updatePhone(String id, PhoneUser phoneUser);

    /**
     * 根据用户id查询用户邮箱地址
     *
     * @param id
     * @return
     */
    QueryResponse getEmailById(String id);

    /**
     * 修改用户密码
     *
     * @param id
     * @param passwordUser
     * @return
     */
    CommonResponse updatePassword(String id, PasswordUser passwordUser);
}
