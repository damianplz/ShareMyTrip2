package com.sdi.business.impl.classes.trips;

import java.util.Date;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.exception.NotPersistedException;

public class TripFind {
	TripDao dao;
	public Trip findById(Long tripId) throws NotPersistedException {
		dao = Factories.persistence.newTripDao();
		Trip viaje = dao.findById(tripId);
		return viaje;
	}

	public Trip findByPromoterAndArrival(Long userId, Date arrival) throws NotPersistedException {
		dao = Factories.persistence.newTripDao();
		Trip viaje = dao.findByPromoterIdAndArrivalDate(userId,arrival);
		if(viaje==null)
			throw new NotPersistedException("El viaje no existe");
		return viaje;
	}

}
