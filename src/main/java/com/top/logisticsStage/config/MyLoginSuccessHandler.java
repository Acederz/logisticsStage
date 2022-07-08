package com.top.logisticsStage.config;

import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("===========登陆成功================");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        WebAuthenticationDetails userDetailsService = (WebAuthenticationDetails) securityContext.getAuthentication().getDetails();
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        System.out.println("===userDetailsService.getRemoteAddress()===>>" + userDetailsService.getRemoteAddress());
        System.out.println("===userDetailsService.getSessionId()===>>" + userDetailsService.getSessionId());
        System.out.println("===userDetails.getRemoteAddress()===>>" + userDetails.getUsername());
        System.out.println("===userDetails.getPassword()===>>" + userDetails.getPassword());
        System.out.println("===userDetails.getAuthorities()===>>" + userDetails.getAuthorities());
        PrintWriter printWriter = response.getWriter();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        String token = TokenUtil.sign(userDetails.getUsername());
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("code", "200");
        msgMap.put("token", token);
        msgMap.put("account", userDetails.getUsername());
        msgMap.put("msg", "登录成功");
        printWriter.write(new Gson().toJson(msgMap));
        printWriter.flush();
        printWriter.close();
    }
}
