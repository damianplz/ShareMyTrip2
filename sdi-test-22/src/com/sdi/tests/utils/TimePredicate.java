package com.sdi.tests.utils;

import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

public class TimePredicate implements Predicate<WebDriver>{

	//Clase creada para permitir que se espere para que cargue una pagina
	//el tiempo que se pida
	
	boolean accedidoAnteriormente = true;

	@Override
	public boolean apply(WebDriver arg0) {
		accedidoAnteriormente = !accedidoAnteriormente;
		
		return accedidoAnteriormente;
		
	}

}
