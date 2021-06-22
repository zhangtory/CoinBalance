package com.zhangtory.coinbalance.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhangtory.coinbalance.controller.response.ChartResponse;
import com.zhangtory.coinbalance.service.ChartService;
import com.zhangtory.coinbalance.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangtory
 * @date 2021/6/20 21:48
 * @description:
 */
@Controller
public class ChartController {

    @Autowired
    private ChartService chartService;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name","hello pillar");
        return "index";
    }

    @RequestMapping("/chart")
    public String chart(Model model) {
        ChartResponse response = chartService.sumChart();
        model.addAttribute("timeList", JSONObject.toJSONString(response.getTimeList()));
        model.addAttribute("sumChartData", JSONObject.toJSONString(response.getAmountList()));
        model.addAttribute("detailListData", JSONObject.toJSONString(response.getDetailList()));
        model.addAttribute("detailLegend", JSONObject.toJSONString(response.getDetailLegendList()));
        model.addAttribute("holdRateData", JSONObject.toJSONString(response.getHoldRateList()));
        return "chart";
    }

}
