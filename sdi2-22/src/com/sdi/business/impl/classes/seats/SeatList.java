package com.sdi.business.impl.classes.seats;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.exception.NotPersistedException;

public class SeatList {

	public List<Seat> getSeats() throws NotPersistedException {
		SeatDao dao = Factories.persistence.newSeatDao();
		List<Seat> plazas = dao.findAll();
		if(plazas==null)
			throw new NotPersistedException("No se han encontrado plazas");
		return plazas;
	}

}
