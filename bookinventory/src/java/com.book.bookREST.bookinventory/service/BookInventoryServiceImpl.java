package com.book.bookREST.bookinventory.service;

import com.book.bean.BookInventory;
import com.book.mongo.MongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service("bookInventoryService")
public class BookInventoryServiceImpl implements BookInventoryService {
    private static final Logger logger = LoggerFactory.getLogger(BookInventoryServiceImpl.class);
    private static final String COLLECTION_NAME = "bookinventory";
    private static final String DB_NAME = "library";
    private final CodecRegistry pojoCodecRegistry;
    private MongoClient mongoClient;

    public BookInventoryServiceImpl() {
        mongoClient = MongoFactory.getMongo();
        PojoCodecProvider codecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .register(BookInventory.class)
                .build();
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(codecProvider));
    }

    @Override
    public void addBookInventory(BookInventory bookInventory) {
        logger.debug("addBookInventory : BookInventory with author {} id {} addded to List", bookInventory.getBookId(), bookInventory.getAuthor());
        MongoCollection<BookInventory> bookCatalogMongoCollectionCollection = getMongoCollection();
        bookCatalogMongoCollectionCollection.insertOne(bookInventory);
    }

    @Override
    public List<BookInventory> listBookInventory() {
        MongoCollection<BookInventory> mongoCollection = getMongoCollection();
        List<BookInventory> list = new ArrayList<>();
        FindIterable<BookInventory> bookInventories = mongoCollection.find(BookInventory.class);
        for (BookInventory bookInventory : bookInventories) {
            list.add(bookInventory);
        }
        logger.debug("listBookInventory : BookInventory list of size{} returned", list.size());
        return list;
    }

    @Override
    public BookInventory getBookInventory(String bookId) {
        logger.debug("getBookInventory : BookInventory with id {} returned", bookId);
        MongoCollection<BookInventory> mongoCollection = getMongoCollection();
        return mongoCollection.find(eq("bookId", bookId)).first();
    }

    @Override
    public void deleteBookInventory(BookInventory bookInventory) {
        logger.debug("deleteBookInventory : BookInventory with id {} deleted", bookInventory.getBookId());
        MongoCollection<BookInventory> mongoCollection = getMongoCollection();
        mongoCollection.deleteOne(eq("bookId", bookInventory.getBookId()));
    }

    @Override
    public BookInventory updateBookInventory(BookInventory bookInventory) {
        logger.debug("updateBookInventory : BookInventory with id {} received", bookInventory.getBookId());
        MongoCollection<BookInventory> mongoCollection = getMongoCollection();
        BasicDBObject filter = new BasicDBObject("bookId", bookInventory.getBookId());
        BasicDBObject newValue = new BasicDBObject("author", bookInventory.getAuthor())
                .append("noOfPages", bookInventory.getNoOfPages())
                .append("available", bookInventory.isAvailable());
        BasicDBObject updateOperationDocument = new BasicDBObject("$set", newValue);
        mongoCollection.findOneAndUpdate(filter, updateOperationDocument);
        return bookInventory;
    }

    private MongoCollection<BookInventory> getMongoCollection() {
        return mongoClient.getDatabase(DB_NAME)
                .withCodecRegistry(pojoCodecRegistry).getCollection(COLLECTION_NAME, BookInventory.class);
    }
}
