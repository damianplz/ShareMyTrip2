package com.sdi.business.impl.classes.users;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class UserUpdate {

	public void update(User user) throws NotPersistedException {
		UserDao dao = Factories.persistence.newUserDao();
		try{
			dao.update(user);
		}catch(PersistenceException e){
			throw new NotPersistedException("El usuario no se ha encontrado");
		}
		
	}

}
