package com.curtis.talent_recruitment.position.controller;

import com.curtis.talent_recruitment.api.position.PositionControllerApi;
import com.curtis.talent_recruitment.entity.request.position.AddPosition;
import com.curtis.talent_recruitment.entity.request.position.UpdatePosition;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import com.curtis.talent_recruitment.position.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public QueryResponse getList() {
        return positionService.getList();
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
}
