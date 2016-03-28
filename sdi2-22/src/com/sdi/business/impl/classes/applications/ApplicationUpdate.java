package com.sdi.business.impl.classes.applications;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class ApplicationUpdate {

	public void update(Application app) throws NotPersistedException {
		ApplicationDao dao = Factories.persistence.newApplicationDao();
		try{
			dao.update(app);
		}catch(PersistenceException e){
			throw new NotPersistedException("La solicitud no se ha encontrado");
		}
		
	}

}
