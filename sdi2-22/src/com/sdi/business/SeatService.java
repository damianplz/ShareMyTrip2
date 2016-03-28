package com.sdi.business;

import java.util.List;

import com.sdi.model.Seat;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface SeatService {
	
	void saveSeat(Seat seat) throws AlreadyPersistedException;
	void deleteSeat(Seat seat) throws NotPersistedException;
	Seat findByUserAndSeat(Long userId, Long tripId) throws NotPersistedException;
	void updateSeat(Seat seat) throws NotPersistedException;
	List<Seat> getSeats() throws NotPersistedException;

}
