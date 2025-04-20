package com.wj.books.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 基础数据实体
 *
 * @author wujun
 * @date 2025-04-19
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDataEntity extends BaseEntity {

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "deleted")
    private Boolean deleted;

    public void preInsert() {
        this.createTime = new Date();
        this.deleted = false;
    }

    public void preUpdate() {
        this.updateTime = new Date();
    }

    public void preDelete() {
        this.preUpdate();
        this.deleted = true;
    }

}
