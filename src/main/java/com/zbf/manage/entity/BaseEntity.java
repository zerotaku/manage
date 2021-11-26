package com.zbf.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *基础模板entity
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseEntity {
    private Date createTime;
    private Date updateTime;
    private String creator;
    private String updater;
    private Integer version;//版本标记，默认从0开始
    private Integer isSync;//同步标记，默认为0未同步
    private String extend;//备注
    private Integer extend1;//扩展字段-整型
    private Double extend2;//扩展字段-浮点
    private String extend3;//扩展字段-字符串1
    private String extend4;//扩展字段-字符串2
    private String extend5;//扩展字段-字符串3
    private String extend6;//扩展字段-字符串4
    private String extend7;//扩展字段-文本
}
