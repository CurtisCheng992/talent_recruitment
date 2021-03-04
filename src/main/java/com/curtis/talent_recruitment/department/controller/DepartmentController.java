package com.curtis.talent_recruitment.department.controller;

import com.curtis.talent_recruitment.api.department.DepartmentControllerApi;
import com.curtis.talent_recruitment.department.service.IDepartmentService;
import com.curtis.talent_recruitment.entity.request.department.AddDepartment;
import com.curtis.talent_recruitment.entity.request.department.UpdateDepartment;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:33 PM 3/1/2021
 */
@RestController
@RequestMapping("department")
public class DepartmentController implements DepartmentControllerApi {

    @Autowired
    private IDepartmentService departmentService;

    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return departmentService.getList();
    }

    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return departmentService.getDetail(id);
    }

    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddDepartment addDepartment) {
        return departmentService.add(addDepartment);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return departmentService.delete(id);
    }

    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateDepartment updateDepartment) {
        return departmentService.update(id,updateDepartment);
    }
}
