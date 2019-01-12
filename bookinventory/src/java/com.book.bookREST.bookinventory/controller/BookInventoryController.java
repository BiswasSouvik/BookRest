package com.book.bookREST.bookinventory.controller;

import com.book.bean.BookInventory;
import com.book.bookREST.bookinventory.service.BookInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookinventory")
class BookInventoryController {
    private static final Logger logger = LoggerFactory.getLogger(BookInventoryController.class);
    private BookInventoryService bookInventoryService;

    public BookInventoryController(BookInventoryService bookInventoryService) {
        this.bookInventoryService = bookInventoryService;
    }

    @RequestMapping(value = "findall", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    ResponseEntity<List<BookInventory>> findAll() {
        logger.debug("findAll : init request findAll");
        return new ResponseEntity<>(bookInventoryService.listBookInventory(), HttpStatus.OK);
    }

    @RequestMapping(value = "find/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    ResponseEntity<BookInventory> find(@PathVariable("id") String id) {
        logger.debug("find : init request find");
        final BookInventory bookInventory = bookInventoryService.getBookInventory(id);
        return new ResponseEntity<>(bookInventory, HttpStatus.OK);
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    ResponseEntity<BookInventory> updateBookInventory(@RequestBody BookInventory bookInventory) {
        logger.debug("updateBookInventory : init request updateBookInventory");
        return new ResponseEntity<>(bookInventoryService.updateBookInventory(bookInventory), HttpStatus.OK);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    ResponseEntity<BookInventory> add(@RequestBody BookInventory bookInventory) throws Exception {
        logger.debug("add : init request add");
        BookInventory temp = bookInventoryService.getBookInventory(bookInventory.getBookId());
        if (temp != null) {
            throw new Exception("BookCatalog already exists");
        } else {
            bookInventoryService.addBookInventory(bookInventory);
            return new ResponseEntity<>(bookInventory, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    void delete(@PathVariable("id") String id) throws Exception {
        logger.debug("delete : init request delete");
        BookInventory temp = bookInventoryService.getBookInventory(id);
        if (temp == null) {
            throw new Exception("BookInventory doesnot exists");
        } else {
            bookInventoryService.deleteBookInventory(bookInventoryService.getBookInventory(id));
        }
    }
}
