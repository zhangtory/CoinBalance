package com.zhangtory.coinbalance.controller.response;

import lombok.Data;

import java.util.List;

/**
 * Created by Scott on 2021/6/21
 **/
@Data
public class ChartResponse {

    /**
     * 时间列表
     */
    List<String> timeList;

    /**
     * sumChart数据
     */
    List<String> amountList;

}
