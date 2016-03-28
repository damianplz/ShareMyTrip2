package com.sdi.business.impl.classes.seats;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class SeatSave {

	public void save(Seat seat) throws AlreadyPersistedException {
		SeatDao dao = Factories.persistence.newSeatDao();
		try{
			dao.save(seat);
		}catch(PersistenceException e){
			throw new AlreadyPersistedException("La plaza ya existe", e);
		}
		
	}

}
