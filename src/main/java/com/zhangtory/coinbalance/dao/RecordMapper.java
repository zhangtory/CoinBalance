package com.zhangtory.coinbalance.dao;

import org.apache.ibatis.annotations.Mapper;
import com.zhangtory.coinbalance.model.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @Author: ZhangTory
* @Date: 2021-06-04
* @Description:  Mapper 接口
*/
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

}

