package com.sdi.business.impl.classes.trips;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.exception.NotPersistedException;

public class TripList {

	public List<Trip> getTrips() throws NotPersistedException {
		TripDao dao = Factories.persistence.newTripDao();
		List<Trip> viajes = dao.findAll();
		if(viajes == null)
			throw new NotPersistedException("No hay viajes");
		return viajes;
	}

}
