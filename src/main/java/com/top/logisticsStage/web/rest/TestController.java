package com.top.logisticsStage.web.rest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class TestController {

    @GetMapping("api/login")
    public String login() {
        return "login";
    }

    @GetMapping("email")
    public String email() {
        return "email";
    }

    @GetMapping("menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("success")
    @ResponseBody
    public String loginS() {
        return "SUCCESS";
    }

    @GetMapping("error")
    @ResponseBody
    public String loginE() {
        return "ERROR";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("getData")
    @ResponseBody
    public String getData() {
        return "date";
    }
}
