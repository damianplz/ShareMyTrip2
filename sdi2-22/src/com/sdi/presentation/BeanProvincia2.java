package com.sdi.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@ManagedBean(name = "provincia2")
@ApplicationScoped
public class BeanProvincia2 {

	private List<SelectItem> comunidades;
	private String provincia;

	@PostConstruct
	public void init() {
		comunidades = new ArrayList<SelectItem>();

		SelectItemGroup andalucia = new SelectItemGroup("Andalucía");
		SelectItemGroup aragon = new SelectItemGroup("Aragón");
		SelectItemGroup canarias = new SelectItemGroup("Canarias");
		SelectItemGroup cantabria = new SelectItemGroup("Cantabria");
		SelectItemGroup castillaLeon = new SelectItemGroup("Castilla y León");
		SelectItemGroup castillaLaMancha = new SelectItemGroup(
				"Castilla-La Mancha");
		SelectItemGroup valencia = new SelectItemGroup("Comunidad Valenciana");
		SelectItemGroup cataluña = new SelectItemGroup("Cataluña");
		SelectItemGroup ceuta = new SelectItemGroup("Ceuta");
		SelectItemGroup madrid = new SelectItemGroup("Comunidad de Madrid");
		SelectItemGroup extremadura = new SelectItemGroup("Extremadura");
		SelectItemGroup galicia = new SelectItemGroup("Galicia");
		SelectItemGroup baleares = new SelectItemGroup("Islas Baleares");
		SelectItemGroup laRioja = new SelectItemGroup("La Rioja");
		SelectItemGroup melilla = new SelectItemGroup("Melilla");
		SelectItemGroup navarra = new SelectItemGroup("Navarra");
		SelectItemGroup paisvasco = new SelectItemGroup("País Vasco");
		SelectItemGroup asturias = new SelectItemGroup("Principado de Asturias");
		SelectItemGroup murcia = new SelectItemGroup("Región de Murcia");

		SelectItem alava = new SelectItem("Alava");
		SelectItem albacete = new SelectItem("Albacete");
		SelectItem alicante = new SelectItem("Alicante");
		SelectItem almeria = new SelectItem("Almería");
		SelectItem asturiasI = new SelectItem("Asturias");
		SelectItem avila = new SelectItem("Avila");
		SelectItem badajoz = new SelectItem("Badajoz");
		SelectItem barcelona = new SelectItem("Barcelona");
		SelectItem burgos = new SelectItem("Burgos");
		SelectItem caceres = new SelectItem("Cáceres");
		SelectItem cadiz = new SelectItem("Cádiz");
		SelectItem cantabriaI = new SelectItem("Cantabria");
		SelectItem castellon = new SelectItem("Castellón");
		SelectItem ceutaI = new SelectItem("Ceuta");
		SelectItem ciudadReal = new SelectItem("Ciudad Real");
		SelectItem cordoba = new SelectItem("Córdoba");
		SelectItem coruña = new SelectItem("La Coruña");
		SelectItem cuenca = new SelectItem("Cuenca");
		SelectItem gerona = new SelectItem("Gerona");
		SelectItem granada = new SelectItem("Granada");
		SelectItem guadalajara = new SelectItem("Guadalajara");
		SelectItem guipuzcoa = new SelectItem("Guipúzcoa");
		SelectItem huelva = new SelectItem("Huelva");
		SelectItem huesca = new SelectItem("Huesca");
		SelectItem balearesI = new SelectItem("Islas Baleares");
		SelectItem jaen = new SelectItem("Jaén");
		SelectItem leon = new SelectItem("León");
		SelectItem lerida = new SelectItem("Lérida");
		SelectItem lugo = new SelectItem("Lugo");
		SelectItem madridI = new SelectItem("Madrid");
		SelectItem malagaI = new SelectItem("Málaga");
		SelectItem melillaI = new SelectItem("Melilla");
		SelectItem murciaI = new SelectItem("Murcia");
		SelectItem navarraI = new SelectItem("Navarra");
		SelectItem orense = new SelectItem("Orense");
		SelectItem palencia = new SelectItem("Palencia");
		SelectItem lasPalmas = new SelectItem("Las Palmas");
		SelectItem pontevedra = new SelectItem("Pontevedra");
		SelectItem laRiojaI = new SelectItem("La Rioja");
		SelectItem salamanca = new SelectItem("Salamanca");
		SelectItem segovia = new SelectItem("Segovia");
		SelectItem sevilla = new SelectItem("Sevilla");
		SelectItem soria = new SelectItem("Soria");
		SelectItem tarragona = new SelectItem("Tarragona");
		SelectItem santaCruz = new SelectItem("Santa Cruz de Tenerife");
		SelectItem teruel = new SelectItem("Teruel");
		SelectItem toledo = new SelectItem("Toledo");
		SelectItem valenciaI = new SelectItem("Valencia");
		SelectItem valladolid = new SelectItem("Valladolid");
		SelectItem vizcaya = new SelectItem("Vizcaya");
		SelectItem zamora = new SelectItem("Zamora");
		SelectItem zaragoza = new SelectItem("Zaragoza");

		String provincias[] = { "Alava", "Albacete", "Alicante", "Almería",
				"Asturias", "Avila", "Badajoz", "Barcelona", "Burgos",
				"Cáceres", "Cádiz", "Cantabria", "Castellón", "Ciudad Real",
				"Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada",
				"Guadalajara", "Guipúzcoa", "Huelva", "Huesca",
				"Islas Baleares", "Jaén", "León", "Lérida", "Lugo", "Madrid",
				"Málaga", "Murcia", "Navarra", "Orense", "Palencia",
				"Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia",
				"Sevilla", "Soria", "Tarragona", "Santa Cruz de Tenerife",
				"Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya",
				"Zamora", "Zaragoza" };
		andalucia.setSelectItems(new SelectItem[] { almeria, cadiz, cordoba,
				granada, huelva, jaen, malagaI, sevilla });
		aragon.setSelectItems(new SelectItem[] { huesca, teruel, zaragoza });
		canarias.setSelectItems(new SelectItem[] { santaCruz, lasPalmas });
		cantabria.setSelectItems(new SelectItem[] { cantabriaI });
		castillaLeon.setSelectItems(new SelectItem[] { avila, leon, palencia,
				salamanca, segovia, soria, valladolid, zamora, burgos });
		castillaLaMancha.setSelectItems(new SelectItem[] { albacete,
				ciudadReal, cuenca, guadalajara, toledo });
		valencia.setSelectItems(new SelectItem[] { alicante, castellon,
				valenciaI });
		cataluña.setSelectItems(new SelectItem[] { barcelona, gerona, lerida,
				tarragona });
		ceuta.setSelectItems(new SelectItem[] { ceutaI });
		madrid.setSelectItems(new SelectItem[] { madridI });
		extremadura.setSelectItems(new SelectItem[] { badajoz, caceres });
		galicia.setSelectItems(new SelectItem[] { coruña, lugo, orense,
				pontevedra });
		baleares.setSelectItems(new SelectItem[] { balearesI });
		laRioja.setSelectItems(new SelectItem[] { laRiojaI });
		melilla.setSelectItems(new SelectItem[] { melillaI });
		navarra.setSelectItems(new SelectItem[] { navarraI });
		paisvasco
				.setSelectItems(new SelectItem[] { alava, guipuzcoa, vizcaya });
		asturias.setSelectItems(new SelectItem[] { asturiasI });
		murcia.setSelectItems(new SelectItem[] { murciaI });

		comunidades.add(andalucia);
		comunidades.add(aragon);
		comunidades.add(canarias);
		comunidades.add(cantabria);
		comunidades.add(castillaLeon);
		comunidades.add(castillaLaMancha);
		comunidades.add(valencia);
		comunidades.add(cataluña);
		comunidades.add(ceuta);
		comunidades.add(madrid);
		comunidades.add(extremadura);
		comunidades.add(galicia);
		comunidades.add(baleares);
		comunidades.add(laRioja);
		comunidades.add(melilla);
		comunidades.add(navarra);
		comunidades.add(paisvasco);
		comunidades.add(asturias);
		comunidades.add(murcia);

	}

	public List<SelectItem> getComunidades() {
		return comunidades;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
