package com.sdi.presentation;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import com.sdi.infrastructure.Factories;
import com.sdi.model.AddressPoint;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.model.Waypoint;
import com.sdi.persistence.exception.AlreadyPersistedException;

@ManagedBean(name = "viaje")
@SessionScoped
public class BeanViaje extends Trip implements Serializable {

	private static final long serialVersionUID = 1L;

	private String addressA;
	private String cityA;
	private String zipCodeA;
	private String stateA;
	private String countryA;

	private String addressD;
	private String cityD;
	private String zipCodeD;
	private String stateD;
	private String countryD;

	public String getAddressA() {
		addressA = this.getDestination().getAddress();
		return addressA;
	}

	public String getCityA() {
		cityA = this.getDestination().getCity();
		return cityA;
	}

	public String getZipCodeA() {
		zipCodeA = this.getDestination().getZipCode();
		return zipCodeA;
	}

	public String getStateA() {
		stateA = this.getDestination().getState();
		return stateA;
	}

	public String getCountryA() {
		countryA = this.getDestination().getCountry();
		return countryA;
	}

	public String getAddressD() {
		addressD = this.getDeparture().getAddress();
		return addressD;
	}

	public String getCityD() {
		cityD = this.getDeparture().getCity();
		return cityD;
	}

	public String getZipCodeD() {
		zipCodeD = this.getDeparture().getZipCode();
		return zipCodeD;
	}

	public String getStateD() {
		stateD = this.getDeparture().getState();
		return stateD;
	}

	public String getCountryD() {
		countryD = this.getDeparture().getCountry();
		return countryD;
	}

	public BeanViaje() {

	}

	public void precarga() throws ParseException {
		setArrivalDate(new Date());
		setAvailablePax(0);
		setClosingDate(new Date());
		setComments("...");
		setDeparture(new AddressPoint("", "", "", "", "",
				new Waypoint(0.0, 0.0)));
		setDestination(new AddressPoint("", "", "", "", "", new Waypoint(0.0,
				0.0)));
		setDepartureDate(new Date());
		setEstimatedCost(0.0);
		setMaxPax(0);
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER");
		setPromoterId(u.getId());
		setStatus(TripStatus.OPEN);
	}

	public void postcarga() throws ParseException {
		setArrivalDate(new Date());
		setAvailablePax(4);
		setClosingDate(new Date());
		setComments("...");
		setDeparture(new AddressPoint("Calle salida", "Ciudad salida",
				"Provincia salida", "Estado salida", "12345", new Waypoint(0.0,
						0.0)));
		setDestination(new AddressPoint("Calle llegada", "Ciudad llegada",
				"Provincia llegada", "Estado llegada", "12345", new Waypoint(
						0.0, 0.0)));
		setDepartureDate(new Date());
		setEstimatedCost(20.0);
		setMaxPax(5);
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER");
		setPromoterId(u.getId());
		setStatus(TripStatus.OPEN);
	}

	public String crear() {
		String resultado = "exito";
		try {
			Factories.services.createTripsService().saveTrip(this);
		} catch (AlreadyPersistedException e) {
			e.printStackTrace();
			resultado = "fracaso";
		}
		return resultado;
	}
	

	@PostConstruct
	public void init() throws ParseException {
		precarga();
		provincias = service.getProvincias();
		comunidades = serviceC.getComunidades();
	}
	

	public void setAddressD(String address) {
		this.addressD = address;
		this.getDeparture().setAddress(address);

	}

	public void setCityD(String city) {
		this.cityD = city;
		this.getDeparture().setCity(city);

	}

	public void setZipCodeD(String zipCode) {
		this.zipCodeD = zipCode;
		this.getDeparture().setZipCode(zipCode);

	}

	public void setStateD(String state) {
		this.stateD = state;
		this.getDeparture().setState(state);

	}

	public void setCountryD(String country) {
		this.countryD = country;
		this.getDeparture().setCountry(country);
	}

	public void setAddressA(String address) {
		this.addressA = address;
		this.getDestination().setAddress(address);
	}

	public void setCityA(String city) {
		this.cityA = city;
		this.getDestination().setCity(city);
	}

	public void setZipCodeA(String zipCode) {
		this.zipCodeA = zipCode;
		this.getDestination().setZipCode(zipCode);
	}

	public void setStateA(String state) {
		this.stateA = state;
		this.getDestination().setState(state);
	}

	public void setCountryA(String country) {
		this.countryA = country;
		this.getDestination().setCountry(country);
	}
	
	private String option;   
    private Item provincia; 
    private List<Item> provincias;
    
    private Item comunidad; 
    private List<Item> comunidades;
     
    public BeanComunidades getServiceC() {
		return serviceC;
	}

	public void setServiceC(BeanComunidades serviceC) {
		this.serviceC = serviceC;
	}

	public Item getComunidad() {
		return comunidad;
	}

	public void setComunidad(Item comunidad) {
		this.comunidad = comunidad;
	}

	public List<Item> getComunidades() {
		return comunidades;
	}

	@ManagedProperty("#{provincias}")
    private BeanProvincias service;
    
    @ManagedProperty("#{comunidades}")
    private BeanComunidades serviceC;
     
 
    public String getOption() {
        return option;
    }
 
    public void setOption(String option) {
        this.option = option;
    }
 
    public Item getProvincia() {
		return provincia;
	}

	public void setProvincia(Item provincia) {
		this.provincia = provincia;
	}

	public List<Item> getProvincias() {
		return provincias;
	}

	public BeanProvincias getService() {
		return service;
	}

	public void setService(BeanProvincias service) {
        this.service = service;
    }

}
