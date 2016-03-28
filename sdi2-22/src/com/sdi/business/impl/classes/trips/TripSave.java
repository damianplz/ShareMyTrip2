package com.sdi.business.impl.classes.trips;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class TripSave {

	public void save(Trip trip) throws AlreadyPersistedException {
		TripDao dao = Factories.persistence.newTripDao();
		try{
			dao.save(trip);
		}catch(PersistenceException e){
			throw new AlreadyPersistedException("El viaje ya existe",e);
		}
		
	}

}
