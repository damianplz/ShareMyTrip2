package com.sdi.business.impl.classes.applications;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.exception.NotPersistedException;

public class ApplicationFind {

	ApplicationDao dao;
	
	public Application findById(Long userId, Long tripId) throws NotPersistedException {
		dao = Factories.persistence.newApplicationDao();
		Application solicitud = dao.findById(new Long[]{userId,tripId});
		/*if(solicitud == null)
			throw new NotPersistedException("No se ha encontrado la solicitud");*/
		return solicitud;
	}

	public List<Application> findByUser(Long userId) throws NotPersistedException {
		dao = Factories.persistence.newApplicationDao();
		List<Application> solicitud = dao.findByUserId(userId);
		if(solicitud == null)
			throw new NotPersistedException("No se ha encontrado la solicitud");
		return solicitud;
	}

	public List<Application> findByTrip(Long tripId) throws NotPersistedException {
		dao = Factories.persistence.newApplicationDao();
		List<Application> solicitud = dao.findByTripId(tripId);
		if(solicitud == null)
			throw new NotPersistedException("No se ha encontrado la solicitud");
		return solicitud;
	}

}
