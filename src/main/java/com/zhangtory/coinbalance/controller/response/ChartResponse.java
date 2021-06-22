package com.zhangtory.coinbalance.controller.response;

import com.zhangtory.coinbalance.model.vo.DetailsVo;
import com.zhangtory.coinbalance.model.vo.HoldRateVo;
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

    /**
     * detailListChart
     */
    List<DetailsVo> detailList;

    /**
     * detailLegend
     */
    List<String> detailLegendList;

    /**
     * holdRateData
     */
    List<HoldRateVo> holdRateList;

}
