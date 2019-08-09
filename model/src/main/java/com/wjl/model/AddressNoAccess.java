package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:非准入地区表
 * User: ZHAOJP
 */
@Data
public class AddressNoAccess {
    /**
     * 主键
     */
    private Long id;
    /**
     * 身份证编码
     */
    private String certNo;
    /**
     * 名称
     */
    private String districtName;
    /**
     * 身份证匹配位数，底层代码中需要和身份证匹配的位数，一般为4或者6位
     */
    private Integer idMatch;
    /**
     * 是否生效为非准入区域，1为生效、0为未生效
     */
    private Integer effectIndex;
    /**
     * 生效日期
     */
    private Date effectDate;
}
