package com.curtis.talent_recruitment.school.controller;

import com.curtis.talent_recruitment.api.school.SchoolControllerApi;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.school.service.ISchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Curtis
 * @Description: 学校模块控制层
 * @Date: Created in 3:48 PM 2/25/2021
 */
@RestController
@RequestMapping("school")
public class SchoolController implements SchoolControllerApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    @Autowired
    private ISchoolService schoolService;

    /**
     * 查询所有学校信息
     * @return
     */
    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return schoolService.getList();
    }

    /**
     * 根据id查询一所学校信息
     * @param id
     * @return
     */
    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return schoolService.getDetail(id);
    }
}
