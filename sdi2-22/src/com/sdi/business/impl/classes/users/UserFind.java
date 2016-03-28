package com.sdi.business.impl.classes.users;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.NotPersistedException;

public class UserFind {

	public User findByLogin(String login) throws NotPersistedException {
		UserDao dao = Factories.persistence.newUserDao();
		User usuario = dao.findByLogin(login);
		if(usuario==null)
			throw new NotPersistedException("El usuario no se ha encontrado");
		return usuario;
	}

	public User findById(Long userId) throws NotPersistedException {
		UserDao dao = Factories.persistence.newUserDao();
		User usuario = dao.findById(userId);
		if(usuario==null)
			throw new NotPersistedException("El usuario no se ha encontrado");
		return usuario;
	}

}
