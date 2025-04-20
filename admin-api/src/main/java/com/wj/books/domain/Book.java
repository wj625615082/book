package com.wj.books.domain;

import com.wj.books.commons.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 图书实体类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "图书实体类，包含图书的基本信息和状态")
public class Book extends BaseEntity {

    /**
     * 国际标准书号
     */
    @NotBlank(message = "请输入书号")
    @Column(name = "isbn", unique = true, nullable = false)
    @ApiModelProperty(value = "国际标准书号", required = true, example = "978-3-16-148410-0")
    private String isbn;

    /**
     * 书名
     */
    @NotBlank(message = "请输入书名")
    @Column(name = "title", nullable = false)
    @ApiModelProperty(value = "书名", required = true, example = "Java编程思想")
    private String title;

    /**
     * 作者
     */
    @Column(name = "author", nullable = false)
    @ApiModelProperty(value = "作者", required = true, example = "Bruce Eckel")
    private String author;

    /**
     * 出版社
     */
    @Column(name = "publisher")
    @ApiModelProperty(value = "出版社", example = "机械工业出版社")
    private String publisher;

    /**
     * 出版日期
     */
    @Column(name = "publish_date")
    @ApiModelProperty(value = "出版日期", example = "2023-01-01")
    private LocalDate publishDate;

    /**
     * 定价
     */
    @Column(name = "price", precision = 10, scale = 2)
    @ApiModelProperty(value = "定价", example = "108.00")
    private BigDecimal price;

    /**
     * 分类号
     */
    @Column(name = "category_code")
    @ApiModelProperty(value = "分类号", example = "it技术")
    private String categoryCode = "it技术";

    /**
     * 馆藏位置
     */
    @Column(name = "location")
    @ApiModelProperty(value = "馆藏位置", example = "1号书柜")
    private String location = "1号书柜";

    /**
     * 状态:1-在架,2-借出,3-维修中,4-丢失
     */
    @Column(name = "status", nullable = false)
    @ApiModelProperty(value = "状态:1-在架,2-借出,3-维修中,4-丢失", required = true, allowableValues = "1, 2, 3, 4", example = "1")
    private Integer status = 1;

    /**
     * 总副本数
     */
    @Column(name = "total_copies", nullable = false)
    @ApiModelProperty(value = "总副本数", required = true, example = "1")
    private Integer totalCopies = 1;

    /**
     * 可借副本数
     */
    @Column(name = "available_copies", nullable = false)
    @ApiModelProperty(value = "可借副本数", required = true, example = "1")
    private Integer availableCopies = 1;

    /**
     * 入馆日期
     */
    @Column(name = "register_date", nullable = false)
    @ApiModelProperty(value = "入馆日期", required = true, example = "2023-01-01T12:00:00")
    private LocalDateTime registerDate = LocalDateTime.now();

    /**
     * 图书简介
     */
    @Column(name = "description", length = 500)
    @ApiModelProperty(value = "图书简介", example = "本书详细介绍了Java编程的基本概念和高级特性。")
    private String description;

    /**
     * 封面图片URL
     */
    @Column(name = "cover_url", length = 255)
    @ApiModelProperty(value = "封面图片URL", example = "http://example.com/book_cover.jpg")
    private String coverUrl;

    // 状态常量
    public static final int STATUS_AVAILABLE = 1;    // 在架
    public static final int STATUS_BORROWED = 2;     // 借出
    public static final int STATUS_MAINTENANCE = 3;  // 维修中
    public static final int STATUS_LOST = 4;         // 丢失
}
