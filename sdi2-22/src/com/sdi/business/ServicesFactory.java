package com.sdi.business;

public interface ServicesFactory {
	
	LoginService createLoginService();
	
	SeatService createSeatsService();
	
	ApplicationService createApplicationsService();
	
	UserService createUsersService();
	
	TripService createTripsService();

}
