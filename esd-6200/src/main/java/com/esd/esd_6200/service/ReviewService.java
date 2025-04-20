package com.esd.esd_6200.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esd.esd_6200.dao.ReviewRepository;
import com.esd.esd_6200.pojo.Book;
import com.esd.esd_6200.pojo.Review;
import com.esd.esd_6200.requestModels.ReviewRequest;


@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review review = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        return review != null;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());;
        review.setRating(reviewRequest.getRating());
        review.setReviewDescription(reviewRequest.getReviewDescription().orElse(null));
        review.setUserEmail(userEmail);
        reviewRepository.save(review);
    }
    
    public List<Review> getAllReviews()
    {
        return reviewRepository.findAll();
    }
    
    public List<Review> findByBookId(Long bookId) {
        
        return reviewRepository.findBookById(bookId, 0, 20);
    }
}