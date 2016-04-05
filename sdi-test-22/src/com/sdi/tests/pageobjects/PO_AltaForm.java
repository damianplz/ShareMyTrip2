package com.sdi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AltaForm {

	public void rellenaFormulario(WebDriver driver, String pId, String pnombre,
			String papellidos, String pemail,
			String ppass, String prepass) {
		WebElement id = driver.findElement(By.id("form-cuerpo:registroForm:userId"));
		id.click();
		id.clear();
		id.sendKeys(pId);
		WebElement nombre = driver.findElement(By.id("form-cuerpo:registroForm:nombre"));
		nombre.click();
		nombre.clear();
		nombre.sendKeys(pnombre);
		WebElement apellidos = driver.findElement(By.id("form-cuerpo:registroForm:apellidos"));
		apellidos.click();
		apellidos.clear();
		apellidos.sendKeys(papellidos);
		WebElement idcorreo = driver.findElement(By.id("form-cuerpo:registroForm:correo"));
		idcorreo.click();
		idcorreo.clear();
		idcorreo.sendKeys(pemail);
		WebElement password = driver.findElement(By.id("form-cuerpo:registroForm:pwd1"));
		password.click();
		password.clear();
		password.sendKeys(ppass);
		WebElement repassword = driver
				.findElement(By.id("form-cuerpo:registroForm:pwd2"));
		repassword.click();
		repassword.clear();
		repassword.sendKeys(prepass);
		// Pulsar el boton de Alta.
		By boton = By.id("form-cuerpo:registroForm:btnRegistrar");
		driver.findElement(boton).click();
	}

}
