package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.ApplicationService;
import com.sdi.business.impl.classes.applications.ApplicationDelete;
import com.sdi.business.impl.classes.applications.ApplicationFind;
import com.sdi.business.impl.classes.applications.ApplicationList;
import com.sdi.business.impl.classes.applications.ApplicationSave;
import com.sdi.business.impl.classes.applications.ApplicationUpdate;
import com.sdi.model.Application;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public class SimpleApplicationService implements ApplicationService {

	@Override
	public void saveApplication(Application app)
			throws AlreadyPersistedException {
		new ApplicationSave().save(app);
		
	}

	@Override
	public void deleteApplication(Application app) throws NotPersistedException {
		new ApplicationDelete().delete(app);
		
	}

	@Override
	public Application findById(Long userId, Long tripId)
			throws NotPersistedException {
		return new ApplicationFind().findById(userId,tripId);
	}

	@Override
	public void updateApplication(Application app) throws NotPersistedException {
		new ApplicationUpdate().update(app);		
	}

	@Override
	public List<Application> getApplications() throws NotPersistedException {
		return new ApplicationList().getApplications();
	}

	@Override
	public List<Application> findByUser(Long userId) throws NotPersistedException {
		return new ApplicationFind().findByUser(userId);
	}

	@Override
	public List<Application> findByTrip(Long tripId) throws NotPersistedException {
		return new ApplicationFind().findByTrip(tripId);
	}

}
