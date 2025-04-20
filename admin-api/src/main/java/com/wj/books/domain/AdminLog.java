package com.wj.books.domain;

import com.wj.books.commons.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author wujun
 * @date 2025-04-19
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "用户日志表")
@Builder
@AllArgsConstructor
public class AdminLog extends BaseEntity {

    @Column(name = "admin")
    @ApiModelProperty(value = "管理员名称", required = true, example = "admin")
    private String admin;

    @Column(name = "path")
    @ApiModelProperty(value = "请求路径", required = true, example = "/api/book/list")
    private String path;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", required = true, example = "2023-01-01T12:00:00")
    private Date createTime;

    @Column(name = "action")
    @ApiModelProperty(value = "操作描述", required = true, example = "获取图书列表")
    private String action;

    @Column(name = "type")
    @ApiModelProperty(value = "操作类型", required = true, allowableValues = "1, 2", example = "1")
    private Integer type;

    @Column(name = "result")
    @ApiModelProperty(value = "操作结果", required = true, example = "成功")
    private String result;

    @Column(name = "ip")
    @ApiModelProperty(value = "客户端IP地址", required = true, example = "127.0.0.1")
    private String ip;

    public AdminLog() {

    }
}
