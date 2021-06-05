package com.zhangtory.coinbalance.controller;

import com.zhangtory.base.core.response.BaseResponse;
import com.zhangtory.base.core.response.ResponseBuilder;
import com.zhangtory.coinbalance.controller.Request.BalanceHistoryRequest;
import com.zhangtory.coinbalance.model.vo.AccountBalanceVO;
import com.zhangtory.coinbalance.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
*  @Author: ZhangTory
*  @Date: 2021-06-04
*  @Description:  控制层
*/
@RestController
@RequestMapping("record")
@Api(tags = "持仓记录")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @ApiOperation("当前持仓汇总")
    @GetMapping("latest")
    public BaseResponse<List<AccountBalanceVO>> getBalance() {
        return ResponseBuilder.success(recordService.getBalance());
    }

    @ApiOperation("历史持仓汇总")
    @GetMapping("history")
    public BaseResponse<List<List<AccountBalanceVO>>> getBalanceHistory(@RequestBody BalanceHistoryRequest request) {
        return ResponseBuilder.success(recordService.getBalanceHistory(request));
    }

}

