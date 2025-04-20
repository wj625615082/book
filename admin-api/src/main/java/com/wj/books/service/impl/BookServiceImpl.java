package com.wj.books.service.impl;

import com.wj.books.domain.Book;
import com.wj.books.dto.BookDto;
import com.wj.books.repository.BookRepository;
import com.wj.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 图书管理服务类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    /*
     * @Description: 分页查询图书信息
     * */
    @Override
    public Page<Book> list(BookDto bookDto) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        Pageable pageable = PageRequest.of(bookDto.getPage() - 1, bookDto.getSize(),sort);
        // 动态查询条件构建
        if (!StringUtils.isEmpty(bookDto.getTitle())
                || !StringUtils.isEmpty(bookDto.getIsbn())) {

            return bookRepository.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (!StringUtils.isEmpty(bookDto.getIsbn())) {
                    predicates.add(cb.equal(root.get("isbn"), bookDto.getIsbn()));
                }
                if (!StringUtils.isEmpty(bookDto.getTitle())) {
                    predicates.add(cb.like(root.get("title"), "%" + bookDto.getTitle() + "%"));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }, pageable);
        }

        return bookRepository.findAll(pageable);
    }

    /*
     * @Description: 根据ID获取图书信息
     * @Cacheable：有缓存记录，没有则查询数据库
     * */
    @Cacheable(value = "book", key = "#id")
    @Override
    public Book getById(Integer id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    /*
     * @Description: 保存图书信息
     * @CacheEvict：清空对应图书缓存
     * */
    @Override
    public Book save(Book book) {
        Optional<Book> existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("图书ISBN已存在");
        }
        return bookRepository.save(book);
    }

    /*
     * @Description: 修改图书信息
     * @CacheEvict：清空对应图书缓存
     * */
    @CacheEvict(value = "book", key = "#book.id")
    @Override
    public Book update(Book book) {
        bookRepository.deleteById(book.getId());
        return bookRepository.save(book);
    }

    /*
     * @Description: 根据ID删除图书信息
     * @CacheEvict：清空对应图书缓存
     * */
    @CacheEvict(value = "book", key = "#id", allEntries = false)
    @Override
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

}
