package com.curtis.talent_recruitment.user.service;

import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.request.user.AddHR;
import com.curtis.talent_recruitment.entity.request.user.AddUser;
import com.curtis.talent_recruitment.entity.request.user.UpdateUser;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;

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
    QueryResponse getList();

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
}
