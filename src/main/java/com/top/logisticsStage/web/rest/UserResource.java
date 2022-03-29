package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.UserService;
import com.top.logisticsStage.service.dto.UserAuthListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "用户")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "USER";

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/USER_AUTHORITY/{account}")
    @Timed
    @ApiOperation(value = "查询用户权限")
    public ResponseEntity<List<UserAuthListDTO>> getList(@PathVariable String account) {
        List<UserAuthListDTO> list = userService.getAuthorityByAccount(account);
        log.debug("");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/USER_AUTHORITY")
    @Timed
    @ApiOperation(value = "查询用户权限")
    public void getList() {
        log.debug("用户名："+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        log.debug("=====>测试日志debug级别打印<====");
        log.info("=====>测试日志info级别打印<=====");
        log.error("=====>测试日志error级别打印<====");
        log.warn("=====>测试日志warn级别打印<=====");

        String csdn = "https://blog.csdn.net/qq_27706119";
        String git = "https://github.com/JohnnyHL";
        log.info("======>AndOne丶的CSDN博客：{}；AndOne丶的GitHub地址：{}；", csdn, git);

    }
    /**
     * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     * 获取登录用户的用户名： getPrincipal()
     * 获取通过身份验证的用户的密码： getCredentials()
     * 获取已认证用户的分配角色： getAuthorities()
     * 获取经过身份验证的用户的更多详细信息： getDetails()
     */




}
