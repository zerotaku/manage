package com.zbf.manage.controller;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @GetMapping("/index")
    public String index() {
        return "花有重开日，人无再少年 → 我是花少【少宫主花无缺】!";
    }

}

//@Api(tags = "首页模块")
//@RestController
//public class TokenController {
//
//    @ApiImplicitParam(name = "name",value = "姓名",required = true)
//    @ApiOperation(value = "向客人问好")
//    @GetMapping("/sayHi")
//    public ResponseEntity<String> sayHi(@RequestParam(value = "name")String name){
//        return ResponseEntity.ok("Hi:"+name);
//    }
//
//}
