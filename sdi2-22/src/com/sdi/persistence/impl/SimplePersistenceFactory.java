package com.sdi.persistence.impl;

import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.PersistenceFactory;
import com.sdi.persistence.RatingDao;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.Transaction;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.UserDao;

/**
 * Implementaci??????n de la factoria que devuelve implementaci??????n de la capa
 * de persistencia con Jdbc 
 * 
 * @author alb
 *
 */
public class SimplePersistenceFactory implements PersistenceFactory {

	@Override
	public Transaction newTransaction() {
		return new TransactionJdbcImpl();
	}

	@Override
	public RatingDao newRatingDao() {
		return new RatingDaoJdbcImpl();
	}

	@Override
	public UserDao newUserDao() {
		return new UserDaoJdbcImpl();
	}

	@Override
	public TripDao newTripDao() {
		return new TripDaoJdbcImpl();
	}

	@Override
	public SeatDao newSeatDao() {
		return new SeatDaoJdbcImpl();
	}

	@Override
	public ApplicationDao newApplicationDao() {
		return new ApplicationDaoJdbcImpl();
	}

}
