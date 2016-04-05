package com.sdi.tests.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_LoginForm {

	
	
   public void rellenaFormulario(WebDriver driver, String userName, String passwordUser)
   {
		WebElement login = driver.findElement(By.id("login-form:user"));
		login.click();
		login.clear();
		login.sendKeys(userName);
		WebElement password = driver.findElement(By.id("login-form:pass"));
		password.click();
		password.clear();
		password.sendKeys(passwordUser);
		//Pulsar el boton de Alta.
		By boton = By.id("login-form:log");
		driver.findElement(boton).click();	   
   }
	
	
	
}
