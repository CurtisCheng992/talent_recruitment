package com.curtis.talent_recruitment.utils.resource;

import com.curtis.talent_recruitment.resource.entity.Resource;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Colin
 * @version 1.0.0
 * @description 文件工具类
 * @date 2020/11/17 16:16
 */
@Component
public class ResourceUtils {

    @Autowired
    private FastFileStorageClient storageClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtils.class);

    public String upload(MultipartFile file) {
        StorePath storePath;
        try {
            storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(),
                    StringUtils.substringAfterLast(file.getOriginalFilename(), "."), null);

            // 生成url地址
            return "http://182.254.148.75/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.error("服务器内部错误：{}，所上传文件名为：{}", e.getMessage(), file.getOriginalFilename());
            return null;
        }
    }

    public void download(HttpServletResponse response, Resource resource) {
        String filePath = resource.getSLocation();
        StorePath storePath = StorePath.praseFromUrl(filePath);

        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] data = this.storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);

        try {

            response.reset();

            // 跨域设置
            response.addHeader("Access-Control-Allow-Origin", "http://www.yixuetang.com");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Credentials", "true");

            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + resource.getSName());

            response.addHeader("Content-Length", "" + data.length);
            response.setContentType(resource.getSContentType() + ";charset=UTF-8");

            IOUtils.write(data, response.getOutputStream());

        } catch (Exception ex) {
            LOGGER.error("下载文件响应客户端发生异常！异常原因：{}", ex);
        }
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return boolean
     */
    public boolean delete(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return true;
        } catch (FdfsUnsupportStorePathException e) {
            LOGGER.error("删除文件响应客户端发生异常！异常原因：{}", e);
            return false;
        }
    }
}
