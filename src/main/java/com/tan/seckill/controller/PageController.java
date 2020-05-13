package com.tan.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName PageController
 * @Description TODO
 * @Author tan
 * @Date 2020/4/16 21:35
 * @Version 1.0
 **/
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }
}
