package com.curtis.talent_recruitment.collection.controller;

import com.curtis.talent_recruitment.api.collection.CollectionControllerApi;
import com.curtis.talent_recruitment.collection.service.ICollectionService;
import com.curtis.talent_recruitment.entity.request.collection.AddCollection;
import com.curtis.talent_recruitment.entity.request.collection.UpdateCollection;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 7:46 PM 3/1/2021
 */
@RestController
@RequestMapping("collection")
public class CollectionController implements CollectionControllerApi {

    @Autowired
    private ICollectionService collectionService;

    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return collectionService.getList();
    }

    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return collectionService.getDetail(id);
    }

    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddCollection addCollection) {
        return collectionService.add(addCollection);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return collectionService.delete(id);
    }

    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateCollection updateCollection) {
        return collectionService.update(id, updateCollection);
    }
}
