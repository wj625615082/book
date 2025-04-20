package com.wj.books.dto;

import com.wj.books.commons.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

/**
 * 图书实体类
 *
 * @author wujun
 * @date 2025-04-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "图书实体类")
public class BookDto extends BaseEntity {

    @ApiModelProperty(value = "国际标准书号", required = true)
    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @ApiModelProperty(value = "书名", required = true)
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty(value = "作者", required = true)
    @Column(name = "author", nullable = false)
    private String author;

    @ApiModelProperty(value = "出版社")
    @Column(name = "publisher")
    private String publisher;

    @ApiModelProperty(value = "分类号")
    @Column(name = "category_code")
    private String categoryCode;

    @ApiModelProperty(value = "状态:1-在架,2-借出,3-维修中,4-丢失", required = true)
    @Column(name = "status", nullable = false)
    private Integer status;

    @ApiModelProperty(value = "页码")
    @Column(name = "page")
    private Integer page = 1;

    @ApiModelProperty(value = "每页显示条数")
    @Column(name = "size")
    private Integer size = 20;

    // 状态常量
    public static final int STATUS_AVAILABLE = 1;    // 在架
    public static final int STATUS_BORROWED = 2;     // 借出
    public static final int STATUS_MAINTENANCE = 3;  // 维修中
    public static final int STATUS_LOST = 4;         // 丢失
}
