package com.zbf.manage.controller;

import com.zbf.manage.common.util.RetResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/tool")
@Api(value = "/tool", description = "工具", tags = "tool")
public class ToolController {

    @ApiOperation(value = "需求1", notes = "花少的需求")
    @ApiImplicitParams({
    })
    @GetMapping("/table/update")
    public String generateTableUpdateSQL(@RequestParam String tableName, @RequestParam String tableKey, @RequestParam String pojoKey, @RequestParam String fieldStr) {
        String[] fields = fieldStr.split(",");
        StringBuffer xml = new StringBuffer();
        xml.append("<update id=\"updateList\" parameterType=\"list\">");
        xml.append("update ").append(tableName);
        xml.append("<trim prefix=\"set\" suffixOverrides=\",\">");
        for (String field : fields) {
            xml.append("<trim prefix=\"").append(field).append(" =case\" suffix=\"end,\">");
            xml.append("<foreach collection=\"list\" item=\"i\" index=\"index\">");
            xml.append("<if test=\"i.").append(field).append("!=null\">");
            xml.append("when ").append(tableKey).append("=#{i.").append(pojoKey).append("}").append(" then #{i.").append(field).append("}");
            xml.append("</if> </foreach>  </trim>");
        }
        xml.append("</trim> where <foreach collection=\"list\" separator=\"or\" item=\"i\" index=\"index\">");
        xml.append(tableKey).append("=#{i.").append(pojoKey).append("}");
        xml.append("</foreach> </update>");
        return String.format(xml.toString());
    }


}
