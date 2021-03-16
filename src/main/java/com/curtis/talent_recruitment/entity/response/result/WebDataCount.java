package com.curtis.talent_recruitment.entity.response.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Curtis
 * @Description:
 * @Date: Created in 6:10 PM 3/9/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebDataCount {

    private Integer totalVisitCount;

    private Integer dateVisitCount;

    private Integer totalDownloadCount;

    private Integer dateDownloadCount;

    private Integer totalRegisterCount;

    private Integer dateRegisterCount;

}
