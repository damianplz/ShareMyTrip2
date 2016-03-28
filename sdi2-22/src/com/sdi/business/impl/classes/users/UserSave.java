package com.sdi.business.impl.classes.users;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.model.UserStatus;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class UserSave {

	public void save(User user) throws AlreadyPersistedException {
		UserDao dao = Factories.persistence.newUserDao();
		user.setStatus(UserStatus.ACTIVE);
		User a = dao.findByLogin(user.getLogin());
		if (a != null)
			throw new AlreadyPersistedException("El login ya existe");
		dao.save(user);
	}
}
