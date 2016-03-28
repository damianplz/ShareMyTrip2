package com.sdi.business;

import java.util.List;

import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface UserService {
	
	void saveUser(User user) throws AlreadyPersistedException;
	void deleteUser(User user) throws NotPersistedException;
	User findByLogin(String login) throws NotPersistedException;
	void updateUser(User user) throws NotPersistedException;
	List<User> getUsers() throws NotPersistedException;
	User finById(Long userId) throws NotPersistedException;

}
