package com.wj.books.repository;

import com.wj.books.domain.Book;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Integer>,
        JpaSpecificationExecutor<Book> {

    Optional<Book> findByIsbn(String isbn);
}
