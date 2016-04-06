package com.sdi.presentation;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.business.ServicesFactory;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
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

	private Long auxId;

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
					if (tr.getAvailablePax() > 0
							&& tr.getStatus().equals(TripStatus.OPEN))
						viajesValidos.add(tr);

			viajes = viajesValidos;
			Log.debug("Obtenida lista de viajes conteniendo [%d] viajes", viajes.size());
		} catch (NotPersistedException e) {
			//e.printStackTrace();
			Log.error("Algo ha ocurrido obteniendo lista de viajes");
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
						u.getId(), t.getId()) != null
						|| Factories.services.createSeatsService()
								.findByUserAndSeat(u.getId(), t.getId()) != null
						&& Factories.services.createSeatsService()
								.findByUserAndSeat(u.getId(), t.getId())
								.getStatus().equals(SeatStatus.ACCEPTED))
					aux.add(t);

		} catch (NotPersistedException e) {
			Log.error("Algo ha ocurrido obteniendo los viajes en los que el usuario está involucrado");
		} finally {
			setViajes(aux);
			Log.debug("Obtenida lista de viajes del usuario conteniendo [%d] viajes", aux.size());
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
					if (trip.getAvailablePax() > 0
							&& trip.getStatus().equals(TripStatus.OPEN)) {
						if (contador < filas) {
							viajesDisponibles.add(trip);
							contador++;
						}

					}
				}
			}
			filasAux = filas;
		}
		Log.debug("Obtenida lista de viajes disponibles conteniendo [%d] viajes", viajesDisponibles.size());

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
			for (Trip t : getViajes()) {
				if (!espropio(t)) {
					t.setStatus(TripStatus.CANCELLED);
					factoria.createTripsService().updateTrip(t);
					seats = factoria.createSeatsService().findByTrip(t.getId());
					if (seats != null)
						for (Seat s : seats) {
							s.setStatus(SeatStatus.EXCLUDED);
							factoria.createSeatsService().updateSeat(s);
						}
				}
			}
		} catch (NotPersistedException e) {
			Log.error("Ha ocurrido un error al cancelar viajes");
			resultado = "fracaso";
		}
		Log.debug("Viajes cancelados con éxito");
		misViajes();
		return resultado;
	}

	public String cargar(Trip viaje) {
		String resultado = "editar";
		establecerViaje(viaje);
		establecerId(viaje);
		return resultado;
	}

	private void establecerViaje(Trip viaje) {
		this.viaje.setDeparture(viaje.getDeparture());
		this.viaje.setDestination(viaje.getDestination());
		this.viaje.setArrivalDate(viaje.getArrivalDate());
		this.viaje.setAvailablePax(viaje.getAvailablePax());
		this.viaje.setClosingDate(viaje.getClosingDate());
		this.viaje.setComments(viaje.getComments());
		this.viaje.setDepartureDate(viaje.getDepartureDate());
		this.viaje.setEstimatedCost(viaje.getEstimatedCost());
		this.viaje.setMaxPax(viaje.getMaxPax());
		this.viaje.setPromoterId(viaje.getPromoterId());
		this.viaje.setStatus(viaje.getStatus());
	}

	private void establecerId(Trip viaje) {
		this.auxId = viaje.getId();
	}

	public String actualizar(Trip viaje) {
		establecerViaje(viaje);
		String resultado = "exito";
		try {
			viaje.setId(auxId);
			Factories.services.createTripsService().updateTrip(viaje);
		} catch (NotPersistedException e) {
			Log.error("Error al actualizar el viaje con id[%d]",viaje.getId());
			resultado = "fracaso";
		}
		Log.debug("Se ha actualizado el viaje [%d] correctamente",viaje.getId());
		return resultado;
	}

	public String cancelarSolicitud(Trip viaje) {
		String resultado = "exito";
		ServicesFactory factoria;
		User usuario;
		Application app;
		Seat seat;
		try {
			factoria = Factories.services;
			usuario = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("LOGGEDIN_USER");
			usuario = factoria.createUsersService().finById(usuario.getId());
			viaje = factoria.createTripsService().findById(viaje.getId());
			seat = factoria.createSeatsService().findByUserAndSeat(
					usuario.getId(), viaje.getId());
			if (viaje.getClosingDate().after(new Date())) {
				app = Factories.services.createApplicationsService().findById(
						usuario.getId(), viaje.getId());
				if (app != null) {
					factoria.createApplicationsService().deleteApplication(app);
					if (seat != null) {
						seat.setStatus(SeatStatus.EXCLUDED);
						factoria.createSeatsService().updateSeat(seat);
						viaje.setAvailablePax(viaje.getAvailablePax() + 1);
						factoria.createTripsService().updateTrip(viaje);
					} else {
						seat = new Seat(usuario.getId(), viaje.getId(), "",
								SeatStatus.EXCLUDED);
						factoria.createSeatsService().saveSeat(seat);
					}
				}

			} else {
				resultado = "FRACASO";
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"botonCancelarSolicitud",
								new FacesMessage(
										"El plazo para cancelar solicitudes de éste viaje esta cerrado"));
				Log.debug("El plazo para cancelar solicitudes en el viaje [%d] está cerrado",viaje.getId());
			}

		} catch (NotPersistedException e) {
			resultado = "FRACASO";
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"botonCancelarSolicitud",
							new FacesMessage(
									"Error al cancelar la solicitud, consulte con el administrador"));
			Log.error("Error al cancelar una solicitud en el viaje [%d]",viaje.getId());
		} catch (AlreadyPersistedException e) {
			resultado = "FRACASO";
			FacesContext.getCurrentInstance().addMessage(
					"botonCancelarSolicitud",
					new FacesMessage("La plaza ya existe"));
			Log.error("Error al cancelar una solicitud en el viaje [%d], la plaza ya existe",viaje.getId());
		}
		misViajes();
		return resultado;
	}

}
