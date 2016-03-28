package com.sdi.business.impl;

import com.sdi.business.ApplicationService;
import com.sdi.business.LoginService;
import com.sdi.business.SeatService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.business.UserService;

public class SimpleServicesFactory implements ServicesFactory {
	
	@Override
	public LoginService createLoginService(){
		return new SimpleLoginService();
	}

	@Override
	public SeatService createSeatsService() {
		return new SimpleSeatService();
	}

	@Override
	public ApplicationService createApplicationsService() {
		return new SimpleApplicationService();
	}

	@Override
	public UserService createUsersService() {
		return new SimpleUserService();
	}

	@Override
	public TripService createTripsService() {
		return new SimpleTripService();
	}

}
