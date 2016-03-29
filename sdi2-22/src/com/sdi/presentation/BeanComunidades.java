package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
 
 
@ManagedBean(name = "comunidades", eager=true)
@ApplicationScoped
public class BeanComunidades {
	
	private List<Item> comunidades; 



	@PostConstruct
	public void init() {
		String prov[] = { "Andalucía", "Aragón", "Canarias", "Cantabria",
				"Castilla y León", "Castilla-La Mancha", "Cataluña", "Ceuta",
				"Comunidad Valenciana", "Comunidad de Madrid", "Extremadura",
				"Galicia", "Islas Baleares", "La Rioja", "Melilla", "Navarra",
				"País Vasco", "Principado de Asturias", "Región de Murcia" };
		comunidades=new ArrayList<Item>();
		for(int i=0;i<prov.length;i++)
			comunidades.add(new Item(i,prov[i].toString(),prov[i].toString()));

	}



	public List<Item> getComunidades() {
		return comunidades;
	}
}