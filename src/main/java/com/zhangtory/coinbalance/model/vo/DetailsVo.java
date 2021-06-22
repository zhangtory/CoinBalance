package com.zhangtory.coinbalance.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 2021/6/22
 **/
@Data
public class DetailsVo {

    private String type = "line";

    private boolean smooth = true;

    private String name;

    private List<String> data;

    public DetailsVo(String name) {
        this.name = name;
        data = new ArrayList<>();
    }

}
