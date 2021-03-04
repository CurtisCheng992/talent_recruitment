package com.curtis.talent_recruitment.category.service.impl;

import com.curtis.talent_recruitment.category.dao.CategoryDao;
import com.curtis.talent_recruitment.category.entity.Category;
import com.curtis.talent_recruitment.category.service.ICategoryService;
import com.curtis.talent_recruitment.entity.request.school.AddCategory;
import com.curtis.talent_recruitment.entity.request.school.UpdateCategory;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.category.CategoryCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.position.dao.PositionDao;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.hs.commons.utils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 4:18 PM 2/25/2021
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PositionDao positionDao;

    /**
     * 查询所有分类信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<Category> arrCategory = categoryDao.getList();
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrCategory,arrCategory.size()));
    }

    /**
     * 根据id查询一个分类信息
     * @param id
     * @return
     */
    @Override
    public QueryResponse getDetail(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        mpParam.put("id",id);
        //根据id查询用户
        Category category = categoryDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(category)){
            return new QueryResponse(CategoryCode.CATEGORY_NOT_FOUND,null);
        }
        List<Category> arrCategory = new ArrayList<>();
        arrCategory.add(category);
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrCategory,arrCategory.size()));
    }

    /**
     * 添加一个分类
     * @param addCategory
     * @return
     */
    @Override
    public CommonResponse add(AddCategory addCategory) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addCategory)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCategoryName非空判断
        if (!StringUtils.isNoneBlank(addCategory.getSCategoryName())){
            ExceptionThrowUtils.cast(CategoryCode.INVALID_PARAM);
        }
        //sParentID转换
        if ("".equals(addCategory.getSParentID())){
            addCategory.setSParentID(null);
        }
        //根据sParentID判断父级分类是否存在
        if (StringUtils.isNoneBlank(addCategory.getSParentID())){
            mpParam.put("id",addCategory.getSParentID());
            Category parentCategory = categoryDao.getDetail(mpParam);
            if (ObjectUtils.isEmpty(parentCategory)){
                return new CommonResponse(CategoryCode.PARENT_CATEGORY_NOT_FOUND);
            }
            mpParam.clear();
        }
        //添加一个分类
        mpParam = ConvertUtils.objectToMap(addCategory);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        int iResult = categoryDao.add(mpParam);
        if ( iResult <=0 ){
            return new CommonResponse(CategoryCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个分类
     * @param id
     * @return
     */
    @Override
    public CommonResponse delete(String id) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (!StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //删除之前要判断用户，职位以及自身父类是否存在，若存在，则不允许删除
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sDirection",id);
        int iCount = userDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(CategoryCode.DELETE_FAIL_DIRECTION_CATEGORY_EXIST);
        }
        mpCheck.clear();
        mpCheck.put("sCategoryID",id);
        iCount = positionDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(CategoryCode.DELETE_FAIL_POSITION_EXIST);
        }
        mpCheck.clear();
        mpCheck.put("sParentID",id);
        iCount = categoryDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(CategoryCode.DELETE_FAIL_CHILD_CATEGORY_EXIST);
        }
        //根据id查询分类是否存在
        mpParam.put("id",id);
        Category category = categoryDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(category)){
            return new CommonResponse(CategoryCode.CATEGORY_NOT_FOUND);
        }
        int iResult = categoryDao.delete(mpParam);
        if (iResult <=0 ){
            return new CommonResponse(CategoryCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个分类
     * @param id
     * @param updateCategory
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateCategory updateCategory) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updateCategory) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCategoryName非空判断
        if (!StringUtils.isNoneBlank(updateCategory.getSCategoryName())){
            ExceptionThrowUtils.cast(CategoryCode.INVALID_PARAM);
        }
        //sParentID转换
        if ("".equals(updateCategory.getSParentID())){
            updateCategory.setSParentID(null);
        }
        //根据sParentID判断父级分类是否存在
        if (StringUtils.isNoneBlank(updateCategory.getSParentID())){
            mpParam.put("id",updateCategory.getSParentID());
            Category parentCategory = categoryDao.getDetail(mpParam);
            if (ObjectUtils.isEmpty(parentCategory)){
                return new CommonResponse(CategoryCode.PARENT_CATEGORY_NOT_FOUND);
            }
            mpParam.clear();
        }
        mpParam = ConvertUtils.objectToMap(updateCategory);
        mpParam.put("id",id);
        //根据id判断分类是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Category category = categoryDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(category)){
            return new CommonResponse(CategoryCode.CATEGORY_NOT_FOUND);
        }
        int iResult = categoryDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CategoryCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}
