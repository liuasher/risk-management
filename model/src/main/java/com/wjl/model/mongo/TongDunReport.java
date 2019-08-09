package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 同盾报表
 *
 * @author mayue
 * @date 2018/3/5
 */
@Data
public class TongDunReport {
    @Id
    private String id;

    private Long userId;
    /**
     * 查询时间
     */
    private Long queryTime;
    /**
     * 同盾black_box
     */
    private String blackBox;
    /**
     * 是否SUBMMIT同盾接口成功
     */
    private String success;
    /**
     * 同盾report_id
     */
    private String reportId;
    /**
     * 风险分数
     */
    private Long finalScore;
    /**
     * 风险结果，Accept:建议通过；Review:建议审核；Reject:建议拒绝
     */
    private String	finalDecision;
    /**
     * 报表
     */
    private JSONObject report;
}
