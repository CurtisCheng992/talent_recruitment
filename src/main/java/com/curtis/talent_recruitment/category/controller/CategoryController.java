package com.curtis.talent_recruitment.category.controller;

import com.curtis.talent_recruitment.api.category.CategoryControllerApi;
import com.curtis.talent_recruitment.category.service.ICategoryService;
import com.curtis.talent_recruitment.entity.request.school.AddCategory;
import com.curtis.talent_recruitment.entity.request.school.UpdateCategory;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:17 PM 2/25/2021
 */
@RestController
@RequestMapping("category")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询所有分类信息
     * @return
     */
    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return categoryService.getList();
    }

    /**
     * 根据id查询单个分类信息
     * @param id
     * @return
     */
    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return categoryService.getDetail(id);
    }

    /**
     * 添加一个分类
     * @param addCategory
     * @return
     */
    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddCategory addCategory) {
        return categoryService.add(addCategory);
    }

    /**
     * 删除一个分类
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return categoryService.delete(id);
    }

    /**
     * 更新一个分类
     * @param id
     * @param updateCategory
     * @return
     */
    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateCategory updateCategory) {
        return categoryService.update(id,updateCategory);
    }
}
