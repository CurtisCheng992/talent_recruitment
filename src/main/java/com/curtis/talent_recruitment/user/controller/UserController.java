package com.curtis.talent_recruitment.user.controller;

import com.curtis.talent_recruitment.api.user.UserControllerApi;
import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.request.user.AddHR;
import com.curtis.talent_recruitment.entity.request.user.AddUser;
import com.curtis.talent_recruitment.entity.request.user.RegisterUser;
import com.curtis.talent_recruitment.entity.request.user.UpdateUser;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.user.service.IUserService;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Curtis
 * @Description: 用户模块控制层
 * @Date: Created in 4:06 PM 2/23/2021
 */
@RestController
@RequestMapping("user")
public class UserController implements UserControllerApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return userService.getList();
    }

    /**
     * 根据id查询一个用户
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return userService.getDetail(id);
    }

    /**
     * 新增求职者用户
     *
     * @param addUser
     * @return
     */
    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddUser addUser) {
        return userService.add(addUser);
    }

    /**
     * 新增HR用户
     *
     * @param addHR
     * @return
     */
    @Override
    @PostMapping("addHR")
    public CommonResponse addHR(@RequestBody AddHR addHR) {
        return userService.addHR(addHR);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return userService.delete(id);
    }

    /**
     * 更新用户
     *
     * @param updateUser
     * @return
     */
    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateUser updateUser) {
        return userService.update(id,updateUser);
    }

    /**
     * 发送验证码
     *
     * @param sendType
     * @param codeType
     * @param loginUser
     * @return
     */
    @Override
    @PostMapping("code/{sendType}/{codeType}")
    public CommonResponse sendCode(@PathVariable int sendType, @PathVariable int codeType, @RequestBody LoginUser loginUser) {
        if (sendType !=1 && sendType !=2){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        return userService.sendCode(loginUser, sendType, codeType);
    }

    /**
     * 注册用户
     *
     * @param registerUser
     * @return
     */
    @Override
    @PostMapping("register")
    public CommonResponse register(@RequestBody RegisterUser registerUser) {
        return userService.register( registerUser );
    }

}
