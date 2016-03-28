package com.sdi.business.impl.classes.trips;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class TripUpdate {

	public void update(Trip trip) throws NotPersistedException {
		TripDao dao = Factories.persistence.newTripDao();
		try{
			dao.update(trip);
		}catch(PersistenceException e){
			throw new NotPersistedException("El viaje no existe");
		}
		
	}

}
