package com.sdi.persistence;

import java.util.Date;

import com.sdi.model.Trip;
import com.sdi.persistence.util.GenericDao;



public interface TripDao extends GenericDao<Trip, Long> {

	Trip findByPromoterIdAndArrivalDate(Long id, Date arrivalDate);
	
	//Delete the trips of test
	public int deleteTestTrips();

}
