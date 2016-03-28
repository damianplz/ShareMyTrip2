package com.sdi.business.impl.classes.users;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.model.UserStatus;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class UserSave {

	public void save(User user) throws AlreadyPersistedException {
		UserDao dao = Factories.persistence.newUserDao();
		try{
			user.setStatus(UserStatus.ACTIVE);
			dao.save(user);
		}catch(PersistenceException e){
			throw new AlreadyPersistedException("El usuario ya existe", e);
		}
		
	}

}
