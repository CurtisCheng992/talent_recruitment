package com.curtis.talent_recruitment.resource.service.impl;

import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.resource.ResourceCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.resource.dao.ResourceDao;
import com.curtis.talent_recruitment.resource.entity.Resource;
import com.curtis.talent_recruitment.resource.service.ResourceService;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.user.entity.User;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.curtis.talent_recruitment.utils.resource.ResourceUtils;
import com.hs.commons.utils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 12:46 PM 3/27/2021
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceUtils resourceUtils;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @javax.annotation.Resource(name = "template")
    private RedisTemplate<String, Object> template;

    // 支持的文件类型：.jpg .jpeg .png .mp4 .avi .doc .xls .pdf .ppt .pptx .xlsx .txt
    private static final List<String> CONTENT_TYPES = Arrays.asList( "image/jpeg", "image/png", "video/mpeg4", "video/mp4",
            "video/avi", "application/msword", "application/x-xls", "application/pdf", "application/x-ppt", "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", "text/plain",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/x-zip-compressed" );

    private static final Logger LOGGER = LoggerFactory.getLogger( ResourceServiceImpl.class );

    @Override
    public CommonResponse delete(String sResourceID, String sUserID) {
        //判断资源是否存在
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id", sResourceID);
        Resource resource = resourceDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(resource)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //判断用户
        mpParam.put("id", sUserID);
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(ResourceCode.USER_NOT_FOUND);
        }
        //删除文件
        boolean bDelete = resourceUtils.delete(resource.getSLocation());
        if (!bDelete){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        mpParam.put("id", sResourceID);
        int iResult = resourceDao.delete(mpParam);
        if (iResult <= 0){
            return new CommonResponse(ResourceCode.DELETE_FAIL);
        }

        return CommonResponse.SUCCESS();
    }

    @Override
    public CommonResponse upload(MultipartFile file, String sUserID) {
        //参数非空判断
        if (!StringUtils.isNoneBlank(sUserID) ||
                ObjectUtils.isEmpty(file)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //用户判断
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id", sUserID);
        User user = userDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(ResourceCode.USER_NOT_FOUND);
        }

        String sOriginalFileName = file.getOriginalFilename();
        //检验文件的类型
        String sContentType = file.getContentType();
        if (!CONTENT_TYPES.contains(sContentType)){
            //文件类型不合法
            LOGGER.info("文件类型不合法：{}", sOriginalFileName);
            return new CommonResponse(ResourceCode.CONTENT_TYPE_INVALID);
        }

        //保存到服务器
        String sFilePath = resourceUtils.upload(file);

        if (StringUtils.isBlank(sFilePath)){
            return new CommonResponse(CommonCode.FAIL);
        }

        String sExt = StringUtils.substringAfterLast( sOriginalFileName, "." );
        Resource resource = Resource.builder()
                .id(com.hs.commons.utils.StringUtils.getUUIDString())
                .sUserID(sUserID)
                .iType(1)
                .sExt(sExt)
                .sContentType(sContentType)
                .sName(sOriginalFileName)
                .sLocation(sFilePath)
                .dCreateTime(new Date())
                .dUpdateTime(new Date())
                .build();

        //保存到资源表中
        Map<String, Object> mpParam = ConvertUtils.objectToMap(resource);
        int iResult = resourceDao.add(mpParam);
        if (iResult <=0 ){
            return new CommonResponse(ResourceCode.INSERT_FAIL);
        }

        List<Resource> arrResource = Collections.singletonList(resource);

        return new QueryResponse( CommonCode.SUCCESS,
                new QueryResult(arrResource, arrResource.size()));
    }

    @Override
    public void download(String sResourceID, HttpServletResponse response) {
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("id", sResourceID);
        Resource resource = resourceDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(resource)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        resourceUtils.download( response, resource );
        downloadCount();
    }

    @Override
    public void deleteByLocation(String sLocation) throws UnsupportedEncodingException {
        sLocation = URLDecoder.decode(sLocation,"utf-8");
        sLocation = sLocation.split("=")[0];
        resourceUtils.delete(sLocation);
        Map<String, Object> mpParam = new HashMap<>();
        mpParam.put("sLocation",sLocation);
        resourceDao.deleteByLocation(mpParam);
    }

    private void downloadCount() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String dateKey = "count:dl:" + sdf.format( new Date() );
        redisTemplate.opsForValue().setIfAbsent( dateKey, "0", 25L, TimeUnit.HOURS );
        redisTemplate.opsForValue().increment( dateKey );
        redisTemplate.opsForValue().increment( "count:dl" );
    }

}
