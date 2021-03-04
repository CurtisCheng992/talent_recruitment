package com.curtis.talent_recruitment.application.controller;

import com.curtis.talent_recruitment.api.application.ApplicationControllerApi;
import com.curtis.talent_recruitment.application.service.IApplicationService;
import com.curtis.talent_recruitment.entity.request.application.AddApplication;
import com.curtis.talent_recruitment.entity.request.application.UpdateApplication;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public QueryResponse getList() {
        return applicationService.getList();
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
}