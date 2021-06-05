package com.zhangtory.coinbalance.model.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.KeySequence;
import lombok.*;

/**
 * @Author: ZhangTory
 * @Date: 2021-06-04
 * @Description: 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 记录时间
     */
    private LocalDateTime createTime;

    private BigDecimal usd;

    public LocalDateTime getCreateTime() {
        return createTime == null ?
                LocalDateTime.now().withMinute(0).withSecond(0).withNano(0) : createTime;
    }

}
