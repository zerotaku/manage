package com.zbf.manage.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * access
 * @author
 */
@Data
public class Access extends BaseEntity implements Serializable {
    private Integer id;

    /**
     * 唯一标识
     */
    private String appkey;

    /**
     * 回调次数
     */
    private String callbacksNumber;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 保活区间
     */
    private String keepAlive;

    /**
     * 是否启用
     */
    private Boolean isEnable;

    private static final long serialVersionUID = 1L;

}
