package com.curtis.talent_recruitment.position.controller;

import com.curtis.talent_recruitment.api.position.PositionControllerApi;
import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.position.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 5:20 PM 3/1/2021
 */
@RestController
@RequestMapping("position")
public class PositionController implements PositionControllerApi {

    @Autowired
    private IPositionService positionService;

    @Override
    @GetMapping("getList")
    public QueryResponse getList(@RequestParam(required = false) String sHRID) {
        return positionService.getList(sHRID);
    }

    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return positionService.getDetail(id);
    }

    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddPosition addPosition) {
        return positionService.add(addPosition);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return positionService.delete(id);
    }

    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdatePosition updatePosition) {
        return positionService.update(id, updatePosition);
    }

    @Override
    @GetMapping("getHot")
    public QueryResponse getHot(@RequestParam(required = false) Integer iLimit) {
        return positionService.getHot(iLimit);
    }

    @Override
    @GetMapping("getNew")
    public QueryResponse getNew(Integer iLimit) {
        return positionService.getNew(iLimit);
    }

    @Override
    @GetMapping("getSearch")
    public QueryResponse getSearch(String sPositionName) {
        return positionService.getSearch(sPositionName);
    }

    @Override
    @GetMapping("getCount/hr/{sHRID}")
    public QueryResponse getCount(@PathVariable String sHRID) {
        return positionService.getCount(sHRID);
    }

    @Override
    @PostMapping("getPageByCategory/currentPage/pageSize/{lCurrentpage}/{lPageSize}")
    public QueryResponse getListByCategoryName(@PathVariable Long lCurrentpage, @PathVariable Long lPageSize, @RequestBody Map<String, Object> mpParam) {
        return positionService.getListByCategoryName(lCurrentpage, lPageSize, mpParam);
    }

    @Override
    @PostMapping("getByPage/currentPage/pageSize/{lCurrentpage}/{lPageSize}")
    public QueryResponse getByPage(@PathVariable Long lCurrentpage, @PathVariable Long lPageSize, @RequestBody Map<String, Object> mpParam) {
        return positionService.getByPage(lCurrentpage, lPageSize, mpParam);
    }

    @Override
    @PutMapping("updatePositionHot/{sPositionID}/{iHot}")
    public CommonResponse updatePositionHot(@PathVariable String sPositionID, @PathVariable Integer iHot) {
        return positionService.updatePositionHot(sPositionID, iHot);
    }

}
