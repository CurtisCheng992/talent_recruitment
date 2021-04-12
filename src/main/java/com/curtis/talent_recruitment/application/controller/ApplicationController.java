package com.curtis.talent_recruitment.application.controller;

import com.curtis.talent_recruitment.api.application.ApplicationControllerApi;
import com.curtis.talent_recruitment.application.service.IApplicationService;
import com.curtis.talent_recruitment.entity.request.application.AddApplication;
import com.curtis.talent_recruitment.entity.request.application.UpdateApplication;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:08 PM 3/1/2021
 */
@RestController
@RequestMapping("application")
public class ApplicationController implements ApplicationControllerApi {

    @Autowired
    private IApplicationService applicationService;

    @Override
    @GetMapping("getList")
    public QueryResponse getList(@RequestParam(required = false) String sUserID, @RequestParam(required = false) String sHRID) {
        return applicationService.getList(sUserID, sHRID);
    }

    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return applicationService.getDetail(id);
    }

    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddApplication addApplication) {
        return applicationService.add(addApplication);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return applicationService.delete(id);
    }

    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateApplication updateApplication) {
        return applicationService.update(id, updateApplication);
    }

    @Override
    @GetMapping("getList/user/{sUserID}")
    public QueryResponse getListByUserID(@PathVariable String sUserID) {
        return applicationService.getListByUserID(sUserID);
    }

    @Override
    @GetMapping("getCount/user/{sUserID}")
    public QueryResponse getCountByUserID(@PathVariable String sUserID) {
        return applicationService.getCountByUserID(sUserID);
    }

    @Override
    @GetMapping("getCount/position/user/{sPositionID}/{sUserID}")
    public QueryResponse getCount(@PathVariable String sPositionID, @PathVariable String sUserID) {
        return applicationService.getCount(sPositionID, sUserID);
    }

    @Override
    @DeleteMapping("delete/position/user/{sPositionID}/{sUserID}")
    public CommonResponse deleteByCondition(@PathVariable String sPositionID, @PathVariable String sUserID) {
        return applicationService.deleteByCondition(sPositionID, sUserID);
    }

    @Override
    @PutMapping("verify/hr/id/{sHRID}/{id}")
    public CommonResponse VerifyByID(@PathVariable String sHRID, @PathVariable String id) {
        return applicationService.verify(sHRID, id);
    }

    @Override
    @GetMapping("getCount/hr/{sHRID}")
    public QueryResponse getCountByHR(@PathVariable String sHRID) {
        return applicationService.getCountByHR(sHRID);
    }

    @Override
    @PostMapping("getByPage/currentPage/pageSize/{lCurrentPage}/{lPageSize}")
    public QueryResponse getByPage(@PathVariable Long lCurrentPage, @PathVariable Long lPageSize, @RequestBody Map<String, Object> mpParam) {
        return applicationService.getByPage(lCurrentPage, lPageSize, mpParam);
    }
}
