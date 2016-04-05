package com.sdi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RegViajeForm {

	public void rellenaFormulario(WebDriver driver, String depAddress,
			String depCity, String depState, String depCountry, String depCP,
			String depLat, String depLon, String depDate, String depHour,
			String arrAddress, String arrCity, String arrState,
			String arrCountry, String arrCP, String arrLat, String arrLon,
			String arrDate, String arrHour, String closHour, String passengers,
			String cost, String comment, CharSequence closDate) {

		// Datos de salida
		WebElement departureAddress = driver.findElement(By
				.id("viaje:departureAddress"));
		departureAddress.click();
		departureAddress.clear();
		departureAddress.sendKeys(depAddress);
		WebElement departureCity = driver.findElement(By
				.id("viaje:departureCity"));
		departureCity.click();
		departureCity.clear();
		departureCity.sendKeys(depCity);
		WebElement departureState = driver.findElement(By
				.id("viaje:departureState"));
		departureState.click();
		departureState.clear();
		departureState.sendKeys(depState);
		WebElement departureCountry = driver.findElement(By
				.id("viaje:departureCountry"));
		departureCountry.click();
		departureCountry.clear();
		departureCountry.sendKeys(depCountry);
		WebElement departureCP = driver.findElement(By.id("viaje:departureCP"));
		departureCP.click();
		departureCP.clear();
		departureCP.sendKeys(depCP);
		WebElement departureLatitude = driver.findElement(By
				.id("viaje:departureLatitude"));
		departureLatitude.click();
		departureLatitude.clear();
		departureLatitude.sendKeys(depLat);
		WebElement departureLongitud = driver.findElement(By
				.id("viaje:departureLongitud"));
		departureLongitud.click();
		departureLongitud.clear();
		departureLongitud.sendKeys(depLon);
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
		WebElement arrivalAddress = driver.findElement(By
				.id("viaje:arrivalAddress"));
		arrivalAddress.click();
		arrivalAddress.clear();
		arrivalAddress.sendKeys(arrAddress);
		WebElement arrivalCity = driver.findElement(By.id("viaje:arrivalCity"));
		arrivalCity.click();
		arrivalCity.clear();
		arrivalCity.sendKeys(arrCity);
		WebElement arrivalState = driver.findElement(By
				.id("viaje:arrivalState"));
		arrivalState.click();
		arrivalState.clear();
		arrivalState.sendKeys(arrState);
		WebElement arrivalCountry = driver.findElement(By
				.id("viaje:arrivalCountry"));
		arrivalCountry.click();
		arrivalCountry.clear();
		arrivalCountry.sendKeys(arrCountry);
		WebElement arrivalCP = driver.findElement(By.id("viaje:arrivalCP"));
		arrivalCP.click();
		arrivalCP.clear();
		arrivalCP.sendKeys(arrCP);
		WebElement arrivalLatitude = driver.findElement(By
				.id("viaje:arrivalLatitude"));
		arrivalLatitude.click();
		arrivalLatitude.clear();
		arrivalLatitude.sendKeys(arrLat);
		WebElement arrivalLongitud = driver.findElement(By
				.id("viaje:arrivalLongitud"));
		arrivalLongitud.click();
		arrivalLongitud.clear();
		arrivalLongitud.sendKeys(arrLon);
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
		WebElement maxPas = driver.findElement(By.id("viaje:maxPas"));
		maxPas.click();
		maxPas.clear();
		maxPas.sendKeys(passengers);
		WebElement estimatedCost = driver.findElement(By
				.id("viaje:estimatedCost"));
		estimatedCost.click();
		estimatedCost.clear();
		estimatedCost.sendKeys(cost);
		WebElement comments = driver.findElement(By.id("viaje:comments"));
		comments.click();
		comments.clear();
		comments.sendKeys(comment);
		// Pulsar el boton de Alta.
		By boton = By.id("viaje:enviar");
		driver.findElement(boton).click();
	}

}
