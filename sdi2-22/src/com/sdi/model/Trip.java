package com.sdi.model;

import java.util.Date;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.exception.NotPersistedException;
import org.primefaces.model.SelectableDataModel;

/**
 * This class is not an entity, it is a DTO with the same fields as 
 * a row in the table
 * 
 * @see Data Transfer Object pattern
 * @author alb
 *
 */
public class Trip {
	
	private Boolean propio = false;
	
	private Long id;
	
	private AddressPoint departure;
	private AddressPoint destination;
	private Date arrivalDate;
	private Date departureDate;
	private Date closingDate;
	private Integer availablePax = 0; 
	private Integer maxPax = 0;
	private Double estimatedCost = 0.0;
	private String comments;
	private TripStatus status;
	
	private Long promoterId;
	
	public Trip(){
		
	}

	public Trip(AddressPoint salida, AddressPoint llegada, Date fechaSalida,
			Date fechaLlegada, Date fechaLimite, Double coste,
			String comentarios, Integer plazasMaximas, Integer plazasDisponibles, Long userId) {
		setDeparture(salida);
		setDestination(llegada);
		setArrivalDate(fechaLlegada);
		setDepartureDate(fechaSalida);
		setClosingDate(fechaLimite);
		setMaxPax(plazasMaximas);
		setAvailablePax(plazasDisponibles);
		setComments(comentarios);
		setEstimatedCost(coste);
		setStatus(TripStatus.OPEN);
		setPromoterId(userId);
	}

	private User getPromotor(){
		try {
			return Factories.services.createUsersService().finById(promoterId);
		} catch (NotPersistedException e) {
			return null;//TODO OJO PELIGRO TESTEANDO
		}
	}
	
	public String getPromotorName(){
		User user=getPromotor();
		return user.getName() +" "+ user.getSurname();
	}
	
	public AddressPoint getDeparture() {
		return departure;
	}

	public void setDeparture(AddressPoint departure) {
		this.departure = departure;
	}

	public TripStatus getStatus() {
		return status;
	}

	public void setStatus(TripStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AddressPoint getDestination() {
		return destination;
	}

	public void setDestination(AddressPoint destination) {
		this.destination = destination;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public Integer getAvailablePax() {
		return availablePax;
	}

	public void setAvailablePax(Integer availablePax) {
		this.availablePax = availablePax;
	}

	public Integer getMaxPax() {
		return maxPax;
	}

	public void setMaxPax(Integer maxPax) {
		this.maxPax = maxPax;
	}

	public Double getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(Double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id 
				+ ", departure=" + departure 
				+ ", destination=" + destination 
				+ ", arrivalDate=" + arrivalDate 
				+ ", departureDate=" + departureDate 
				+ ", closingDate=" + closingDate 
				+ ", availablePax=" + availablePax 
				+ ", maxPax=" + maxPax 
				+ ", estimatedCost=" + estimatedCost 
				+ ", comments=" + comments 
				+ ", status=" + status
				+ ", promoterId=" + promoterId 
			+ "]";
	}

	public Boolean getPropio() {
		return propio;
	}

	public void setPropio(Boolean propio) {
		this.propio = propio;
	}
	
	public boolean isCancelled(){return status != TripStatus.CANCELLED;}


}
