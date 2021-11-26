package com.zbf.manage.common.exception;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorModel {
    /**
     * 错误标志
     */
    private String model;
    /**
     * 错误描述
     */
    private String msg;
}
