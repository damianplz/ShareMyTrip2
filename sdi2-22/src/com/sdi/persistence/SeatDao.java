package com.sdi.persistence;

import com.sdi.model.Seat;
import com.sdi.persistence.util.GenericDao;



public interface SeatDao extends GenericDao<Seat, Long[]> {

	Seat findByUserAndTrip(Long userId, Long tripId);

	public int deleteTestSeats();

}
