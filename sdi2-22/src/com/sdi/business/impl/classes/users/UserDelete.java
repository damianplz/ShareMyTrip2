package com.sdi.business.impl.classes.users;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class UserDelete {

	public void delete(User user) throws NotPersistedException {
		UserDao dao = Factories.persistence.newUserDao();
		try{
			dao.delete(user.getId());
		}catch(PersistenceException e){
			throw new NotPersistedException("El usuario no se ha encontrado");
		}
	}

}
