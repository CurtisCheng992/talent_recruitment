package com.curtis.talent_recruitment.entity.response;

import com.curtis.talent_recruitment.entity.response.code.ResponseCode;
import com.curtis.talent_recruitment.entity.response.result.QueryResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Colin
 * @version 1.0.0
 * @description 查询操作的响应结果类
 * @date 2020/10/23 15:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryResponse extends CommonResponse {

    private QueryResult queryResult;

    public QueryResponse(ResponseCode responseCode, QueryResult queryResult) {
        super( responseCode );
        this.queryResult = queryResult;
    }

}
