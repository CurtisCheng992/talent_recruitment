package com.curtis.talent_recruitment.resume.controller;

import com.curtis.talent_recruitment.api.resume.ResumeControllerApi;
import com.curtis.talent_recruitment.entity.request.resume.AddResume;
import com.curtis.talent_recruitment.entity.request.resume.UpdateResume;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.resume.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 6:01 PM 3/1/2021
 */
@RestController
@RequestMapping("resume")
public class ResumeController implements ResumeControllerApi {

    @Autowired
    private IResumeService resumeService;

    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return resumeService.getList();
    }

    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return resumeService.getDetail(id);
    }

    @Override
    @GetMapping("getCount/user/{sUserID}")
    public QueryResponse getCount(@PathVariable String sUserID) {
        return resumeService.getCount(sUserID);
    }

    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddResume addResume) {
        return resumeService.add(addResume);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return resumeService.delete(id);
    }

    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateResume updateResume) {
        return resumeService.update(id, updateResume);
    }

    @Override
    @GetMapping("get/user/{sUserID}")
    public QueryResponse getByUserID(@PathVariable String sUserID) {
        return resumeService.getByUserID(sUserID);
    }

    @Override
    @PutMapping("update/picture/id/{id}")
    public CommonResponse updatePicture(@PathVariable String id, @RequestBody String sPicture) {
        return resumeService.updatePicture(id, sPicture);
    }

    @Override
    @PostMapping("getByPage/currentPage/pageSize/{lCurrentPage}/{lPageSize}")
    public QueryResponse getByPage(@PathVariable Long lCurrentPage, @PathVariable Long lPageSize, @RequestBody Map<String, Object> mpParam) {
        return resumeService.getByPage(lCurrentPage, lPageSize, mpParam);
    }
}
