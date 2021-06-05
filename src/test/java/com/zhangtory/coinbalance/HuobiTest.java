package com.zhangtory.coinbalance;

import com.huobi.client.AccountClient;
import com.huobi.client.SubUserClient;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.SubuserAggregateBalance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by Scott on 2021/6/5
 **/
@SpringBootTest
public class HuobiTest {

    @Autowired
    @Qualifier("huobiAccountClient")
    private AccountClient accountClient;

    @Autowired
    private SubUserClient subUserClient;

    @Test
    void accountTest() {
        List<Account> accounts = accountClient.getAccounts();
        accounts.forEach(account -> {
            AccountBalance accountBalance = accountClient.getAccountBalance(AccountBalanceRequest.builder()
                    .accountId(account.getId()).build());
            System.out.println(accountBalance.toString());
        });
    }

    @Test
    void subUserTest() {
        List<SubuserAggregateBalance> subuserAggregateBalance = subUserClient.getSubuserAggregateBalance();
        subuserAggregateBalance.forEach(balance -> {
            System.out.println(balance.toString());
        });
    }

}
