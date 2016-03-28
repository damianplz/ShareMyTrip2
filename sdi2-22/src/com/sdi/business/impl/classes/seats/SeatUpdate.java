package com.sdi.business.impl.classes.seats;


import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class SeatUpdate {

	public void update(Seat seat) throws NotPersistedException {
		SeatDao dao = Factories.persistence.newSeatDao();
		try{
			dao.update(seat);
		}catch(PersistenceException e){
			throw new NotPersistedException("La plaza no se ha encontrado, no se puede actualizar");
		}
		
	}

}
