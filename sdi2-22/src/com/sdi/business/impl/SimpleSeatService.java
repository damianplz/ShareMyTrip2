package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.SeatService;
import com.sdi.business.impl.classes.seats.SeatDelete;
import com.sdi.business.impl.classes.seats.SeatFind;
import com.sdi.business.impl.classes.seats.SeatList;
import com.sdi.business.impl.classes.seats.SeatSave;
import com.sdi.business.impl.classes.seats.SeatUpdate;
import com.sdi.model.Seat;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public class SimpleSeatService implements SeatService {
	
	//crear plaza
	@Override
	public void saveSeat(Seat seat) throws AlreadyPersistedException{
		new SeatSave().save(seat);
	}
	//eliminar plaza
	@Override
	public void deleteSeat(Seat seat) throws NotPersistedException{
		new SeatDelete().delete(seat);
	}
	//buscar plaza
	@Override
	public Seat findByUserAndSeat(Long userId, Long tripId) throws NotPersistedException{
		return new SeatFind().findById(userId,tripId);
	}
	//actualizar plaza
	@Override
	public void updateSeat(Seat seat) throws NotPersistedException{
		new SeatUpdate().update(seat);
	}
	//listar plazas
	@Override
	public List<Seat> getSeats() throws NotPersistedException{
		return new SeatList().getSeats();
	}

}
