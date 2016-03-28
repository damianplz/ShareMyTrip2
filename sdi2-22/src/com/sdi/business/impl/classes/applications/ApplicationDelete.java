package com.sdi.business.impl.classes.applications;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class ApplicationDelete {

	public void delete(Application app) throws NotPersistedException {
		ApplicationDao dao = Factories.persistence.newApplicationDao();
		try{
			dao.delete(new Long[]{app.getUserId(),app.getTripId()});
		}catch(PersistenceException e){
			throw new NotPersistedException("La solicitud no existe");
		}
		
	}

}
