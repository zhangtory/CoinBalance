package com.zhangtory.coinbalance.controller.Request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Scott on 2021/6/4
 **/
@Data
public class BalanceHistoryRequest {

    @ApiModelProperty("币种，空为查询全部")
    private List<String> currency;

    @ApiModelProperty("请求条数，默认最近24条")
    private Integer num = 48;

}
