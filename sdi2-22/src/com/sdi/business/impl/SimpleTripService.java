package com.sdi.business.impl;

import java.util.Date;
import java.util.List;

import com.sdi.business.TripService;
import com.sdi.business.impl.classes.trips.TripDelete;
import com.sdi.business.impl.classes.trips.TripFind;
import com.sdi.business.impl.classes.trips.TripList;
import com.sdi.business.impl.classes.trips.TripSave;
import com.sdi.business.impl.classes.trips.TripUpdate;
import com.sdi.model.Trip;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public class SimpleTripService implements TripService {

	@Override
	public void saveTrip(Trip trip) throws AlreadyPersistedException {
		new TripSave().save(trip);
		
	}

	@Override
	public void deleteTrip(Trip trip) throws NotPersistedException {
		new TripDelete().delete(trip);
		
	}

	@Override
	public Trip findById(Long tripId) throws NotPersistedException {
		return new TripFind().findById(tripId);
	}

	@Override
	public void updateTrip(Trip trip) throws NotPersistedException {
		new TripUpdate().update(trip);
		
	}

	@Override
	public List<Trip> getTrips() throws NotPersistedException {
		return new TripList().getTrips();
	}

	@Override
	public Trip findByPromoterAndArrival(Long userId, Date arrival)
			throws NotPersistedException {
		return new TripFind().findByPromoterAndArrival(userId,arrival);
	}


}
