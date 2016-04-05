package com.sdi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_ModifViajeForm {

	public void rellenaFormulario(WebDriver driver,String depDate, String depHour,
			String arrCity,	String arrDate, String arrHour, String closHour,  
			String closDate) {

		// Datos de salida
		WebElement departureDate = driver.findElement(By
				.id("viaje:departureDate"));
		departureDate.click();
		departureDate.clear();
		departureDate.sendKeys(depDate);
		WebElement departureHour = driver.findElement(By
				.id("viaje:departureHour"));
		departureHour.click();
		departureHour.clear();
		departureHour.sendKeys(depHour);

		// Datos de llegada

		WebElement arrivalCity = driver.findElement(By.id("viaje:arrivalCity"));
		arrivalCity.click();
		arrivalCity.clear();
		arrivalCity.sendKeys(arrCity);
		WebElement arrivalDate = driver.findElement(By.id("viaje:arrivalDate"));
		arrivalDate.click();
		arrivalDate.clear();
		arrivalDate.sendKeys(arrDate);
		WebElement arrivalHour = driver.findElement(By.id("viaje:arrivalHour"));
		arrivalHour.click();
		arrivalHour.clear();
		arrivalHour.sendKeys(arrHour);
		
		// Datos generales
		WebElement closingDate = driver.findElement(By.id("viaje:closingDate"));
		closingDate.click();
		closingDate.clear();
		closingDate.sendKeys(closDate);
		WebElement closingHour = driver.findElement(By.id("viaje:closingHour"));
		closingHour.click();
		closingHour.clear();
		closingHour.sendKeys(closHour);
		// Pulsar el boton de Alta.
		By boton = By.id("viaje:enviar");
		driver.findElement(boton).click();
	}

}
