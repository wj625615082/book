package com.wj.books.service;

import com.wj.books.domain.Book;
import com.wj.books.dto.BookDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface BookService {

    /**
     * 列表查询
     *
     * @param bookDto 查询dto
     * @return 分页列表
     */
    Page<Book> list(BookDto bookDto);

    @Cacheable(value = "book", key = "#id")
    Book getById(Integer id);

    @CacheEvict(value = "book", key = "#book.id", allEntries = false)
    Book save(Book book);

    /*
     * @Description: 修改图书信息
     * @CacheEvict：清空对应图书缓存
     * */
    @CacheEvict(value = "book", key = "#book.id", allEntries = false)
    Book update(Book book);

    @CacheEvict(value = "book", key = "#id", allEntries = false)
    void deleteById(Integer id);
}
