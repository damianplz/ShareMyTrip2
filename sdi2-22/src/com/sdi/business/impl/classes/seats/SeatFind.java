package com.sdi.business.impl.classes.seats;


import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.exception.NotPersistedException;

public class SeatFind {

	public Seat findById(Long userId, Long tripId) throws NotPersistedException {
		SeatDao dao = Factories.persistence.newSeatDao();
		Seat plaza = dao.findByUserAndTrip(userId, tripId);
		return plaza;
	}

}
