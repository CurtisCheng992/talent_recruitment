package com.curtis.talent_recruitment.resource.controller;

import com.curtis.talent_recruitment.api.resource.ResourceControllerApi;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.UploadResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.resource.entity.Resource;
import com.curtis.talent_recruitment.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 12:44 PM 3/27/2021
 */
@RestController
@RequestMapping("resource")
public class ResourceController implements ResourceControllerApi {

    @Autowired
    private ResourceService resourceService;

    @Override
    @PostMapping("delete/sLocation")
    public CommonResponse deleteByLocation(@RequestBody String sLocation) throws UnsupportedEncodingException {
        resourceService.deleteByLocation(sLocation);
        return new CommonResponse(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping("delete/resource/user/{sResourceID}/{sUserID}")
    public CommonResponse delete(@PathVariable String sResourceID, @PathVariable String sUserID) {
        return resourceService.delete(sResourceID, sUserID);
    }

    @Override
    @PostMapping("upload/file/user/{sUserID}")
    public UploadResponse upload(MultipartFile file, @PathVariable String sUserID) {
        QueryResponse queryResponse = (QueryResponse) resourceService.upload( file, sUserID );

        @SuppressWarnings("unchecked")
        List<Resource> data = queryResponse.getQueryResult().getData();

        return UploadResponse.builder()
                .errno( queryResponse.isSuccess() ? 0 : 1 )
                .data( Collections.singletonList(
                        CollectionUtils.isEmpty( data )
                                ? null
                                : data.get( 0 ).getSLocation()
                ) )
                .build();
    }

    @Override
    @GetMapping("download/resource/{sResourceID}")
    public void download(@PathVariable String sResourceID, HttpServletResponse response) {
        resourceService.download(sResourceID, response);
    }
}
