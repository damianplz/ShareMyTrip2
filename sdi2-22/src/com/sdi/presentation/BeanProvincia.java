package com.sdi.presentation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@ManagedBean(name = "provincia")
@ApplicationScoped
public class BeanProvincia {
	
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
	        SelectItemGroup castillaLaMancha = new SelectItemGroup("Castilla-La Mancha");
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

	         
	        SelectItem option31 = new SelectItem("Option 3.1", "Option 3.1");
	        SelectItem option32 = new SelectItem("Option 3.2", "Option 3.2");
	        SelectItem option33 = new SelectItem("Option 3.3", "Option 3.3");
	        SelectItem option34 = new SelectItem("Option 3.4", "Option 3.4");
	         
	        SelectItem option41 = new SelectItem("Option 4.1", "Option 4.1");
	         
	        SelectItem option111 = new SelectItem("Option 1.1.1");
	        SelectItem option112 = new SelectItem("Option 1.1.2");
	        group11.setSelectItems(new SelectItem[]{option111, option112});
	         
	        SelectItem option121 = new SelectItem("Option 1.2.1", "Option 1.2.1");
	        SelectItem option122 = new SelectItem("Option 1.2.2", "Option 1.2.2");
	        SelectItem option123 = new SelectItem("Option 1.2.3", "Option 1.2.3");
	        group12.setSelectItems(new SelectItem[]{option121, option122, option123});
	         
	        SelectItem option211 = new SelectItem("Option 2.1.1", "Option 2.1.1");
	        group21.setSelectItems(new SelectItem[]{option211});
	         
	        group1.setSelectItems(new SelectItem[]{group11, group12});
	        group2.setSelectItems(new SelectItem[]{group21});
	        group3.setSelectItems(new SelectItem[]{option31, option32, option33, option34});
	        group4.setSelectItems(new SelectItem[]{option41});
	         
	        comunidades.add(group1);
	        comunidades.add(group2);
	        comunidades.add(group3);
	        comunidades.add(group4);
	    }

		public List<SelectItem> getComunidades() {
			return comunidades;
		}

		public void setComunidades(List<SelectItem> comunidades) {
			this.comunidades = comunidades;
		}

		public String getProvincia() {
			return provincia;
		}

		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}
	     


}
