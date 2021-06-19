package com.zhangtory.coinbalance.model.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.KeySequence;
import lombok.*;

/**
 * @Author: ZhangTory
 * @Date: 2021-06-19
 * @Description: 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LockBalance implements Serializable {

    private Integer id;

    /**
     * 交易所
     */
    private String exchange;

    /**
     * 账户类型
     */
    private String account;

    /**
     * 币种
     */
    private String currency;

    /**
     * 总额
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
