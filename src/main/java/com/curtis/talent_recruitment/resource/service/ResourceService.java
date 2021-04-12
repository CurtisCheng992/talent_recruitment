package com.curtis.talent_recruitment.resource.service;

import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 12:45 PM 3/27/2021
 */
public interface ResourceService {

    /**
     * 删除文件
     *
     * @param sResourceID
     * @param sUserID
     * @return
     */
    CommonResponse delete(String sResourceID, String sUserID);

    /**
     * 文件上传
     *
     * @param file
     * @param sUserID
     * @return
     */
    CommonResponse upload(MultipartFile file, String sUserID);

    /**
     * 下载文件
     *
     * @param sResourceID
     * @param response
     */
    void download(String sResourceID, HttpServletResponse response);

    /**
     * 根据文件路径删除文件资源
     *
     * @param sLocation
     */
    void deleteByLocation(String sLocation) throws UnsupportedEncodingException;

    /**
     * 根据条件分页查询
     *
     * @param lCurrentPage
     * @param lPageSize
     * @param mpParam
     * @return
     */
    QueryResponse getByPage(Long lCurrentPage, Long lPageSize, Map<String, Object> mpParam);
}
