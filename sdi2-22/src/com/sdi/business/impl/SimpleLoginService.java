package com.sdi.business.impl;

import com.sdi.business.LoginService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.exception.NotPersistedException;

public class SimpleLoginService implements LoginService{
	User user;
	@Override
	public User verify(String login, String password) {
		if(!validLogin(login,password))
			return null;
		return user;
	}

	@Override
	public boolean validLogin(String login, String password) {
		//return "admin".equals(login) && "password".equals(password);
		boolean valido = false;	
			try {
				user = Factories.services.createUsersService().findByLogin(login);
				valido = (user.getLogin().equals(login) && user.getPassword().equals(password))?true:false;
			} catch (NotPersistedException e) {
				valido = false;
			}
			return valido;
	}

}
