package com.sdi.business;

import java.util.List;

import com.sdi.model.Application;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface ApplicationService {

	void saveApplication(Application app) throws AlreadyPersistedException;
	void deleteApplication(Application app) throws NotPersistedException;
	Application findById(Long userId, Long tripId) throws NotPersistedException;
	List<Application> findByUser(Long userId) throws NotPersistedException;
	List<Application> findByTrip(Long tripId) throws NotPersistedException;
	void updateApplication(Application app) throws NotPersistedException;
	List<Application> getApplications() throws NotPersistedException;
}
