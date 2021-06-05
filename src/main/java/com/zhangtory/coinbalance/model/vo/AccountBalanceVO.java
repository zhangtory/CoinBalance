package com.zhangtory.coinbalance.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Scott on 2021/6/4
 **/
@Data
public class AccountBalanceVO {

    /**
     * 交易所
     */
    @ApiModelProperty("交易所")
    private String exchange;

    /**
     * 账户类型
     */
    @ApiModelProperty("账户类型")
    private String account;

    /**
     * 币种
     */
    @ApiModelProperty("币种")
    private String currency;

    /**
     * 总额
     */
    @ApiModelProperty("总额")
    private BigDecimal amount;

    @ApiModelProperty("rmb")
    private BigDecimal rmb;

    @ApiModelProperty("记录时间")
    private LocalDateTime createTime;

}
