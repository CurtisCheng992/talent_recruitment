package com.curtis.talent_recruitment.entity.response.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 通用的查询结果集
 * @date 2021/2/23 11:02
 */
@Data
@AllArgsConstructor
public class QueryResult<T> {

    // 数据列表
    private List<T> data;

    // 数据总数
    private int total;

}
