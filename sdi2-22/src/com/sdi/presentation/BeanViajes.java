package com.sdi.presentation;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.ServicesFactory;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.persistence.exception.NotPersistedException;

import org.primefaces.event.*;

@ManagedBean(name = "viajes")
@SessionScoped
public class BeanViajes implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Trip> viajes;
	private List<Trip> viajesDisponibles;
	private int filas = 5;
	private int filasAux = filas;

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

	public List<Trip> getViajesDisponibles() {

		if (viajesDisponibles == null || filas != filasAux) {
			viajesDisponibles = new ArrayList<Trip>();
			int contador = 0;

			for (Trip trip : viajes) {
				if (trip.getClosingDate().after(
						Calendar.getInstance().getTime())) {
					if (trip.getAvailablePax() > 0) {
						if (contador < filas) {
							viajesDisponibles.add(trip);
							contador++;
						}

					}
				}
			}
			filasAux = filas;
		}

		return viajesDisponibles;
	}

	public void update() {
		viajesDisponibles = getViajesDisponibles();
	}

	public List<Trip> getViajes() {
		return viajes;
	}

	public void setViajes(List<Trip> viajes) {
		this.viajes = viajes;
	}

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public boolean espropio(Trip viaje) {
		boolean propio = true;
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("LOGGEDIN_USER");
		if (user != null)
			propio = !viaje.getPromoterId().equals(user.getId());
		return propio;
	}

	public void onRowSelect(SelectEvent event) {

		
		FacesMessage msg = new FacesMessage("Trip selected",
				String.valueOf(((Trip) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		
		viajes.remove((Trip) event.getObject());
		
		FacesMessage msg = new FacesMessage("Trip Unselected",
				String.valueOf(((Trip) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String cancelarViajes() {
		String resultado = "exito";
		ServicesFactory factoria;
		List<Seat> seats;
		try {
			factoria = Factories.services;
			for(Trip t:getViajes()){
				seats = factoria.createSeatsService().findByTrip(t.getId());
				if (seats != null)
					for (Seat s : seats) {
						s.setStatus(SeatStatus.EXCLUDED);
						factoria.createSeatsService().updateSeat(s);
					}
				if(!espropio(t))
					factoria.createTripsService().deleteTrip(t);
			}
		} catch (NotPersistedException e) {
			resultado="fracaso";
			e.printStackTrace();
		}
		misViajes();
		return resultado;
	}



}
