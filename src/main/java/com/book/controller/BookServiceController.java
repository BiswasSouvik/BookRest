package com.book.controller;

import com.book.bean.BookCatalog;
import com.book.bean.BookInventory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.asynchttpclient.Dsl.asyncHttpClient;

@RestController
@EnableAsync
@RequestMapping("book")
public class BookServiceController {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceController.class);

    private final AsyncHttpClient asyncHttpClient;
    private final ObjectMapper objectMapper;
    public BookServiceController() {
        objectMapper = new ObjectMapper();
        asyncHttpClient = asyncHttpClient();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

    @RequestMapping(value = "addCat", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public  Response addCatalog(@RequestBody BookCatalog bookCatalog) throws IOException, ExecutionException, InterruptedException {
        logger.debug("addCatalog : init request for addCatalog {}", bookCatalog);
        Response response = null;
        RequestBuilder builder = new RequestBuilder("POST")
                .addHeader("Accept","application/json")
                .addHeader("Content-Type","application/json");
        Request request = builder.setUrl(" http://localhost:8081/bookCatalogREST/bookcatalog/add")
                .setBody(objectMapper.writeValueAsString(bookCatalog))
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "addInv", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response addInventory(@RequestBody BookInventory bookInventory) throws ExecutionException, InterruptedException, IOException {
        logger.debug("addInventory : init request for addInventory {}", bookInventory);
        Response response = null;
        RequestBuilder builder = new RequestBuilder("POST")
                .addHeader("Content-Type","application/json");
        Request request = builder.setUrl("http://localhost:8082/bookInventoryREST/bookinventory/add")
                .setBody(objectMapper.writeValueAsString(bookInventory))
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "findallcat", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response findAllCat() throws ExecutionException, InterruptedException {
        logger.debug("findAllCat : init request for findAllCat");
        Response response = null;
        RequestBuilder builder = new RequestBuilder("GET");
        Request request = builder.setUrl("http://localhost:8081/bookCatalogREST/bookcatalog/findall").build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "findallinv", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response findAllInv() throws ExecutionException, InterruptedException {
        logger.debug("findAllInventory : init request findAllInvetory");
        Response response = null;
        RequestBuilder builder = new RequestBuilder("GET");
        Request request = builder.setUrl("http://localhost:8082/bookInventoryREST/bookinventory/findall").build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "findcat/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response findCat(@PathVariable String id) throws ExecutionException, InterruptedException {
        logger.debug("findInventory : init request findInventory with id {}", id);
        Response response = null;
        RequestBuilder builder = new RequestBuilder("GET");
        Request request = builder.setUrl("http://localhost:8081/bookCatalogREST/bookcatalog/find/" + id)
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "findinv/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response findInv(@PathVariable String id) throws ExecutionException, InterruptedException {
        logger.debug("findInv : init request findInv for id {}", id);
        Response response = null;
        RequestBuilder builder = new RequestBuilder("GET");
        Request request = builder.setUrl("http://localhost:8082/bookInventoryREST/bookinventory/find/" + id)
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "deleteinv/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response deleteInv(@PathVariable String id) throws ExecutionException, InterruptedException {
        logger.debug("deleteInv : init request deleteInv for id {}", id);
        Response response = null;
        RequestBuilder builder = new RequestBuilder("DELETE");
        Request request = builder.setUrl("http://localhost:8082/bookInventoryREST/bookinventory/delete/" + id)
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "deletecat/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response deleteCat(@PathVariable String id) throws ExecutionException, InterruptedException {
        logger.debug("deleteCat : init request deleteCat for id {}", id);
        Response response = null;
        RequestBuilder builder = new RequestBuilder("DELETE");
        Request request = builder.setUrl("http://localhost:8081/bookCatalogREST/bookcatalog/delete/" + id)
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "editinv", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response editInv(@RequestBody BookInventory bookInventory) throws ExecutionException, InterruptedException, IOException {
        logger.debug("editInv : init request editInv");
        Response response = null;
        RequestBuilder builder = new RequestBuilder("POST")
                .addHeader("Content-Type","application/json");
        Request request = builder.setUrl("http://localhost:8082/bookInventoryREST/bookinventory/edit")
                .setBody(objectMapper.writeValueAsString(bookInventory))
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }

    @RequestMapping(value = "editcat", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @Async
    public Response editCat(@RequestBody BookCatalog bookCatalog) throws ExecutionException, InterruptedException, IOException {
        logger.debug("editCat : init request editCat");
        Response response = null;
        RequestBuilder builder = new RequestBuilder("POST")
                .addHeader("Content-Type","application/json");
        Request request = builder.setUrl("http://localhost:8081/bookCatalogREST/bookcatalog/edit")
                .setBody(objectMapper.writeValueAsString(bookCatalog))
                .build();
        ListenableFuture<Response> listenableFuture = asyncHttpClient.executeRequest(request);
        if(listenableFuture.isDone())
            response = listenableFuture.get();
        return response;
    }
}