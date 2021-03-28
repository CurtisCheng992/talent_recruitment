package com.curtis.talent_recruitment.company.controller;

import com.curtis.talent_recruitment.api.company.CompanyControllerApi;
import com.curtis.talent_recruitment.company.service.ICompanyService;
import com.curtis.talent_recruitment.entity.request.company.AddCompany;
import com.curtis.talent_recruitment.entity.request.company.UpdateCompany;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 3:36 PM 3/1/2021
 */
@RestController
@RequestMapping("company")
public class CompanyController implements CompanyControllerApi {

    @Autowired
    private ICompanyService companyService;

    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return companyService.getList();
    }

    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return companyService.getDetail(id);
    }

    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddCompany addCompany) {
        return companyService.add(addCompany);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return companyService.delete(id);
    }

    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateCompany updateCompany) {
        return companyService.update(id,updateCompany);
    }

    @Override
    @GetMapping("getHot")
    public QueryResponse getHot(@RequestParam(required = false) Integer iLimit) {
        return companyService.getHot(iLimit);
    }

    @Override
    @GetMapping("getDetail/hr/{sHRID}")
    public QueryResponse getDetailByHRID(@PathVariable String sHRID) {
        return companyService.getDetailByHRID(sHRID);
    }
}
