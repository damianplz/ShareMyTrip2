package com.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

import com.sdi.business.ApplicationService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.persistence.PersistenceFactory;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@ManagedBean(name = "solicitudes")
@SessionScoped
public class BeanSolicitudes implements Serializable {

	private static final long serialVersionUID = 1L;

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
						} else {
							resultado = "error";
							FacesContext
									.getCurrentInstance()
									.addMessage(
											"formTodos",
											new FacesMessage(
													"No se aceptan solicitudes fuera de plazo"));
						}
					} else {
						resultado = "error";
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"formTodos",
										new FacesMessage(
												"No puede solicitar plaza en un viaje propio"));
					}
				} else {
					resultado = "error";
					FacesContext.getCurrentInstance().addMessage(
							"formTodos",
							new FacesMessage(
									"Ya ha solicitado plaza en éste viaje"));
				}

			} else {
				resultado = "error";
				FacesContext.getCurrentInstance().addMessage(
						"formTodos",
						new FacesMessage(
								"No quedan plazas libres en éste viaje"));
			}
		} catch (AlreadyPersistedException e) {
			resultado = "error";
			FacesContext.getCurrentInstance().addMessage("formTodos",
					new FacesMessage("Ya ha solicitado plaza en éste viaje"));
		} catch (NotPersistedException er) {
			er.printStackTrace();
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
			FacesMessage message = new FacesMessage(e.getMessage());
			throw new ValidatorException(message);
		}
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
						} else {
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
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"formSolicitudes",
										new FacesMessage(
												"El plazo para aceptar solicitudes ya se ha cerrado"));
					}
				} else {
					resultado = "fracaso";
					FacesContext.getCurrentInstance().addMessage(
							"formSolicitudes",
							new FacesMessage(
									"No quedan plazas libres en éste viaje"));
				}
			} else {
				resultado = "fracaso";
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"formSolicitudes",
								new FacesMessage(
										"Ha ocurrido algún error con la solicitud, no se puede confirmar"));
			}
		} catch (Exception e) {
			resultado = "fracaso";
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
		try {
			factoria = Factories.services;
			usuario = factoria.createUsersService().finById(app.getUserId());
			viaje = factoria.createTripsService().findById(app.getTripId());
			app = factoria.createApplicationsService().findById(
					usuario.getId(), viaje.getId());
			if (app != null) {
				if (viaje.getClosingDate().after(new Date())) {
					viaje.setAvailablePax(viaje.getAvailablePax() + 1);
					factoria.createTripsService().updateTrip(viaje);
					factoria.createSeatsService().saveSeat(
							new Seat(usuario.getId(), viaje.getId(), "",
									SeatStatus.EXCLUDED));
					factoria.createApplicationsService().deleteApplication(app);
				} else {
					resultado = "fracaso";
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"formSolicitudes",
									new FacesMessage(
											"La fecha para cancelar solicitudes ha sido cerrada"));
				}

			} else {
				resultado = "fracaso";
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"formSolicitudes",
								new FacesMessage(
										"La solicitud ya ha sido tratada anteriormente"));
			}

		} catch (Exception e) {
			resultado = "fracaso";
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
					.before(new Date()) || Factories.services.createTripsService()
					.findById(app.getTripId()).getAvailablePax()==0)
				state = "SIN PLAZA";
			else
				state = seat.getStatus().name();
		} catch (NotPersistedException e) {
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
			e.printStackTrace();
			existe = false;

		}

		return !existe;
	}

}
