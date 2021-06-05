package com.zhangtory.coinbalance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kucoin.sdk.KucoinRestClient;
import com.zhangtory.coinbalance.controller.Request.BalanceHistoryRequest;
import com.zhangtory.coinbalance.dao.RecordMapper;
import com.zhangtory.coinbalance.model.entity.Record;
import com.zhangtory.coinbalance.model.vo.AccountBalanceVO;
import com.zhangtory.coinbalance.service.GetBalanceService;
import com.zhangtory.coinbalance.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*  @Author: ZhangTory
*  @Date: 2021-06-04
*  @Description:  服务实现类
*/
@Service
@Slf4j
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private KucoinRestClient restClient;

    @Override
    public List<AccountBalanceVO> getBalance() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        List<AccountBalanceVO> result = getRecordList(now);
        return result;
    }

    @Override
    public void logBalanceRecord() {
        Map<String, GetBalanceService> beansMap = applicationContext.getBeansOfType(GetBalanceService.class);
        beansMap.values().forEach(balance -> {
            try {
                List<Record> list = balance.getBalance();
                // 获取币价
                list.forEach(record -> {
                    record.setRmb(record.getAmount().multiply(getQuotation(record.getCurrency())));
                });
                log.info(JSONObject.toJSONString(list));
                this.saveBatch(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<List<AccountBalanceVO>> getBalanceHistory(BalanceHistoryRequest request) {
        List<List<AccountBalanceVO>> result = new ArrayList<>();
        for (Integer i = 0; i < request.getNum(); i++) {
            LocalDateTime time = LocalDateTime.now().plusHours(i * -1L).withMinute(0).withSecond(0).withNano(0);
            List<AccountBalanceVO> voList = getRecordList(time);
            result.add(voList);
        }
        return result;
    }

    private List<AccountBalanceVO> getRecordList(LocalDateTime time) {
        List<AccountBalanceVO> voList = new ArrayList<>();
        List<Record> list = this.list(new QueryWrapper<Record>().lambda()
                .eq(Record::getCreateTime, time));
        list.forEach(record -> {
            AccountBalanceVO vo = new AccountBalanceVO();
            BeanUtils.copyProperties(record, vo);
            voList.add(vo);
        });
        return voList;
    }

    /**
     * 获取行情价格
     * @param currency
     * @return
     */
    private BigDecimal getQuotation(String currency) {
        try {
            return restClient.currencyAPI().getFiatPrice("RMB", currency).get(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

}
