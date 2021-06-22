package com.zhangtory.coinbalance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangtory.coinbalance.controller.Request.BalanceHistoryRequest;
import com.zhangtory.coinbalance.model.entity.Record;
import com.zhangtory.coinbalance.model.vo.AccountBalanceVO;

import java.util.List;


/**
*  @Author: ZhangTory
*  @Date: 2021-06-04
*  @Description:  服务类
*/
public interface RecordService extends IService<Record> {

    /**
     * 获取当前持仓
     * @return
     */
    List<AccountBalanceVO> getBalance();

    /**
     * 记录持仓
     */
    void logBalanceRecord();

    /**
     * 获取持仓历史
     * @return
     */
    List<List<AccountBalanceVO>> getBalanceHistory(BalanceHistoryRequest request);

}
