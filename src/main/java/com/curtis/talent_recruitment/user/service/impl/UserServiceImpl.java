package com.curtis.talent_recruitment.user.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.auth.entity.UserInfo;
import com.curtis.talent_recruitment.category.dao.CategoryDao;
import com.curtis.talent_recruitment.category.entity.Category;
import com.curtis.talent_recruitment.collection.dao.CollectionDao;
import com.curtis.talent_recruitment.department.dao.UserDepartmentDao;
import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.request.user.*;
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
import com.curtis.talent_recruitment.utils.auth.CookieUtils;
import com.curtis.talent_recruitment.utils.auth.JwtConfig;
import com.curtis.talent_recruitment.utils.auth.JwtUtils;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.curtis.talent_recruitment.utils.user.*;
import com.github.pagehelper.PageInfo;
import com.hs.commons.utils.ConvertUtils;
import com.hs.commons.utils.PageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private MailUtils mailUtils;

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private JwtConfig config;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger( UserServiceImpl.class );

    private static final String LOGIN_KEY_PREFIX = "user:code:login:";

    private static final String REGISTER_KEY_PREFIX = "user:code:register";

    private static final String MODIFY_KEY_PREFIX = "user:code:modify:";

    private static final String CHANGE_KEY_PREFIX = "user:code:change:";

    private final List<Integer> SEND_TYPE = new ArrayList<>(Arrays.asList( 1, 2 ));

    private final List<Long> ROLE_ID_LIST = new ArrayList<>(Arrays.asList( 1L, 2L, 3L ));

    private final List<Integer> CODE_TYPE = new ArrayList<>(Arrays.asList( 1, 2, 3, 4 ));

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public QueryResponse getList(Integer iRoleType) {
        Map<String, Object> mpParam = new HashMap<>();
        if (iRoleType ==1 || iRoleType == 2 || iRoleType == 3){
            mpParam.put("iRoleType",iRoleType);
        }
        List<User> arrUser = userDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (User user : arrUser) {
            if (StringUtils.isNoneBlank(user.getSSchoolID())){
                mpGet.put("id",user.getSSchoolID());
                School school = schoolDao.getDetail(mpGet);
                user.setSSchoolName(school.getSSchoolName());
            }
            if (StringUtils.isNoneBlank(user.getSDirection())){
                mpGet.put("id",user.getSDirection());
                Category category = categoryDao.getDetail(mpGet);
                user.setSDirectionName(category.getSCategoryName());
            }
        }
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
        Map<String, Object> mpGet = new HashMap<>();
        if (StringUtils.isNoneBlank(user.getSSchoolID())){
            mpGet.put("id",user.getSSchoolID());
            School school = schoolDao.getDetail(mpGet);
            user.setSSchoolName(school.getSSchoolName());
        }
        if (StringUtils.isNoneBlank(user.getSDirection())){
            mpGet.put("id",user.getSDirection());
            Category category = categoryDao.getDetail(mpGet);
            user.setSDirectionName(category.getSCategoryName());
        }
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
        //判断sUsername,sPassword,sRealName,sEmail,iGender是否为空
        if (!StringUtils.isNoneBlank(addUser.getSUsername()) ||
            !StringUtils.isNoneBlank(addUser.getSPassword()) ||
            !StringUtils.isNoneBlank(addUser.getSRealName()) ||
            !StringUtils.isNoneBlank(addUser.getSEmail())
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
        //学校转换
        if (StringUtils.isNoneBlank(addUser.getSSchoolName())){
            Map<String, Object> mpSchool = new HashMap<>();
            mpSchool.put("sSchoolName", addUser.getSSchoolName());
            School school = schoolDao.getDetail(mpSchool);
            addUser.setSSchoolID(school.getId());
        }else{
            addUser.setSSchoolID(null);
        }
        //新增求职者用户
        User user = new User();
        BeanUtils.copyProperties(addUser, user);
        user.setId(com.hs.commons.utils.StringUtils.getUUIDString());
        user.setIStatus(1);
        user.setIRoleType(3);
        Date date = new Date();
        user.setDCreateTime(date);
        user.setDUpdateTime(date);
        user.setSAvatar("http://182.254.148.75/group1/M00/00/00/rBEABGBhgYmAeGj-AAANQYGm1Cs616.jpg");
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
        //判断sUsername,sPassword,sRealName,sEmail,iGender是否为空
        if (!StringUtils.isNoneBlank(addHR.getSUsername()) ||
                !StringUtils.isNoneBlank(addHR.getSPassword()) ||
                !StringUtils.isNoneBlank(addHR.getSRealName()) ||
                !StringUtils.isNoneBlank(addHR.getSEmail())
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
        //新增HR用户
        User user = new User();
        BeanUtils.copyProperties(addHR, user);
        user.setId(com.hs.commons.utils.StringUtils.getUUIDString());
        user.setIStatus(1);
        user.setIRoleType(2);
        Date date = new Date();
        user.setDCreateTime(date);
        user.setDUpdateTime(date);
        user.setSAvatar("http://182.254.148.75/group1/M00/00/00/rBEABGBhgYmAeGj-AAANQYGm1Cs616.jpg");
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
        //更新用户状态
        int iStatus = 0;
        if (user.getIStatus() == 0){
            iStatus = 1;
        }else if (user.getIStatus() == 1){
            iStatus = 0;
        }
        Map<String, Object> mpStatus = new HashMap<>();
        mpStatus.put("id",user.getId());
        mpStatus.put("iStatus",iStatus);
        if (user.getIRoleType() == 1) { // 超级管理员不允许被调用接口注销
            return CommonResponse.FAIL();
        }
        int iResult = userDao.updateStatusById(mpStatus);
//        int iResult = userDao.delete(mpParam);
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
            updateUser.getIStatus() == null ||
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
        if (StringUtils.isNoneBlank(updateUser.getSSchoolName())){
            mpParam.put("sSchoolName",updateUser.getSSchoolName());
            School school = schoolDao.getDetail(mpParam);
            if (ObjectUtils.isEmpty(school)){
                return new CommonResponse(UserCode.SCHOOL_NOT_FOUND);
            }
            updateUser.setSSchoolID(school.getId());
            mpParam.clear();
        }
        //判断sDirection是否在t_Category里
        if (StringUtils.isNoneBlank(updateUser.getSDirectionName())){
            mpParam.put("sCategoryName",updateUser.getSDirectionName());
            Category category = categoryDao.getDetail(mpParam);
            if (ObjectUtils.isEmpty(category)){
                return new CommonResponse(UserCode.CATEGORY_NOT_FOUND);
            }
            updateUser.setSDirection(category.getId());
            mpParam.clear();
        }
        //更新用户信息
        mpParam = ConvertUtils.objectToMap(updateUser);
        mpParam.put("dUpdateTime", new Date());
        mpParam.put("id", id);
        if (updateUser.getBChangePassword() == false){
            mpParam.remove("sPassword");
        }
        int iResult = userDao.update(mpParam);
        if (iResult <= 0) {
            return new CommonResponse(UserCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 发送验证码
     *
     * @param loginUser
     * @param sendType
     * @param codeType
     * @return
     */
    @Override
    public CommonResponse sendCode(LoginUser loginUser, int sendType, int codeType) {
        //登录实体类或者sendType、codeType参数验证
        if (ObjectUtils.isEmpty(loginUser) || !SEND_TYPE.contains( sendType ) || !CODE_TYPE.contains( codeType )){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }

        User user;

        //生成六位数字验证码
        String code = NumberUtils.generateCode(6);
        Map<String, Object> mpParam = new HashMap<>();

        switch (sendType){
            case 1: //发送到手机
                //phone为非法参数
                if(!ParamCheckUtils.checkPhone(loginUser.getSPhone())){
                    return new CommonResponse(CommonCode.INVALID_PARAM);
                }
                mpParam.clear();
                mpParam.put("sPhone",loginUser.getSPhone());
                user = userDao.getDetail(mpParam);
                //如果是因登录和修改密码要求发送验证码到手机，先确认该账号已经注册过
                if ((codeType == 1 || codeType ==3)&& user == null){
                    return new CommonResponse(UserCode.PHONE_NOT_REGISTERED);
                }
                //调用工具类发送验证码到手机
                try{
                    smsUtils.sendSms(loginUser.getSPhone(), code, smsConfig.getSignName(), smsConfig.getVerifyCodeTemplate());
                }catch (ClientException e){
                    LOGGER.error("发送验证码到用户手机异常！异常原因：{}",e);
                    ExceptionThrowUtils.cast(CommonCode.SERVER_ERROR);
                }
                break;
            case 2: //发送到邮箱
                //email为非法参数
                if (!ParamCheckUtils.checkEmail(loginUser.getSEmail())){
                    return new CommonResponse(CommonCode.INVALID_PARAM);
                }
                mpParam.clear();
                mpParam.put("sEmail", loginUser.getSEmail());
                user = userDao.getDetail(mpParam);
                //如果是因为登录和修改密码要求发送验证码到邮箱，先确认该账号已经注册过
                if ((codeType == 1 || codeType ==3)&& user == null){
                    return new CommonResponse(UserCode.EMAIL_NOT_REGISTERED);
                }
                // 如果是因注册和换绑邮箱要求发送验证码到邮箱，先确认该账号尚未注册过
                if ((codeType == 2 || codeType == 4) && user != null) {
                    return new CommonResponse( UserCode.EMAIL_HAS_BEEN_REGISTERED );
                }
                try {
                    // 发送验证码
                    mailUtils.sendMail( loginUser.getSEmail(), "【人才招聘系统】验证码",
                            "【人才招聘系统】您的验证码是" + code + "，用于验证身份、修改密码等，该验证码5分钟内有效，请勿向他人泄露。" );
                } catch (Exception e) {
                    LOGGER.error( "发送验证码到用户邮箱地址异常！异常原因：{}", e );
                    ExceptionThrowUtils.cast( CommonCode.SERVER_ERROR );
                }
        }

        // 将验证码存入 redis ，并设置过期时间为 5 分钟
        String key = sendType == 1 ? loginUser.getSPhone() : loginUser.getSEmail();
        switch (codeType) {
            case 1: // 登录验证码
                this.redisTemplate.opsForValue().set( LOGIN_KEY_PREFIX + key, code, 5, TimeUnit.MINUTES );
                break;
            case 2: // 注册验证码
                this.redisTemplate.opsForValue().set( REGISTER_KEY_PREFIX + key, code, 5, TimeUnit.MINUTES );
                break;
            case 3: // 修改密码验证码
                this.redisTemplate.opsForValue().set( MODIFY_KEY_PREFIX + key, code, 5, TimeUnit.MINUTES );
                break;
            case 4: // 换绑邮箱/手机验证码
                this.redisTemplate.opsForValue().set( CHANGE_KEY_PREFIX + key, code, 5, TimeUnit.MINUTES );
                break;
        }
        return new CommonResponse( CommonCode.SUCCESS );
    }

    /**
     * 注册用户
     *
     * @param registerUser
     * @return
     */
    @Override
    @Transactional
    public CommonResponse register(RegisterUser registerUser) {
        if (ObjectUtils.isEmpty(registerUser)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //邮箱检验
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sEmail",registerUser.getSEmail());
        if (!ObjectUtils.isEmpty(userDao.getDetail(mpParam))){
            return new CommonResponse(UserCode.EMAIL_HAS_BEEN_REGISTERED);
        }
        //验证码检验
        if (!StringUtils.equals(registerUser.getCode(),this.redisTemplate.opsForValue().get(REGISTER_KEY_PREFIX+registerUser.getSEmail()))){
            return new CommonResponse(UserCode.REGISTER_FAIL_CODE_WRONG);
        }
        //用户名验证
        mpParam.clear();
        mpParam.put("sUsername",registerUser.getSUsername());
        User foundUser = userDao.getDetail(mpParam);
        if (!ObjectUtils.isEmpty(foundUser)){
            return new CommonResponse(UserCode.REGISTER_FAIL_USERNAME_CONFLICT);
        }
        //角色类型验证
        if (registerUser.getIRoleType()!=1 && registerUser.getIRoleType()!=2 && registerUser.getIRoleType()!=3){
            return new CommonResponse(UserCode.REGISTER_FAIL_ROLETYPE_NOT_FOUND);
        }
        //将用户存入数据库
        User user = User.builder().id(com.hs.commons.utils.StringUtils.getUUIDString())
                .sUsername(registerUser.getSUsername())
                .sPassword(registerUser.getSPassword())
                .sRealName(registerUser.getSRealName())
                .sPhone(registerUser.getSEmail())
                .sEmail(registerUser.getSEmail())
                .iGender(registerUser.getIGender())
                .iAge(registerUser.getIAge())
                .sAvatar(registerUser.getSAvatar())
                .sProvince(registerUser.getSProvince())
                .sCity(registerUser.getSCity())
                .iGraduationYear(registerUser.getIGraduationYear())
                .sMajor(registerUser.getSMajor())
                .sEducation(registerUser.getSEducation())
                .sSchoolID(registerUser.getSSchoolID())
                .iRoleType(registerUser.getIRoleType())
                .iStatus(1)
                .sDirection(registerUser.getSDirection())
                .sDescription(registerUser.getSDescription())
                .dCreateTime(new Date())
                .dUpdateTime(new Date())
                .build();
        Map<String, Object> mpAdd = ConvertUtils.objectToMap(user);
        int iResultAdd = userDao.add(mpAdd);

        registerCount();

        return new CommonResponse(CommonCode.SUCCESS);
    }

    @Override
    public CommonResponse updateAvatar(String id, String sAvatar, HttpServletRequest request, HttpServletResponse response) {
        //参数非空判断
        if (StringUtils.isBlank(sAvatar)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //修改用户头像
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        User user = userDao.getDetail(mpCheck);
        user.setSAvatar(sAvatar);
        user.setDUpdateTime(new Date());
        Map<String, Object> mpParam = ConvertUtils.objectToMap(user);
        int iResult = userDao.update(mpParam);

        // 重新生成 token 信息并写回
        String cookieName = "37e5efd7f4914c32b8f4721943977f08".equals(id) ? config.getAdminCookieName() : config.getUserCookieName();
        String token = CookieUtils.getCookieValue(request, cookieName);
        try {
            // 重新生成 token 信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, config.getPublicKey());
            String newToken = JwtUtils.generateToken(new UserInfo(userInfo.getId(), userInfo.getSUsername(),
                            userInfo.getIsHR(), sAvatar, userInfo.getBRememberMe(), userInfo.getIStatus()),
                    config.getPrivateKey(), config.getExpire());
            // 重新写入cookie
            CookieUtils.setCookie(request, response, cookieName, newToken,
                    // 如果用户勾选了记住我，则将 cookie 存活时间设置为 7 天
                    userInfo.getBRememberMe() ? 14 * config.getCookieMaxAge() : config.getCookieMaxAge(),
                    null, true);
        } catch (Exception e) {
            LOGGER.error("解析token信息异常！异常原因：{}", e);
            return new CommonResponse(CommonCode.SERVER_ERROR);
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
        String sRoleName = (String) mpParam.get("sRoleName");
        if (StringUtils.isNoneBlank(sRoleName)){
            if ("管理员".equals(sRoleName)){
                mpParam.put("iRoleType",1);
            }else if ("HR".equals(sRoleName)){
                mpParam.put("iRoleType",2);
            }else if("求职者".equals(sRoleName)){
                mpParam.put("iRoleType",3);
            }else if("全部".equals(sRoleName)){
                mpParam.put("iRoleType","");
            }
        }
        String sGender = (String) mpParam.get("iGender");
        if ("1".equals(sGender) || "0".equals(sGender)){
            Integer iGender = Integer.parseInt(sGender);
            if (iGender != null){
                if (iGender != 0 && iGender != 1){
                    mpParam.put("iGender","");
                }
            }
        }else{
            mpParam.put("iGender","");
        }
        List<User> arrUser = userDao.getList(mpParam);
        Map<String, Object> mpGet = new HashMap<>();
        for (User user : arrUser) {
            if (StringUtils.isNoneBlank(user.getSSchoolID())){
                mpGet.put("id",user.getSSchoolID());
                School school = schoolDao.getDetail(mpGet);
                user.setSSchoolName(school.getSSchoolName());
            }
            if (StringUtils.isNoneBlank(user.getSDirection())){
                mpGet.put("id",user.getSDirection());
                Category category = categoryDao.getDetail(mpGet);
                user.setSDirectionName(category.getSCategoryName());
            }
        }
        //分页
        PageInfo<User> page = new PageInfo<>(arrUser);
        List<PageInfo<User>> arrPage = Collections.singletonList(page);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPage, arrPage.size()));
    }

    @Override
    public CommonResponse updateEmail(String id, EmailUser emailUser) {
        //参数验证
        if ((ObjectUtils.isEmpty(emailUser)) || (StringUtils.isBlank(id))){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //新邮箱校验
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sEmail",emailUser.getSEmail());
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isNotEmpty(user)){
            return new CommonResponse(UserCode.UPDATE_EMAIL_FAIL_EMAIL_ALREADY_EXISTS);
        }
        //用户id验证
        mpCheck.clear();
        mpCheck.put("id",id);
        user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(UserCode.USER_NOT_FOUND);
        }
        //验证码校验
        if (!StringUtils.equals(emailUser.getSCode(), this.redisTemplate.opsForValue().get(CHANGE_KEY_PREFIX + emailUser.getSEmail()))) {
            return new CommonResponse(UserCode.UPDATE_FAIL_CODE_WRONG);
        }
        //更新个人信息
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id",id);
        mpParam.put("dUpdateTime",new Date());
        mpParam.put("sEmail",emailUser.getSEmail());
        int iResult = userDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(UserCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    @Override
    public CommonResponse updatePhone(String id, PhoneUser phoneUser) {
        //参数验证
        if ((ObjectUtils.isEmpty(phoneUser)) || (StringUtils.isBlank(id))){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //新手机号校验
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sPhone",phoneUser.getSPhone());
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isNotEmpty(user)){
            return new CommonResponse(UserCode.PHONE_HAS_BEEN_REGISTERED);
        }
        //用户id验证
        mpCheck.clear();
        mpCheck.put("id",id);
        user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(UserCode.USER_NOT_FOUND);
        }
        //验证码校验
        if (!StringUtils.equals(phoneUser.getSCode(), this.redisTemplate.opsForValue().get(CHANGE_KEY_PREFIX + phoneUser.getSPhone()))) {
            return new CommonResponse(UserCode.UPDATE_FAIL_CODE_WRONG);
        }
        //更新用户信息
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id",id);
        mpParam.put("dUpdateTime",new Date());
        mpParam.put("sPhone",phoneUser.getSPhone());
        int iResult = userDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(UserCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    @Override
    public QueryResponse getEmailById(String id) {
        //参数验证
        if (StringUtils.isBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //用户验证
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id",id);
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new QueryResponse(UserCode.USER_NOT_FOUND, null);
        }
        List<String> arrEmail = Collections.singletonList(user.getSEmail());
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrEmail, arrEmail.size()));
    }

    @Override
    public CommonResponse updatePassword(String id, PasswordUser passwordUser) {
        //参数验证
        if ((ObjectUtils.isEmpty(passwordUser) || (StringUtils.isBlank(id)))){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //用户验证
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(UserCode.USER_NOT_FOUND);
        }
        //验证码校验
        if (!StringUtils.equals(passwordUser.getSCode(), this.redisTemplate.opsForValue().get(MODIFY_KEY_PREFIX + passwordUser.getSEmail()))) {
            return new CommonResponse(UserCode.UPDATE_PASSWORD_FAIL_CODE_WRONG);
        }
        //更新人员信息
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("dUpdateTime",new Date());
        mpParam.put("id",id);
        mpParam.put("sPassword", passwordUser.getSPassword());
        int iResult = userDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(UserCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    private void registerCount() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String dateKey = "count:rg:" + sdf.format( new Date() );
        redisTemplate.opsForValue().setIfAbsent( dateKey, "0", 25L, TimeUnit.HOURS );
        redisTemplate.opsForValue().increment( dateKey );
        redisTemplate.opsForValue().increment( "count:rg" );
    }
}
