package com.book.bookREST.bookinventory.service;

import com.book.bean.BookInventory;

import java.util.List;

public interface BookInventoryService {
    void addBookInventory(BookInventory bookInventory);

    List<BookInventory> listBookInventory();

    BookInventory getBookInventory(String bookId);

    void deleteBookInventory(BookInventory bookInventory);

    BookInventory updateBookInventory(BookInventory bookInventory);
}
