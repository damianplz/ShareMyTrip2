package com.sdi.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.sdi.business.ApplicationService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@ManagedBean(name = "solicitudes")
@SessionScoped
public class BeanSolicitudes implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private Long tripId;

	private List<Application> applications;

	public Long getUserId() {
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER");
		return u.getId();
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String solicitar(Trip viaje) {
		String resultado = "exito";
		ApplicationService as;
		try {
			as = Factories.services.createApplicationsService();
			Application app = new Application(getUserId(), viaje.getId());
			as.saveApplication(app);
			System.out.println("Se ha creado una solicitud");
		} catch (AlreadyPersistedException e) {
			e.printStackTrace();
			resultado = "fracaso";
		}
		return resultado;
	}

	public String cargar(Trip viaje) {
		String resultado = "exito";
		ApplicationService as;
		try {
			as = Factories.services.createApplicationsService();
			setApplications(as.findByTrip(viaje.getId()));
		} catch (NotPersistedException e) {
			resultado = "fracaso";
			FacesMessage message =new FacesMessage(e.getMessage());
			throw new ValidatorException(message );
		}
		return resultado;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

}
