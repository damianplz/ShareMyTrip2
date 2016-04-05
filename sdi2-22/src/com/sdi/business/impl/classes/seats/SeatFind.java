package com.sdi.business.impl.classes.seats;


import java.util.ArrayList;
import java.util.List;

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

	public List<Seat> findByTrip(Long id) {
		SeatDao dao = Factories.persistence.newSeatDao();
		List<Seat> todas = dao.findAll();
		List<Seat> plazas = null;
		if(todas!=null){
			plazas = new ArrayList<Seat>();
			for(Seat s:todas)
				if(s.getTripId().equals(id))
					plazas.add(s);
		}		
		return plazas;
	}

}
