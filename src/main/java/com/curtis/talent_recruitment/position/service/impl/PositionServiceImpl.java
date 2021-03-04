package com.curtis.talent_recruitment.position.service.impl;

import com.curtis.talent_recruitment.application.dao.ApplicationDao;
import com.curtis.talent_recruitment.category.dao.CategoryDao;
import com.curtis.talent_recruitment.category.entity.Category;
import com.curtis.talent_recruitment.collection.dao.CollectionDao;
import com.curtis.talent_recruitment.comment.dao.CommentDao;
import com.curtis.talent_recruitment.department.dao.DepartmentDao;
import com.curtis.talent_recruitment.department.entity.Department;
import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.position.PositionCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.position.dao.PositionDao;
import com.curtis.talent_recruitment.position.entity.Position;
import com.curtis.talent_recruitment.position.service.IPositionService;
import com.curtis.talent_recruitment.utils.exception.ExceptionThrowUtils;
import com.hs.commons.utils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:22 PM 3/1/2021
 */
@Service
public class PositionServiceImpl implements IPositionService {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CollectionDao collectionDao;

    /**
     * 查询所有职位信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<Position> arrPosition = positionDao.getList();
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrPosition,arrPosition.size()));
    }

    /**
     * 根据id查询一个职位信息
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
        //根据id查询职位信息
        mpParam.put("id",id);
        positionDao.updateViews(mpParam);
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new QueryResponse(PositionCode.POSITION_NOT_FOUND,null);
        }
        List<Position> arrPosition = new ArrayList<>();
        arrPosition.add(position);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrPosition,arrPosition.size()));
    }

    /**
     * 新增一个职位信息
     * @param addPosition
     * @return
     */
    @Override
    public CommonResponse add(AddPosition addPosition) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addPosition)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCategoryID,sDepartmentID,sPositionName非空判断
        if (!StringUtils.isNoneBlank(addPosition.getSCategoryID()) ||
        !StringUtils.isNoneBlank(addPosition.getSDepartmentID()) ||
        !StringUtils.isNoneBlank(addPosition.getSPositionName())){
            ExceptionThrowUtils.cast(PositionCode.INVALID_PARAM);
        }
        //根据sCategoryID判断该分类是否存在
        mpParam.put("id",addPosition.getSCategoryID());
        Category category = categoryDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(category)){
            return new CommonResponse(PositionCode.CATEGORY_NOT_FOUND);
        }
        mpParam.clear();
        //根据sDepartmentID判断该部门是否存
        mpParam.put("id",addPosition.getSDepartmentID());
        Department department = departmentDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(department)){
            return new CommonResponse(PositionCode.DEPARTMENT_NOT_FOUND);
        }
        mpParam.clear();
        //判断职位的招聘人数是否小于1
        if (addPosition.getIQuantity() < 1){
            return new CommonResponse(PositionCode.QUANTITY_CANT_LESS_THAN_1);
        }
        //添加一个职位信息
        mpParam = ConvertUtils.objectToMap(addPosition);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        int iResult = positionDao.add(mpParam);
        if (iResult <= 0){
            return new CommonResponse(PositionCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个职位信息
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
        //判断该职位下是否存在评论，申请，收藏
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("sPositionID",id);
        int iCount = commentDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(PositionCode.DELETE_FAIL_COMMENT_EXIST);
        }
        iCount = applicationDao.getCount(mpCheck);
        if (iCount >= 1){
            return new CommonResponse(PositionCode.DELETE_FAIL_APPLICATION_EXIST);
        }
        iCount = collectionDao.getCount(mpCheck);
        if (iCount >=1 ){
            return new CommonResponse(PositionCode.DELETE_FAIL_COLLECTION_EXIST);
        }
        //根据id查询该职位是否存在
        mpParam.put("id",id);
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(PositionCode.POSITION_NOT_FOUND);
        }
        //根据id删除一个职位信息
        int iResult = positionDao.delete(mpParam);
        if (iResult <= 0){
            return new CommonResponse(PositionCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个职位信息
     * @param id
     * @param updatePosition
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdatePosition updatePosition) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updatePosition) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sCategoryID,sDepartmentID,sPositionName非空判断
        if (!StringUtils.isNoneBlank(updatePosition.getSCategoryID()) ||
                !StringUtils.isNoneBlank(updatePosition.getSDepartmentID()) ||
                !StringUtils.isNoneBlank(updatePosition.getSPositionName())){
            ExceptionThrowUtils.cast(PositionCode.INVALID_PARAM);
        }
        //根据sCategoryID判断该分类是否存在
        mpParam.put("id",updatePosition.getSCategoryID());
        Category category = categoryDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(category)){
            return new CommonResponse(PositionCode.CATEGORY_NOT_FOUND);
        }
        mpParam.clear();
        //根据sDepartmentID判断该部门是否存
        mpParam.put("id",updatePosition.getSDepartmentID());
        Department department = departmentDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(department)){
            return new CommonResponse(PositionCode.DEPARTMENT_NOT_FOUND);
        }
        mpParam.clear();
        //根据id查询该职位是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Position position = positionDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(PositionCode.POSITION_NOT_FOUND);
        }
        //判断职位的招聘人数是否小于1
        if (updatePosition.getIQuantity() < 1){
            return new CommonResponse(PositionCode.QUANTITY_CANT_LESS_THAN_1);
        }
        //根据id更新职位信息
        mpParam = ConvertUtils.objectToMap(updatePosition);
        mpParam.put("id",id);
        mpParam.put("dUpdateTime", new Date());
        int iResult = positionDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(PositionCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}
