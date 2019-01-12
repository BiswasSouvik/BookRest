package com.book.bookREST.bookcatalog.service;


import com.book.bookREST.bean.BookCatalog;

import java.util.List;

public interface BookCatalogService {
    void addBookCatalog(BookCatalog bookCatalog);

    List<BookCatalog> listBookCatalog();

    BookCatalog getBookCatalog(String bookId);

    void deleteBookCatalog(BookCatalog bookCatalog);

    BookCatalog updateBookCatalog(BookCatalog bookCatalog);
}
