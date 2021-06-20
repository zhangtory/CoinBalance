package com.zhangtory.coinbalance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangtory
 * @date 2021/6/20 21:48
 * @description:
 */
@Controller
public class ChartController {

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name","hello pillar");
        return "index";
    }

}
