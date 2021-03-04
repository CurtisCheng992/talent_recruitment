package com.curtis.talent_recruitment.collection.service.impl;

import com.curtis.talent_recruitment.collection.dao.CollectionDao;
import com.curtis.talent_recruitment.collection.entity.Collection;
import com.curtis.talent_recruitment.collection.service.ICollectionService;
import com.curtis.talent_recruitment.entity.request.collection.AddCollection;
import com.curtis.talent_recruitment.entity.request.collection.UpdateCollection;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.entity.response.code.CommonCode;
import com.curtis.talent_recruitment.entity.response.code.collection.CollectionCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import com.curtis.talent_recruitment.position.dao.PositionDao;
import com.curtis.talent_recruitment.position.entity.Position;
import com.curtis.talent_recruitment.user.dao.UserDao;
import com.curtis.talent_recruitment.user.entity.User;
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
 * @Date: Created in 7:48 PM 3/1/2021
 */
@Service
public class CollectionServiceImpl implements ICollectionService {

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PositionDao positionDao;

    /**
     * 查询所有收藏信息
     * @return
     */
    @Override
    public QueryResponse getList() {
        List<Collection> arrCollection = collectionDao.getList();
        return new QueryResponse(CommonCode.SUCCESS,new QueryResult(arrCollection, arrCollection.size()));
    }

    /**
     * 根据id查询一个收藏信息
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
        //根据id查询一个收藏信息
        mpParam.put("id",id);
        Collection collection = collectionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(collection)){
            return new QueryResponse(CollectionCode.COLLECTION_NOT_FOUND,null);
        }
        List<Collection> arrCollection = new ArrayList<>();
        arrCollection.add(collection);
        return new QueryResponse(CommonCode.SUCCESS, new QueryResult(arrCollection, arrCollection.size()));
    }

    /**
     * 新增一个收藏信息
     * @param addCollection
     * @return
     */
    @Override
    public CommonResponse add(AddCollection addCollection) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(addCollection)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sUserID,sPositionID非空判断
        if (!StringUtils.isNoneBlank(addCollection.getSUserID()) ||
        !StringUtils.isNoneBlank(addCollection.getSPositionID())){
            ExceptionThrowUtils.cast(CollectionCode.INVALID_PARAM);
        }
        //根据sUserID判断该用户是否存在
        mpParam.put("id",addCollection.getSUserID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(CollectionCode.USER_NOT_FOUND);
        }
        mpParam.clear();
        //根据sPositionID判断干职位是否存在
        mpParam.put("id",addCollection.getSPositionID());
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(CollectionCode.POSITION_NOT_FOUND);
        }
        mpParam.clear();
        //添加一个收藏信息
        mpParam = ConvertUtils.objectToMap(addCollection);
        mpParam.put("id", com.hs.commons.utils.StringUtils.getUUIDString());
        mpParam.put("dCreateTime", new Date());
        mpParam.put("dUpdateTime", new Date());
        int iResult = collectionDao.add(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CollectionCode.INSERT_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除一个收藏信息
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
        //根据id查询判断该收藏是否存在
        mpParam.put("id",id);
        Collection collection = collectionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(collection)){
            return new CommonResponse(CollectionCode.COLLECTION_NOT_FOUND);
        }
        //根据id删除一个收藏
        int iResult = collectionDao.delete(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CollectionCode.DELETE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新一个收藏信息
     * @param id
     * @param updateCollection
     * @return
     */
    @Override
    public CommonResponse update(String id, UpdateCollection updateCollection) {
        Map<String, Object> mpParam = new HashMap<>();
        //参数非空判断
        if (ObjectUtils.isEmpty(updateCollection) && !StringUtils.isNoneBlank(id)){
            ExceptionThrowUtils.cast(CommonCode.INVALID_PARAM);
        }
        //参数sUserID,sPositionID非空判断
        if (!StringUtils.isNoneBlank(updateCollection.getSUserID()) ||
                !StringUtils.isNoneBlank(updateCollection.getSPositionID())){
            ExceptionThrowUtils.cast(CollectionCode.INVALID_PARAM);
        }
        //根据sUserID判断该用户是否存在
        mpParam.put("id",updateCollection.getSUserID());
        User user = userDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(user)){
            return new CommonResponse(CollectionCode.USER_NOT_FOUND);
        }
        mpParam.clear();
        //根据sPositionID判断干职位是否存在
        mpParam.put("id",updateCollection.getSPositionID());
        Position position = positionDao.getDetail(mpParam);
        if (ObjectUtils.isEmpty(position)){
            return new CommonResponse(CollectionCode.POSITION_NOT_FOUND);
        }
        //根据id查询判断该收藏是否存在
        Map<String, Object> mpCheck = new HashMap<>();
        mpCheck.put("id",id);
        Collection collection = collectionDao.getDetail(mpCheck);
        if (ObjectUtils.isEmpty(collection)){
            return new CommonResponse(CollectionCode.COLLECTION_NOT_FOUND);
        }
        //根据id更新一个收藏信息
        mpParam = ConvertUtils.objectToMap(updateCollection);
        mpParam.put("id",id);
        mpCheck.put("dUpdateTime", new Date());
        int iResult = collectionDao.update(mpParam);
        if (iResult <= 0){
            return new CommonResponse(CollectionCode.UPDATE_FAIL);
        }
        return new CommonResponse(CommonCode.SUCCESS);
    }
}