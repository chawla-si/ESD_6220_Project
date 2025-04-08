package com.esd.esd_6200.requestModels;

import java.util.Optional;

import lombok.Data;

@Data
public class ReviewRequest {
    private double rating;

    private Long bookId;

    private Optional<String> reviewDescription;

	public Object getBookId() {
		return bookId;
	}

	public Object getRating() {
		return rating;
	}

	public Object getReviewDescription() {
		// TODO Auto-generated method stub
		return reviewDescription;
	}
}