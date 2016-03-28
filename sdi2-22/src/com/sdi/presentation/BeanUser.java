package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.model.User;

@ManagedBean(name = "user")
@SessionScoped
public class BeanUser extends BeanLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	public BeanUser() {
		iniciaUser(null);
	}

	public void iniciaUser(Object object) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		setLogin(bundle.getString("valorDefectoLogin"));
		setName(bundle.getString("valorDefectoNombre"));
		setSurname(bundle.getString("valorDefectoApellidos"));
		setEmail(bundle.getString("valorDefectoCorreo"));

	}

}
