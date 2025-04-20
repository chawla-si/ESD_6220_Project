package com.esd.esd_6200.controller;


import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.esd.esd_6200.pojo.Review;
import com.esd.esd_6200.requestModels.ReviewRequest;
import com.esd.esd_6200.service.ReviewService;
import com.esd.esd_6200.utils.ExtractJWT;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")


public class ReviewController {

    private ReviewService reviewService;

    public ReviewController (ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    @GetMapping("/r")
    public List<Review> getBooks() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token,
                                    @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        System.out.println("In Review Controller userEmail: "+ userEmail);
        return reviewService.userReviewListed(userEmail, bookId);
    }

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value="Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
    
    @GetMapping("/search/findBookById")
    public List<Review> findBookById(@RequestParam("bookId") Long bookId) {
        return reviewService.findByBookId(bookId);
    }
}
