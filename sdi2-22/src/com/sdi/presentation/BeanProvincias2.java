package com.sdi.presentation;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.*;

@ManagedBean(name = "provincias2")
@ApplicationScoped
public class BeanProvincias2 {

	public Map<String, String> getAsturias() {
		return asturias;
	}

	public Map<String, String> getMurcia() {
		return murcia;
	}

	public Map<String, String> getPaisVasco() {
		return paisVasco;
	}

	public Map<String, String> getNavarra() {
		return navarra;
	}

	public Map<String, String> getMelilla() {
		return melilla;
	}

	public Map<String, String> getLaRioja() {
		return laRioja;
	}

	public Map<String, String> getIslasBaleares() {
		return islasBaleares;
	}

	public Map<String, String> getGalicia() {
		return galicia;
	}

	public Map<String, String> getExtremadura() {
		return extremadura;
	}

	public Map<String, String> getComunidadDeMadrid() {
		return comunidadDeMadrid;
	}

	public Map<String, String> getComunidadValenciana() {
		return comunidadValenciana;
	}

	public Map<String, String> getCeuta() {
		return ceuta;
	}

	public Map<String, String> getCataluña() {
		return cataluña;
	}

	public Map<String, String> getCastillaLaMancha() {
		return castillaLaMancha;
	}

	public Map<String, String> getCastillaLeon() {
		return castillaLeon;
	}

	public Map<String, String> getCantabria() {
		return cantabria;
	}

	public Map<String, String> getCanarias() {
		return canarias;
	}

	public Map<String, String> getAragon() {
		return aragon;
	}

	public Map<String, String> getAndalucia() {
		return andalucia;
	}

	Map<String, String> asturias;
	Map<String, String> murcia;
	Map<String, String> paisVasco;
	Map<String, String> navarra;
	Map<String, String> melilla;
	Map<String, String> laRioja;
	Map<String, String> islasBaleares;
	Map<String, String> galicia;
	Map<String, String> extremadura;
	Map<String, String> comunidadDeMadrid;
	Map<String, String> comunidadValenciana;
	Map<String, String> ceuta;
	Map<String, String> cataluña;
	Map<String, String> castillaLaMancha;
	Map<String, String> castillaLeon;
	Map<String, String> cantabria;
	Map<String, String> canarias;
	Map<String, String> aragon;
	Map<String, String> andalucia;

	public BeanProvincias2() {
	}

	String provincias[] = { "Alava", "Albacete", "Alicante", "Almería",
			"Asturias", "Avila", "Badajoz", "Barcelona", "Burgos", "Cáceres",
			"Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba",
			"La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara",
			"Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", "León",
			"Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra",
			"Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja",
			"Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona",
			"Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia",
			"Valladolid", "Vizcaya", "Zamora", "Zaragoza" };

	String comunidades[] = { "Andalucía", "Aragón", "Canarias", "Cantabria",
			"Castilla y León", "Castilla-La Mancha", "Cataluña", "Ceuta",
			"Comunidad Valenciana", "Comunidad de Madrid", "Extremadura",
			"Galicia", "Islas Baleares", "La Rioja", "Melilla", "Navarra",
			"País Vasco", "Principado de Asturias", "Región de Murcia" };
	private Map<Map<String, String>, String> todas;
	
	public Map<String, String> provincia;

