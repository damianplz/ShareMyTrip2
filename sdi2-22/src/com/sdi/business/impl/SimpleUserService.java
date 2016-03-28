package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.UserService;
import com.sdi.business.impl.classes.users.UserDelete;
import com.sdi.business.impl.classes.users.UserFind;
import com.sdi.business.impl.classes.users.UserList;
import com.sdi.business.impl.classes.users.UserSave;
import com.sdi.business.impl.classes.users.UserUpdate;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public class SimpleUserService implements UserService {

	@Override
	public void saveUser(User user) throws AlreadyPersistedException {
		new UserSave().save(user);
		
	}

	@Override
	public void deleteUser(User user) throws NotPersistedException {
		new UserDelete().delete(user);
	}

	@Override
	public User findByLogin(String login) throws NotPersistedException {
		return new UserFind().findByLogin(login);
	}

	@Override
	public void updateUser(User user) throws NotPersistedException {
		new UserUpdate().update(user);
		
	}

	@Override
	public List<User> getUsers() throws NotPersistedException {
		return new UserList().getUsers();
	}

	@Override
	public User finById(Long userId) throws NotPersistedException {
		return new UserFind().findById(userId);
	}
	
}
