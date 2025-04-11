package com.esd.esd_6200.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import com.esd.esd_6200.pojo.Book;
import org.springframework.web.bind.annotation.*;

import com.esd.esd_6200.service.BookService;

import com.esd.esd_6200.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
	
	@Autowired
    private BookService bookService;

    @Autowired
    public BookController(BookService bookservice) {
//        this.bookService = bookservice;
    }
    
//    @GetMapping("/books")
//    public List<Book> getBooks() {
//      return bookService.getAllBooks();
//    }
    
    @GetMapping("/book")
    public Book getBookById(@RequestParam(name = "bookId") Long bookId) {
      return bookService.findBookById(bookId);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Book> booksPage = bookService.getPaginatedBooks(page, size, sortBy, direction);
        
        Map<String, Object> response = new HashMap<>();
        response.put("books", booksPage.getContent());
        response.put("currentPage", booksPage.getNumber());
        response.put("totalItems", booksPage.getTotalElements());
        response.put("totalPages", booksPage.getTotalPages());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search/title")
    public ResponseEntity<Map<String, Object>> searchBooksByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Book> booksPage = bookService.findBooksByTitle(title, page, size, sortBy, direction);

        Map<String, Object> response = new HashMap<>();
        response.put("books", booksPage.getContent());
        response.put("currentPage", booksPage.getNumber());
        response.put("totalItems", booksPage.getTotalElements());
        response.put("totalPages", booksPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search/category")
    public ResponseEntity<Map<String, Object>> searchBooksByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Book> booksPage = bookService.findBooksByCategory(category, page, size, sortBy, direction);

        Map<String, Object> response = new HashMap<>();
        response.put("books", booksPage.getContent());
        response.put("currentPage", booksPage.getNumber());
        response.put("totalItems", booksPage.getTotalElements());
        response.put("totalPages", booksPage.getTotalPages());

        return ResponseEntity.ok(response);
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
