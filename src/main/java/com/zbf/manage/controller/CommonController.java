package com.zbf.manage.controller;

import com.zbf.manage.common.util.RetResponse;
import com.zbf.manage.service.AccessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/common")
@Api(value = "/common", description = "保活服务", tags = "common")
public class CommonController {

    @Autowired
    private AccessService accessService;


    @ApiOperation(value = "需求1", notes = "花少的需求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "需求2", required = true)
    })
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return String.format("花少需要你们的 %s!", name);
    }


    @PostMapping("keepAlive/{key}")
    public ResponseEntity keepAlive(@PathVariable String key) {
        return accessService.keepAlive(key);
    }

}
