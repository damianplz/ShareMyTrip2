package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.bean.*;


@ManagedBean(name = "provincias", eager=true)
@ApplicationScoped
public class BeanProvincias {
	
	private List<Item> provincias; 



	@PostConstruct
	public void init() {
		String prov[] = { "Alava", "Albacete", "Alicante", "Almería",
				"Oviedo", "Avila", "Badajoz", "Barcelona", "Burgos",
				"Cáceres", "Cádiz", "Santander", "Castellón", "Ciudad Real",
				"Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada",
				"Guadalajara", "Guipúzcoa", "Huelva", "Huesca",
				"Mallorca", "Jaén", "León", "Lérida", "Lugo", "Madrid",
				"Málaga", "Murcia", "Pamplona", "Orense", "Palencia",
				"Las Palmas", "Pontevedra", "Logroño", "Salamanca", "Segovia",
				"Sevilla", "Soria", "Tarragona", "Santa Cruz de Tenerife",
				"Teruel", "Toledo", "Valencia", "Valladolid", "Bilbao",
				"Zamora", "Zaragoza" };
		provincias=new ArrayList<Item>();
		for(int i=0;i<prov.length;i++)
			provincias.add(new Item(i,prov[i].toString(),prov[i].toString()));

	}



	public List<Item> getProvincias() {
		return provincias;
	}

}
