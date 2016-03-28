package com.sdi.business.impl.classes.applications;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.exception.NotPersistedException;

public class ApplicationList {

	public List<Application> getApplications() throws NotPersistedException {
		ApplicationDao dao = Factories.persistence.newApplicationDao();
		List<Application> solicitudes = dao.findAll();
		if(solicitudes == null)
			throw new NotPersistedException("No hay solicitudes");
		return solicitudes;
	}

}
