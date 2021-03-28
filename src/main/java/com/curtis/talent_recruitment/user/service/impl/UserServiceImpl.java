package com.curtis.talent_recruitment.user.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.category.dao.CategoryDao;
import com.curtis.talent_recruitment.category.entity.Category;
import com.curtis.talent_recruitment.collection.dao.CollectionDao;
import com.curtis.talent_recruitment.comment.dao.CommentDao;
import com.curtis.talent_recruitment.department.dao.UserDepartmentDao;
import com.curtis.talent_recruitment.entity.request.auth.LoginUser;
import com.curtis.talent_recruitment.entity.request.user.AddHR;
import com.curtis.talent_recruitment.entity.request.user.AddUser;
import com.curtis.talent_recruitment.entity.request.user.RegisterUser;
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
import com.curtis.talent_recruitment.utils.user.*;
import com.hs.commons.utils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CommentDao commentDao;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsConfig smsConfig;

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
    public QueryResponse getList() {
        List<User> arrUser = userDao.getList();
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
        //判断sUsername,sPassword,sRealName,sPhone,sEmail,iGender是否为空
        if (!StringUtils.isNoneBlank(addUser.getSUsername()) ||
            !StringUtils.isNoneBlank(addUser.getSPassword()) ||
            !StringUtils.isNoneBlank(addUser.getSRealName()) ||
            !StringUtils.isNoneBlank(addUser.getSPhone()) ||
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
        //TODO 邮箱与电话号码判断
        //新增求职者用户
        User user = new User();
        BeanUtils.copyProperties(addUser, user);
        user.setId(com.hs.commons.utils.StringUtils.getUUIDString());
        user.setIStatus(1);
        user.setIRoleType(3);
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
        //TODO 邮箱与电话号码判断
        //新增HR用户
        User user = new User();
        BeanUtils.copyProperties(addHR, user);
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

    private void registerCount() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String dateKey = "count:rg:" + sdf.format( new Date() );
        redisTemplate.opsForValue().setIfAbsent( dateKey, "0", 25L, TimeUnit.HOURS );
        redisTemplate.opsForValue().increment( dateKey );
        redisTemplate.opsForValue().increment( "count:rg" );
    }
}
