package com.zbf.manage.controller;

import com.zbf.manage.common.util.RetResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/token")
@Api(value = "/token", description = "保活服务", tags = "token")
public class TokenController {



    @ApiOperation(value = "需求1", notes = "花少的需求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "需求2", required = true)
    })
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return String.format("花少需要你们的 %s!", name);
    }


    @PostMapping()
    public ResponseEntity ping(@PathVariable String token) {
        if(StringUtils.isEmpty(token)){
            return new ResponseEntity<>(RetResponse.error("token为空或错误"), HttpStatus.OK);
        }
        if(token.equals("CFDAD14E-8785-16D7-E032-6FD489AD0130")){

        }
        return new ResponseEntity<>(RetResponse.success("成功"), HttpStatus.OK);
    }

}
