package com.curtis.talent_recruitment.comment.controller;

import com.curtis.talent_recruitment.api.comment.CommentControllerApi;
import com.curtis.talent_recruitment.comment.service.ICommentService;
import com.curtis.talent_recruitment.entity.request.comment.AddComment;
import com.curtis.talent_recruitment.entity.request.comment.UpdateComment;
import com.curtis.talent_recruitment.entity.response.CommonResponse;
import com.curtis.talent_recruitment.entity.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 8:19 PM 3/1/2021
 */
@RestController
@RequestMapping("comment")
public class CommentController implements CommentControllerApi {

    @Autowired
    private ICommentService commentService;

    @Override
    @GetMapping("getList")
    public QueryResponse getList() {
        return commentService.getList();
    }

    @Override
    @GetMapping("getDetail/{id}")
    public QueryResponse getDetail(@PathVariable String id) {
        return commentService.getDetail(id);
    }

    @Override
    @PostMapping("add")
    public CommonResponse add(@RequestBody AddComment addComment) {
        return commentService.add(addComment);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return commentService.delete(id);
    }

    @Override
    @PutMapping("update/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody UpdateComment updateComment) {
        return commentService.update(id, updateComment);
    }
}
