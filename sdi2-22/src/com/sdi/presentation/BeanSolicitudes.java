package com.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import alb.util.log.Log;

import com.sdi.business.ApplicationService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@ManagedBean(name = "solicitudes")
@SessionScoped
public class BeanSolicitudes implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Long userId;
	private Long tripId;
	private String state;

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
		TripService ts;
		try {
			ts = Factories.services.createTripsService();
			viaje = ts.findById(viaje.getId());
			if (viaje.getAvailablePax() > 0) {
				Seat plaza = Factories.services.createSeatsService()
						.findByUserAndSeat(getUserId(), viaje.getId());
				if (plaza == null) {
					if (!getUserId().equals(viaje.getPromoterId())) {
						if (viaje.getClosingDate().after(new Date())) {
							viaje.setAvailablePax(viaje.getAvailablePax() - 1);
							as = Factories.services.createApplicationsService();
							Application app = new Application(getUserId(),
									viaje.getId());
							as.saveApplication(app);
							System.out.println("Se ha creado una solicitud");
							Log.debug(
									"Se ha creado una solicitud para el usuario [%d], viaje [%d]",
									getUserId(), viaje.getId());
						} else {
							resultado = "error";
							Log.debug("Error, solicitud fuera de plazo");
							FacesContext
									.getCurrentInstance()
									.addMessage(
											"formTodos",
											new FacesMessage(
													"No se aceptan solicitudes fuera de plazo"));
						}
					} else {
						resultado = "error";
						Log.debug("Error, solicitud viaje siendo promotor");
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"formTodos",
										new FacesMessage(
												"No puede solicitar plaza en un viaje propio"));
					}
				} else {
					resultado = "error";
					Log.debug("Error, ya tiene plaza en el viaje");
					FacesContext.getCurrentInstance().addMessage(
							"formTodos",
							new FacesMessage(
									"Ya ha solicitado plaza en éste viaje"));
				}

			} else {
				resultado = "error";
				Log.debug("Error, no quedan plazas en el viaje");
				FacesContext.getCurrentInstance().addMessage(
						"formTodos",
						new FacesMessage(
								"No quedan plazas libres en éste viaje"));
			}
		} catch (AlreadyPersistedException e) {
			resultado = "error";
			Log.error("Error, ya tiene plaza en el viaje");
			FacesContext.getCurrentInstance().addMessage("formTodos",
					new FacesMessage("Ya ha solicitado plaza en éste viaje"));
		} catch (NotPersistedException er) {
			Log.error("Error al solicitar plaza");
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
			Log.error("Error al cargar solicitudes para el viaje [%d]",viaje.getId());
			FacesMessage message = new FacesMessage(e.getMessage());
			throw new ValidatorException(message);
		}
		Log.info("Solicitudes cargadas para el viaje [%d]",viaje.getId());
		return resultado;
	}

	public String confirmar(Application app) {
		String resultado = "exito";
		ServicesFactory factoria;
		User usuario;
		Trip viaje;

		try {
			factoria = Factories.services;

			usuario = factoria.createUsersService().finById(app.getUserId());
			viaje = factoria.createTripsService().findById(app.getTripId());
			app = Factories.services.createApplicationsService().findById(
					usuario.getId(), viaje.getId());
			if (app != null) {
				if (viaje.getAvailablePax() > 0) {
					if (viaje.getClosingDate().after(new Date())) {
						if (factoria.createSeatsService().findByUserAndSeat(
								usuario.getId(), viaje.getId()) == null) {
							viaje.setAvailablePax(viaje.getAvailablePax() - 1);
							factoria.createTripsService().updateTrip(viaje);
							factoria.createSeatsService().saveSeat(
									new Seat(usuario.getId(), viaje.getId(),
											"", SeatStatus.ACCEPTED));
							factoria.createApplicationsService()
									.deleteApplication(app);
							Log.info("Solicitud para el viaje [%d] y usuario [%s] confirmada",viaje.getId(),usuario.getLogin());
						} else {
							Log.debug("Error, el usuario ya tiene plaza");
							resultado = "fracaso";
							FacesContext
									.getCurrentInstance()
									.addMessage(
											"formSolicitudes",
											new FacesMessage(
													"El usuario ya tiene plaza en éste viaje"));
						}

					} else {
						resultado = "fracaso";
						Log.debug("Error, el plazo para aceptar plazas ha sido cerrado");
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"formSolicitudes",
										new FacesMessage(
												"El plazo para aceptar solicitudes ya se ha cerrado"));
					}
				} else {
					resultado = "fracaso";
					Log.debug("Error, no hay plazas libres");
					FacesContext.getCurrentInstance().addMessage(
							"formSolicitudes",
							new FacesMessage(
									"No quedan plazas libres en éste viaje"));
				}
			} else {
				resultado = "fracaso";
				Log.debug("Error, no se puede confirmar la solicitud");
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"formSolicitudes",
								new FacesMessage(
										"Ha ocurrido algún error con la solicitud, no se puede confirmar"));
			}
		} catch (Exception e) {
			resultado = "fracaso";
			Log.debug("Error al confirmar la solicitud");
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"formSolicitudes",
							new FacesMessage(
									"Error al confirmar solicitud, contacte con su administrador"));
		}
		return resultado;
	}

	public String cancelar(Application app) {
		String resultado = "exito";
		ServicesFactory factoria;
		User usuario;
		Trip viaje;
		Seat plaza;
		try {
			factoria = Factories.services;
			usuario = factoria.createUsersService().finById(app.getUserId());
			viaje = factoria.createTripsService().findById(app.getTripId());
			app = factoria.createApplicationsService().findById(
					usuario.getId(), viaje.getId());
			if (app != null) {
				if (viaje.getClosingDate().after(new Date())) {
					plaza = factoria.createSeatsService().findByUserAndSeat(
							usuario.getId(), viaje.getId());
					if (plaza != null) {
						viaje.setAvailablePax(viaje.getAvailablePax() + 1);
						plaza.setStatus(SeatStatus.EXCLUDED);
						factoria.createSeatsService().updateSeat(plaza);
						Log.info("Se ha creado una nueva plaza con estado [%s]",plaza.getStatus().name());
					} else {
						factoria.createSeatsService().saveSeat(
								new Seat(usuario.getId(), viaje.getId(), "",
										SeatStatus.EXCLUDED));
						Log.info("Se ha actualizado la plaza al estado [%s]",SeatStatus.EXCLUDED.name());
					}
					factoria.createTripsService().updateTrip(viaje);
					factoria.createApplicationsService().deleteApplication(app);
					Log.info("Se ha eliminado la solicitud");
				} else {
					resultado = "fracaso";
					Log.debug("La fecha para cancelar solicitudes ha finalizado");
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"formSolicitudes",
									new FacesMessage(
											"La fecha para cancelar solicitudes ha sido cerrada"));
				}

			} else {
				resultado = "fracaso";
				Log.debug("La solicitud ya ha sido tratada");
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"formSolicitudes",
								new FacesMessage(
										"La solicitud ya ha sido tratada anteriormente"));
			}

		} catch (Exception e) {
			resultado = "fracaso";
			Log.debug("Error al denegar la solicitud");
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"formSolicitudes",
							new FacesMessage(
									"Error al denegar solicitud, contacte con su administrador"));
		}
		return resultado;
	}

	public String state(Application app) {
		Seat seat;
		try {
			seat = Factories.services.createSeatsService().findByUserAndSeat(
					app.getUserId(), app.getTripId());
			if (seat == null)
				state = "PENDIENTE";
			else if (Factories.services.createTripsService()
					.findById(app.getTripId()).getClosingDate()
					.before(new Date())
					|| Factories.services.createTripsService()
							.findById(app.getTripId()).getAvailablePax() == 0)
				state = "SIN PLAZA";
			else
				state = seat.getStatus().name();
			Log.info("La solicitud pasa al estadlo [%s]",state);
		} catch (NotPersistedException e) {
			Log.error("La solicitud no se ha podido encontrar");
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"formSolicitudes",
							new FacesMessage(
									"Error al buscar la solicitud, consulte con el administrador"));
		}
		return state;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public boolean existe(Application app) {
		boolean existe = true;
		try {
			if (Factories.services.createApplicationsService().findById(
					app.getUserId(), app.getTripId()) == null)
				existe = false;
		} catch (NotPersistedException e) {
			Log.error("La solicitud no existe");
			existe = false;

		}

		return !existe;
	}

	/*
	 * public String cancelarSolicitud(Trip viaje) { String resultado = "exito";
	 * ServicesFactory factoria; User usuario; Application app; Seat seat; try {
	 * factoria = Factories.services; usuario = (User)
	 * FacesContext.getCurrentInstance()
	 * .getExternalContext().getSessionMap().get("LOGGEDIN_USER"); usuario =
	 * factoria.createUsersService().finById(usuario.getId()); viaje =
	 * factoria.createTripsService().findById(viaje.getId()); seat =
	 * factoria.createSeatsService().findByUserAndSeat( usuario.getId(),
	 * viaje.getId()); if (viaje.getClosingDate().after(new Date())) { app =
	 * Factories.services.createApplicationsService().findById( usuario.getId(),
	 * viaje.getId()); if (app != null) {
	 * factoria.createApplicationsService().deleteApplication(app); if (seat !=
	 * null) { seat.setStatus(SeatStatus.EXCLUDED);
	 * factoria.createSeatsService().updateSeat(seat);
	 * viaje.setAvailablePax(viaje.getAvailablePax() + 1);
	 * factoria.createTripsService().updateTrip(viaje); } else { seat = new
	 * Seat(usuario.getId(), viaje.getId(), "", SeatStatus.EXCLUDED);
	 * factoria.createSeatsService().saveSeat(seat); } }
	 * 
	 * } else { resultado = "FRACASO"; FacesContext .getCurrentInstance()
	 * .addMessage( "botonCancelarSolicitud", new FacesMessage(
	 * "El plazo para cancelar solicitudes de éste viaje esta cerrado")); }
	 * 
	 * } catch (NotPersistedException e) { resultado = "FRACASO"; FacesContext
	 * .getCurrentInstance() .addMessage( "botonCancelarSolicitud", new
	 * FacesMessage(
	 * "Error al cancelar la solicitud, consulte con el administrador")); }
	 * catch (AlreadyPersistedException e) { resultado = "FRACASO"; FacesContext
	 * .getCurrentInstance() .addMessage( "botonCancelarSolicitud", new
	 * FacesMessage( "La plaza ya existe")); }
	 * 
	 * return resultado; }
	 */
}
