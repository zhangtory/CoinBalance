package com.zhangtory.coinbalance.service;

import com.zhangtory.coinbalance.model.entity.Record;

import java.io.IOException;
import java.util.List;

/**
 * Created by Scott on 2021/6/4
 **/
public interface GetBalanceService {

    /**
     * 获取账户余额
     * @return
     */
    List<Record> getBalance() throws IOException;

}
