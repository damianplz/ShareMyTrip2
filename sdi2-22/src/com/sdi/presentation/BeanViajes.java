package com.sdi.presentation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.persistence.exception.NotPersistedException;

@ManagedBean(name = "viajes")
@SessionScoped
public class BeanViajes {

	private List<Trip> viajes;

	@ManagedProperty(value = "#{viaje}")
	private BeanViaje viaje;

	public Trip getViaje() {
		return viaje;
	}

	public void setViaje(BeanViaje viaje) {
		this.viaje = viaje;
	}

	@PostConstruct
	public void init() throws ParseException {
		System.out.println("BeanViaje - PostConstruct");
		// si no existe lo creamos e inicializamos
		if (viaje == null) {
			System.out.println("BeanViaje - No existia");
			viaje = new BeanViaje();
		}
	}

	public String listado() {
		String resultado = "exito";
		List<Trip> viajesValidos;
		try {
			User u = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("LOGGEDIN_USER");
			viajes = Factories.services.createTripsService().getTrips();
			viajesValidos = new ArrayList<Trip>();
			if (u != null) {
				for (Trip t : viajes)
					if (t.getPromoterId().equals(u.getId()))
						t.setPropio(true);
			}
			for (Trip tr : viajes)
				if (tr.getClosingDate().after(Calendar.getInstance().getTime()))
					if (tr.getAvailablePax() > 0)
						viajesValidos.add(tr);

			viajes = viajesValidos;
		} catch (NotPersistedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultado = "fracaso";
		}
		return resultado;
	}

	public String misViajes() {
		String resultado = "exito";
		List<Trip> aux = new ArrayList<Trip>();
		try {
			viajes = Factories.services.createTripsService().getTrips();
			aux = new ArrayList<Trip>();
			User u = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("LOGGEDIN_USER");
			// Como promotor
			for (Trip t : viajes)
				if (t.getPromoterId().equals(u.getId())) {
					aux.add(t);
					t.setPropio(true);
				}
			// Como solicitante
			for (Trip t : viajes)
				if (Factories.services.createApplicationsService().findById(
						u.getId(), t.getId()) != null)
					aux.add(t);
		} catch (NotPersistedException e) {
			System.out.println("El usuario no es solicitante de ningún viaje");
		} finally {
			setViajes(aux);
		}
		return resultado;

	}

	public List<Trip> getViajes() {
		return viajes;
	}

	public void setViajes(List<Trip> viajes) {
		this.viajes = viajes;
	}

}
