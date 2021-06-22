package com.zhangtory.coinbalance.service;

import com.zhangtory.coinbalance.controller.response.ChartResponse;

/**
 * Created by Scott on 2021/6/21
 **/
public interface ChartService {

    /**
     * 图表数据查询
     * @return
     */
    ChartResponse sumChart();

}
