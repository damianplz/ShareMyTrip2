package com.sdi.presentation;

import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.LoginService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

@ManagedBean(name = "login")
@SessionScoped
public class BeanLogin extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String result = "login_form_result_valid";
	private boolean validado = false;
	

	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public BeanLogin() {
		System.out.println("BeanLogin		-		No		existia");
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		if (session.get("LOGGEDIN_USER")!=null) {
			setValidado(true);
		}
	}

	public String verify() {
		LoginService log = Factories.services.createLoginService();
		User user = log.verify(getLogin(), getPassword());
		String resultado = "exito";
		if (user != null) {
			putUserInSession(user);

		} else {
			setResult("login_form_result_error");
			resultado = "fracaso";
			setValidado(false);
		}
		return resultado;
	}

	public String delete() {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		session.remove("LOGGEDIN_USER");
		setValidado(false);
		return "cerrarSesion";
	}

	private void putUserInSession(User user) {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		session.put("LOGGEDIN_USER", user);
		setValidado(true);
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
