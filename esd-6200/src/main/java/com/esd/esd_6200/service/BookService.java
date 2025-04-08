package com.esd.esd_6200.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esd.esd_6200.dao.BookRepository;
import com.esd.esd_6200.pojo.Book;


@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
//    private final CheckoutRepository checkoutRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository
//    		, CheckoutRepository checkoutRepository
    		){
        this.bookRepository = bookRepository;
//        this.checkoutRepository = checkoutRepository;
    }
    
    public List<Book> getAllBooks()
    {
        return bookRepository.findAll();
    }
    
    public Book findBookById(Long id)
    {
        return bookRepository.findById(id);
    }
//
//    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
//        Book book = bookRepository.findById(bookId);
//
//        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
//
//        if (book == null || validateCheckout != null || book.getCopiesAvailable() <= 0) {
//            throw new Exception("Book doesn't exist or already checked out by the user");
//        }
//
//        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
//        bookRepository.save(book);
//
//        Checkout checkout = new Checkout(
//                userEmail,
//                LocalDate.now().toString(),
//                LocalDate.now().plusDays(7).toString(),
//                book.getId()
//        );
//
//        checkoutRepository.save(checkout);
//
//        return book;
//    }
//
//    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
//        return checkoutRepository.findByUserEmailAndBookId(userEmail, bookId) != null;
//    }
//
//    public int currentLoansCount(String userEmail){
//        return checkoutRepository.findBooksByUserEmail(userEmail).size();
//    }
}

