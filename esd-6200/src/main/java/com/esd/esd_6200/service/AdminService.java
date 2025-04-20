package com.esd.esd_6200.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esd.esd_6200.dao.BookRepository;
import com.esd.esd_6200.dao.CheckoutRepository;
import com.esd.esd_6200.dao.ReviewRepository;
import com.esd.esd_6200.pojo.Book;
import com.esd.esd_6200.requestModels.AddBookRequest;

@Service
@Transactional
public class AdminService {
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;
    private CheckoutRepository checkoutRepository;

    @Autowired
    public AdminService(BookRepository bookRepository,
                        ReviewRepository reviewRepository,
                        CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public void increaseBookQuantity(Long bookId) throws Exception {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new Exception("Book not found");
        }
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        book.setCopies(book.getCopies() + 1);
        bookRepository.update(book);
    }

    public void decreaseBookQuantity(Long bookId) throws Exception {
        Book book = bookRepository.findById(bookId);
        if (book == null || book.getCopiesAvailable() <= 0 || book.getCopies() <= 0) {
            throw new Exception("Book not found or quantity locked");
        }
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        book.setCopies(book.getCopies() - 1);
        bookRepository.update(book);
    }

    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) throws Exception {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new Exception("Book not found");
        }
        bookRepository.delete(book);
        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }
}