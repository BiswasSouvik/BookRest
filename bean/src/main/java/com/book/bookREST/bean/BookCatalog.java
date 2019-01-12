package com.book.bookREST.bean;

import java.io.Serializable;

public class BookCatalog implements Serializable {

    private String bookId;
    private String bookName;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
