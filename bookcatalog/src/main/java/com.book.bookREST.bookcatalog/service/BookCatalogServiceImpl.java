package com.book.bookREST.bookcatalog.service;

import com.book.bookREST.bean.BookCatalog;
import com.book.mongo.MongoFactory;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service("bookCatalogService")
public class BookCatalogServiceImpl implements BookCatalogService {
    private static final Logger logger = LoggerFactory.getLogger(BookCatalogServiceImpl.class);
    private static final String COLLECTION_NAME = "bookcatalog";
    private static final String DB_NAME = "library";
    private final CodecRegistry pojoCodecRegistry;
    private MongoClient mongoClient;

    public BookCatalogServiceImpl() {
        mongoClient= MongoFactory.getMongo();
        PojoCodecProvider codecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .register(BookCatalog.class)
                .build();
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(codecProvider));
    }

    @Override
    @Transactional
    public void addBookCatalog(BookCatalog bookCatalog) {
        logger.debug("addBookCatalog : BookCatalog {} id {} addded to List",
                bookCatalog.getBookName(), bookCatalog.getBookId());
        MongoCollection<BookCatalog> bookCatalogMongoCollectionCollection = getMongoCollection();
        bookCatalogMongoCollectionCollection.insertOne(bookCatalog);
    }

    @Override
    public List<BookCatalog> listBookCatalog() {
        MongoCollection<BookCatalog> mongoCollection = getMongoCollection();
        List<BookCatalog> list = new ArrayList<>();
        FindIterable<BookCatalog> BookCatalogs = mongoCollection.find(BookCatalog.class);
        for (BookCatalog BookCatalog : BookCatalogs) {
            list.add(BookCatalog);
        }
        logger.info("listBookCatalog : Returned {} BookCatalogs", list.size());
        return list;
    }

    @Override
    public BookCatalog getBookCatalog(String bookId) {
        logger.debug("getBookCatalog : BookCatalog with id {} returned", bookId);
        MongoCollection<BookCatalog> mongoCollection = getMongoCollection();
        return mongoCollection.find(eq("bookId", bookId)).first();
    }
    @Override
    public BookCatalog updateBookCatalog(BookCatalog bookCatalog){
        logger.debug("getBookCatalog : BookCatalog with id {} received", bookCatalog.getBookId());
            MongoCollection<BookCatalog> mongoCollection=getMongoCollection();
            mongoCollection.findOneAndUpdate(
                    eq("bookId", bookCatalog.getBookId()),
                    (set("bookName", bookCatalog.getBookName())));
            return bookCatalog;
    }

    @Override
    @Transactional
    public void deleteBookCatalog(BookCatalog bookCatalog) {
        logger.debug("deleteBookCatalog : BookCatalog with id {} deleted", bookCatalog.getBookId());
        MongoCollection<BookCatalog> mongoCollection = getMongoCollection();
        mongoCollection.deleteOne(eq("bookId", bookCatalog.getBookId()));
    }

    private MongoCollection<BookCatalog> getMongoCollection() {
        return mongoClient.getDatabase(DB_NAME)
                .withCodecRegistry(pojoCodecRegistry).getCollection(COLLECTION_NAME, BookCatalog.class);
    }
}
