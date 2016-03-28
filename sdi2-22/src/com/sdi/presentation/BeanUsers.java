package com.sdi.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.xml.bind.ValidationException;

import com.sdi.business.UserService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@ManagedBean(name = "controller")
@SessionScoped
public class BeanUsers implements Serializable {

	private static final long serialVersionUID = 1L;

	private User perfil;
	
	@ManagedProperty(value = "#{user}")
	private BeanLogin user;

	public BeanLogin getUser() {
		return user;
	}

	public void setUser(BeanLogin user) {
		this.user = user;
	}

	@PostConstruct
	public void init() {
		System.out.println("BeanUsers - PostConstruct");
		// Buscamos el alumno en la sesión. Esto es un patrón factoría
		// claramente.
		user =  (BeanLogin) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("user"));
		// si no existe lo creamos e inicializamos
		if (user == null) {
			System.out.println("BeanUsers - No existia");
			user = new BeanLogin();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("user", user);
		}
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanUsers - PreDestroy");
	}

	public String registro() {
		UserService userService;
		String resultado = "exito";
		try {
			userService = Factories.services.createUsersService();
			userService.saveUser(user);
			user.verify();
		} catch (AlreadyPersistedException e) {
			resultado = "error";
			FacesContext.getCurrentInstance().addMessage("registroForm",new FacesMessage(e.getMessage()));	
		}
		return resultado;
	}

	public User getPerfil() {
		return perfil;
	}

	public void setPerfil(User perfil) {
		this.perfil = perfil;
	}
	
	public String cargar(Application app) throws ValidationException{
		String resultado="exito";
		UserService us;
		try {
			us = Factories.services.createUsersService();
			setPerfil(us.finById(app.getUserId()));
		} catch (NotPersistedException e) {
			resultado="fracaso";
			throw new ValidatorException(new FacesMessage(e.getMessage()));
		}
		return resultado;
	}

}
