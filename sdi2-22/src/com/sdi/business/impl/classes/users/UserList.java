package com.sdi.business.impl.classes.users;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.NotPersistedException;

public class UserList {

	public List<User> getUsers() throws NotPersistedException {
		UserDao dao = Factories.persistence.newUserDao();
		List<User> usuarios = dao.findAll();
		if(usuarios == null)
			throw new NotPersistedException("No hay usuarios");
		return usuarios;
	}

}
