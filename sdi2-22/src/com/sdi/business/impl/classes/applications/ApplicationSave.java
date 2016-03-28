package com.sdi.business.impl.classes.applications;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class ApplicationSave {

	public void save(Application app) throws AlreadyPersistedException {
		ApplicationDao dao = Factories.persistence.newApplicationDao();
		try{
			dao.save(app);
		}catch(PersistenceException e){
			throw new AlreadyPersistedException("La solicitud ya existe",e);
		}
		
	}

}
