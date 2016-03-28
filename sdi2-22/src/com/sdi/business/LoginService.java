package com.sdi.business;

import com.sdi.model.User;

public interface LoginService {
	
	public User verify(String login, String password);
	public boolean validLogin(String login, String password);

}
