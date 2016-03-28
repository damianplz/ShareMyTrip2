package com.sdi.business;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface TripService {
	void saveTrip(Trip trip) throws AlreadyPersistedException;
	void deleteTrip(Trip trip) throws NotPersistedException;
	Trip findById(Long tripId) throws NotPersistedException;
	Trip findByPromoterAndArrival(Long userId, Date arrival) throws NotPersistedException;
	void updateTrip(Trip trip) throws NotPersistedException;
	List<Trip> getTrips() throws NotPersistedException;

}
