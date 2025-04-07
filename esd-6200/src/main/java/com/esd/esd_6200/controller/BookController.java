package com.esd.esd_6200.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.esd.esd_6200.service.BookService;

import com.esd.esd_6200.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookservice) {
        this.bookService = bookservice;
    }

//    @GetMapping("/secure/currentloans/count")
//    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
//        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        return bookService.currentLoansCount(userEmail);
//    }
//
//    @GetMapping("/secure/ischeckedout/byuser")
//    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
//        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        return bookService.checkoutBookByUser(userEmail, bookId);
//    }
//
//    @PutMapping("/secure/checkout")
//    public Book checkoutBook (@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
//        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        return bookService.checkoutBook(userEmail, bookId);
//    }

}
