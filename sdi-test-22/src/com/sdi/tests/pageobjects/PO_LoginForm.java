package com.sdi.tests.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_LoginForm {

	
	
   public void rellenaFormulario(WebDriver driver, String userName, String passwordUser)
   {
		WebElement login = driver.findElement(By.id("form-cuerpo:login"));
		login.click();
		login.clear();
		login.sendKeys(userName);
		WebElement password = driver.findElement(By.id("form-cuerpo:password"));
		password.click();
		password.clear();
		password.sendKeys(passwordUser);
		//Pulsar el boton de Login.
		By boton = By.id("form-cuerpo:j_idt20");
		driver.findElement(boton).click();	   
   }
	
	
	
}
