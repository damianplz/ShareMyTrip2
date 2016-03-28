package com.sdi.persistence;

public interface PersistenceFactory {
	
	Transaction newTransaction();
	
	RatingDao newRatingDao();

	UserDao newUserDao();

	TripDao newTripDao();

	SeatDao newSeatDao();

	ApplicationDao newApplicationDao();
}