	public void inicia() {
		Map<String, String> asturias = new HashMap<String, String>();
		asturias.put("Asturias", "Principado de Asturias");
		todas.put(asturias, "Comunidad");
		Map<String, String> murcia = new HashMap<String, String>();
		murcia.put("Murcia", "Región de Murcia");
		todas.put(murcia, "Comunidad");
		Map<String, String> paisVasco = new HashMap<String, String>();
		paisVasco.put("Guipúzcoa", "País Vasco");
		paisVasco.put("Alava", "País Vasco");
		paisVasco.put("Vizcaya", "País Vasco");
		todas.put(paisVasco, "Comunidad");
		Map<String, String> navarra = new HashMap<String, String>();
		navarra.put("Navarra", "Navarra");
		todas.put(navarra, "Comunidad");
		Map<String, String> melilla = new HashMap<String, String>();
		melilla.put("Melilla", "Melilla");
		todas.put(melilla, "Comunidad");
		Map<String, String> laRioja = new HashMap<String, String>();
		laRioja.put("La Rioja", "La Rioja");
		todas.put(laRioja, "Comunidad");
		Map<String, String> islasBaleares = new HashMap<String, String>();
		islasBaleares.put("Islas Baleares", "Islas Baleares");
		todas.put(islasBaleares, "Comunidad");
		Map<String, String> galicia = new HashMap<String, String>();
		galicia.put("La Coruña", "Galicia");
		galicia.put("Lugo", "Galicia");
		galicia.put("Orense", "Galicia");
		galicia.put("Pontevedra", "Galicia");
		todas.put(galicia, "Comunidad");
		Map<String, String> extremadura = new HashMap<String, String>();
		extremadura.put("Badajoz", "Extremadura");
		extremadura.put("Cáceres", "Extremadura");
		todas.put(extremadura, "Comunidad");
		Map<String, String> comunidadDeMadrid = new HashMap<String, String>();
		comunidadDeMadrid.put("Madrid", "Comunidad de Madrid");
		todas.put(comunidadDeMadrid, "Comunidad");
		Map<String, String> comunidadValenciana = new HashMap<String, String>();
		comunidadValenciana.put("Alicante", "Comunidad Valenciana");
		comunidadValenciana.put("Castellón", "Comunidad Valenciana");
		comunidadValenciana.put("Valencia", "Comunidad Valenciana");
		todas.put(comunidadValenciana, "Comunidad");
		Map<String, String> ceuta = new HashMap<String, String>();
		ceuta.put("Ceuta", "Ceuta");
		todas.put(ceuta, "Comunidad");
		Map<String, String> cataluña = new HashMap<String, String>();
		cataluña.put("Barcelona", "Cataluña");
		cataluña.put("Gerona", "Cataluña");
		cataluña.put("Lérida", "Cataluña");
		cataluña.put("Tarragona", "Cataluña");
		todas.put(cataluña, "Comunidad");
		Map<String, String> castillaLaMancha = new HashMap<String, String>();
		castillaLaMancha.put("Albacete", "Castilla-La Mancha");
		castillaLaMancha.put("Ciudad Real", "Castilla-La Mancha");
		castillaLaMancha.put("Cuenca", "Castilla-La Mancha");
		castillaLaMancha.put("Guadalajara", "Castilla-La Mancha");
		castillaLaMancha.put("Toledo", "Castilla-La Mancha");
		todas.put(castillaLaMancha, "Comunidad");
		Map<String, String> castillaLeon = new HashMap<String, String>();
		castillaLeon.put("Avila", "Castilla y León");
		castillaLeon.put("León", "Castilla y León");
		castillaLeon.put("Palencia", "Castilla y León");
		castillaLeon.put("Salamanca", "Castilla y León");
		castillaLeon.put("Segovia", "Castilla y León");
		castillaLeon.put("Soria", "Castilla y León");
		castillaLeon.put("Valladolid", "Castilla y León");
		castillaLeon.put("Zamora", "Castilla y León");
		castillaLeon.put("Burgos", "Castilla y León");
		todas.put(castillaLeon, "Comunidad");
		Map<String, String> cantabria = new HashMap<String, String>();
		cantabria.put("Cantabria", "Cantabria");
		todas.put(cantabria, "Comunidad");
		Map<String, String> canarias = new HashMap<String, String>();
		canarias.put("Santa Cruz de Tenerife", "Canarias");
		canarias.put("Las Palmas", "Canarias");
		todas.put(canarias, "Comunidad");
		Map<String, String> aragon = new HashMap<String, String>();
		aragon.put("Huesca", "Aragón");
		aragon.put("Zaragoza", "Aragón");
		aragon.put("Teruel", "Aragón");
		todas.put(aragon, "Comunidad");
		Map<String, String> andalucia = new HashMap<String, String>();
		andalucia.put("Almería", "Andalucía");
		andalucia.put("Cádiz", "Andalucía");
		andalucia.put("Córdoba", "Andalucía");
		andalucia.put("Granada", "Andalucía");
		andalucia.put("Huelva", "Andalucía");
		andalucia.put("Jaén", "Andalucía");
		andalucia.put("Málaga", "Andalucía");
		andalucia.put("Sevilla", "Andalucía");
		todas.put(andalucia, "Comunidad");
	}

	public String[] getProvincias() {

		return provincias;
	}

	public String[] getComunidades() {
		return comunidades;
	}
	
	public Map<String, String> getProvincia(String comunidad){
		
		switch(comunidad){
		case "Andalucía":
			return getAndalucia();
		case "Aragón":
			return getAragon();
		case "Canarias":
			return getCanarias();
		case "Cantabria":
			return getCantabria();
		case "Castilla y León":
			return getCastillaLeon();
		case "Castilla-La Mancha":
			return getCastillaLaMancha();
		case "Cataluña":
			return getCataluña();
		case "Ceuta":
			return getCeuta();
		case "Comunidad Valenciana":
			return getComunidadValenciana();
		case "Comunidad de Madrid":
			return getComunidadDeMadrid();
		case "Extremadura":
			return getExtremadura();
		case "Galicia":
			return getGalicia();
		case "Islas Baleares":
			return getIslasBaleares();
		case "La Rioja":
			return getLaRioja();
		case "Melilla":
			return getMelilla();
		case "Navarra":
			return getNavarra();
		case "País Vasco":
			return getPaisVasco();
		case "Principado de Asturias":
			return getAsturias();
		case "Región de Murcia":
			return getMurcia();
			default: return null;
		}
			
			

		
	}

}
