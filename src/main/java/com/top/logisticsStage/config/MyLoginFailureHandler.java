package com.top.logisticsStage.config;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MyLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("===========登陆失败================");
        PrintWriter printWriter = httpServletResponse.getWriter();
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("result", "1");
        msgMap.put("msg", "登录失败");
        printWriter.write(new Gson().toJson(msgMap));
        printWriter.flush();
        printWriter.close();
    }
}
