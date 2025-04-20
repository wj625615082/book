package com.wj.books.commons;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 基础实体类
 *
 * @author wujun
 * @date 2025-04-19
 *
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
}
