package com.sdi.tests.Tests;

import java.util.List;

import net.sf.cglib.proxy.Factory;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.util.concurrent.Service;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;
import com.sdi.tests.pageobjects.PO_AltaForm;
import com.sdi.tests.pageobjects.PO_LoginForm;
import com.sdi.tests.pageobjects.PO_ModifViajeForm;
import com.sdi.tests.pageobjects.PO_RegViajeForm;
import com.sdi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SDI2_Tests {

	private final static String EXTERNO = "http://localhost:8180/sdi2-22/";
	private final static String INTERNO="http://localhost:8280/sdi2-22/";

	WebDriver driver;
	List<WebElement> elementos = null;
	String selectedServer;

	public SDI2_Tests() {
		selectedServer = INTERNO;
	}

	@Before
	public void run() {
		driver = new FirefoxDriver();
		driver.get(selectedServer);
	}

	@After
	public void end() {
		
		//Eliminar el usuario tras haberse creado en el test
		UserDao user = Factories.persistence.newUserDao();
		User test=user.findByLogin("testSDI");
		if (test!=null) {
			user.delete(test.getId());
		}
		
		
		driver.quit();
	}

	// PRUEBAS
	
	//0. Prueba Funciona Servidor 
	@Test
	public void cargarPaginaInicio(){
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 5);
		SeleniumUtils.textoPresentePagina(driver, "Formulario de login");
	}
	

	// 1. [RegVal] Registro de Usuario con datos válidos.
	@Test
	public void t01_RegVal() {
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:misubmenu1",
				"form-cabecera:linkCrearUsuario");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "registro", 10);
		new PO_AltaForm().rellenaFormulario(driver, "testSDI", "testSDI",
				"testSDI", "testSDI@mail.es", "testSDI", "testSDI");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "menuPerfil", 10);
		SeleniumUtils.textoPresentePagina(driver, "Perfil");
	}

	// 2. [RegInval] Registro de Usuario con datos inválidos (contraseñas //
	// diferentes).
	@Test
	public void t02_RegInval() {
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:misubmenu1",
				"form-cabecera:linkCrearUsuario");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "registro", 10);
		new PO_AltaForm().rellenaFormulario(driver, "testSDI", "testSDI",
				"testSDI", "testSDI@mail.es", "pass", "passDistinta");
		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 5);
		SeleniumUtils.textoPresentePagina(driver, "Password 1 should match with Password 2.");

	}

//	// 3. [IdVal] Identificación de Usuario registrado con datos válidos.
//	@Test
//	public void t03_IdVal() {
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		new PO_LoginForm().rellenaFormulario(driver, "usuario1", "usuario1");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.textoPresentePagina(driver, "Fernando");
//	}
//
//	// 4. [IdInval] Identificación de usuario registrado con datos inválidos.
//	@Test
//	public void t04_IdInval() {
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		new PO_LoginForm().rellenaFormulario(driver, "usuario1", "incorrecto");
//		SeleniumUtils.EsperaCargaPagina(driver, "text",
//				"La contraseña no es correcta", 10);
//		SeleniumUtils.textoPresentePagina(driver,
//				"La contraseña no es correcta");
//		new PO_LoginForm().rellenaFormulario(driver, "noExisto", "usuario1");
//		SeleniumUtils.EsperaCargaPagina(driver, "text", "El usuario no existe",
//				10);
//		SeleniumUtils.textoPresentePagina(driver, "El usuario no existe");
//	}
//
//	// 5. [AccInval] Intento de acceso con URL desde un usuario no público
//	// (no // identificado). Intento de acceso a vistas de acceso privado. -
//	// PASA
//	@Test
//	public void t05_AccInval() {
//		String inval = selectedServer + "infoCompleta.xhtml";
//		driver.get(inval);
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		SeleniumUtils.textoPresentePagina(driver,
//				"Formulario de inicio de sesión");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		SeleniumUtils.textoPresentePagina(driver, "Inicie sesión");
//	}
//
//	// 6. [RegViajeVal] Registro de un viaje nuevo con datos válidos.
//	@Test
//	public void t06_RegViajeVal() {
//		t03_IdVal();
//		SeleniumUtils
//				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkRegistrarViaje");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "viaje", 10);
//		new PO_RegViajeForm().rellenaFormulario(driver, "addres1", "city1",
//				"state1", "country1", "35260", "", "", "20/08/2016", "12:00",
//				"address2", "city2", "state2", "country2", "25036", "2.366",
//				"-53.6982", "21/08/2016", "01:00", "22:05", "4", "296.85",
//				"Viaje de prueba", "15/08/2016");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesParti", 10);
//		SeleniumUtils.textoPresentePagina(driver,
//				"Mon Aug 15 22:05:00 CEST 2016");
//	}
//
//	// 7. [RegViajeInVal] Registro de un viaje nuevo con datos inválidos.
//	@Test
//	public void t07_RegViajeInVal() {
//		t03_IdVal();
//		SeleniumUtils
//				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkRegistrarViaje");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "viaje", 10);
//		new PO_RegViajeForm().rellenaFormulario(driver, "addres1", "city1",
//				"state1", "", "35260", "", "", "20/08/2016", "12:00",
//				"address2", "city2", "state2", "country2", "25036", "2.366",
//				"-53.6982", "21/08/2016", "01:00", "22:05", "4", "296.85",
//				"Viaje de prueba", "23/08/2016");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "viaje", 10);
//		SeleniumUtils.textoPresentePagina(driver, "Registre un nuevo viaje");
//	}
//
//	// 8. [EditViajeVal] Edición de viaje existente con datos válidos.
//	@Test
//	public void t08_EditViajeVal() {
//		// Editamos el viaje de Rue de Percebe cambiando
//		// el nombre de la ciudad
//		t03_IdVal();
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesParti", 10);
//		WebElement element = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'Sat Oct 15 12:50:00 "
//								+ "CEST 2016')]/following-sibling::*/"
//								+ "a[contains(text(),'Modif')]"));
//		element.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "viaje", 10);
//		new PO_ModifViajeForm().rellenaFormulario(driver, "20/10/2016",
//				"23:59", "cityModif", "21/10/2016", "03:05", "12:50",
//				"15/10/2016");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesParti", 10);
//		SeleniumUtils.textoPresentePagina(driver,
//				"Sat Oct 15 12:50:00 CEST 2016");
//	}
//
//	// 9. [EditViajeInVal] Edición de viaje existente con datos inválidos.
//	@Test
//	public void t09_EditViajeInVal() {
//		// Volvemos a escoger el viaje de Rue del Percebe
//		// pero poninedo mal las fechas
//		t03_IdVal();
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesParti", 10);
//		WebElement element = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'Sat Oct 15 12:50:00 CEST"
//								+ " 2016')]/following-sibling::*/"
//								+ "a[contains(text(),'Modif')]"));
//		element.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "viaje", 10);
//		new PO_ModifViajeForm().rellenaFormulario(driver, "20/10/2016",
//				"23:59", "cityModif", "21/10/2016", "03:05", "12:50",
//				"23/10/2016");
//		SeleniumUtils.EsperaCargaPagina(driver, "text",
//				"La fecha de salida es anterior a la de cierre", 10);
//		driver.findElement(By
//				.xpath(".//span[contains(text(),'La fecha de salida "
//						+ "es anterior a la de cierre')]"));
//	}
//
//	// 10. [CancelViajeVal] Cancelación de un viaje existente por un
//	// promotor.
//	@Test
//	public void t10_CancelViajeVal() {
//		login("usuario2", "usuario2");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesParti", 10);
//		WebElement check = driver.findElement(By
//				.xpath(".//a[contains(text(),'332')]/preceding-sibling::*"));
//		check.click();
//		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
//		driver.findElement(By.id("involucrado:btCancelar")).click();
//		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
//		driver.findElement(By
//				.xpath("//a[contains(text(),'332')]/parent::*/following-sibling"
//						+ "::*[contains(text(),'CANCELLED')]"));
//	}
//
//	// 11. [CancelMulViajeVal] Cancelación de múltiples viajes existentes por un
//	// promotor.
//	@Test
//	public void t11_CancelMulViajeVal() {
//		login("usuario1", "usuario1");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesParti", 10);
//		driver.findElement(
//				By.xpath(".//a[contains(text(),'326')]/preceding-sibling::*"))
//				.click();
//		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
//		driver.findElement(
//				By.xpath(".//a[contains(text(),'327')]/preceding-sibling::*"))
//				.click();
//		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
//		driver.findElement(By.id("involucrado:btCancelar")).click();
//		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
//		driver.findElement(By
//				.xpath("//a[contains(text(),'326')]/parent::*/following-sibling"
//						+ "::*[contains(text(),'CANCELLED')]"));
//		driver.findElement(By
//				.xpath("//a[contains(text(),'327')]/parent::*/following-sibling"
//						+ "::*[contains(text(),'CANCELLED')]"));
//	}
//
//	// 12. [Ins1ViajeAceptVal] Inscribir en un viaje un solo usuario y ser
//	// admitido por el promotor.
//	@Test
//	public void t12_Ins1ViajeAceptVal() {
//		// Como testSDI solicitamos el viaje 338 (hay margen de sobra de
//		// tiempo)
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		new PO_LoginForm().rellenaFormulario(driver, "testSDI", "testSDI");
//		SeleniumUtils
//				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkOpciones");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		WebElement element = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'338')]/following-sibling::"
//								+ "*/a[contains(text(),'Solicitar')]"));
//		element.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesSin", 10);
//		SeleniumUtils.textoPresentePagina(driver, "338");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkCerrarSesion");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "login-form", 10);
//		login("usuario2", "usuario2");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		By ver = By
//				.xpath("//td[contains(text(), 'Calle esmalte, Ponga-España')]"
//						+ "/following-sibling::*/a[contains"
//						+ "(@id, 'linkSolicitantes')]");
//		driver.findElement(ver).click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaSolicitantes", 10);
//
//		By aceptar = By
//				.xpath("//td[contains(text(),'320')]/following-sibling::*"
//						+ "/a[contains(text(),'Aceptar')]");
//		driver.findElement(aceptar).click();
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "320", 10);
//
//	}
//
//	// 13. [Ins2ViajeAceptVal] Inscribir en un viaje dos usuarios y ser
//	// admitidos los dos por el promotor.
//	@Test
//	public void t13_Ins2ViajeAceptVal() {
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		new PO_LoginForm().rellenaFormulario(driver, "usuario1", "usuario1");
//		SeleniumUtils
//				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkOpciones");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		WebElement element = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'343')]/following-sibling::"
//								+ "*/a[contains(text(),'Solicitar')]"));
//		element.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesSin", 10);
//		SeleniumUtils.textoPresentePagina(driver, "343");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkCerrarSesion");
//		login("usuario2", "usuario2");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkOpciones");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		WebElement element2 = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'343')]/following-sibling::"
//								+ "*/a[contains(text(),'Solicitar')]"));
//		element2.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesSin", 10);
//		SeleniumUtils.textoPresentePagina(driver, "343");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkCerrarSesion");
//
//		// Aceptar
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		login("usuario3", "usuario3");
//		SeleniumUtils
//				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		By ver = By
//				.xpath(".//a[contains(text(),'343')]/../following-sibling::*/a"
//						+ "[contains(@id,'linkSolicitantes')]");
//		driver.findElement(ver).click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaSolicitantes", 10);
//
//		By aceptar = By
//				.xpath("//td[contains(text(),'317')]/following-sibling::*/"
//						+ "a[contains(text(),'Aceptar')]");
//		driver.findElement(aceptar).click();
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "317", 10);
//
//		By aceptar2 = By
//				.xpath("//td[contains(text(),'318')]/following-sibling::*/a"
//						+ "[contains(text(),'Aceptar')]");
//		driver.findElement(aceptar2).click();
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "318", 10);
//
//	}
//
//	// 14. [Ins3ViajeAceptInval] Inscribir en un viaje (2 plazas máximo)
//	// dos usuarios y ser admitidos los dos y que un tercero intente inscribirse
//	// en ese mismo viaje pero ya no pueda por falta de plazas.
//	@Test
//	public void t14_Ins3ViajeAceptInval() {
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		new PO_LoginForm().rellenaFormulario(driver, "usuario2", "usuario2");
//		SeleniumUtils
//				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkOpciones");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		WebElement element = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'322')]/following-sibling:"
//								+ ":*/a[contains(text(),'Solicitar')]"));
//		element.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesSin", 10);
//		SeleniumUtils.textoPresentePagina(driver, "322");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkCerrarSesion");
//		login("usuario3", "usuario3");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkOpciones");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		WebElement element2 = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'322')]/following-sibling:"
//								+ ":*/a[contains(text(),'Solicitar')]"));
//		element2.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesSin", 10);
//		SeleniumUtils.textoPresentePagina(driver, "322");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkCerrarSesion");
//
//		// Aceptar
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		login("usuario1", "usuario1");
//		SeleniumUtils
//				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		By ver = By
//				.xpath("//td[contains(text(), 'Calle de Aladdin, Ciudad1-Pais1'"
//						+ ")]/following-sibling::*/a"
//						+ "[contains(@id, 'linkSolicitantes')]");
//		driver.findElement(ver).click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaSolicitantes", 10);
//
//		By aceptar = By
//				.xpath("//td[contains(text(),'318')]/following-sibling::*"
//						+ "/a[contains(text(),'Aceptar')]");
//		driver.findElement(aceptar).click();
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "318", 10);
//
//		By aceptar2 = By
//				.xpath("//td[contains(text(),'319')]/following-sibling::*/"
//						+ "a[contains(text(),'Aceptar')]");
//		driver.findElement(aceptar2).click();
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "319", 10);
//
//		// Otro usuario
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkCerrarSesion");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "login-form", 10);
//		login("testSDI", "testSDI");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkOpciones");
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "322", 10);
//
//	}
//
//	// 15. [CancelNoPromotorVal] Un usuario no promotor Cancela plaza.
//	@Test
//	public void t15_CancelNoPromotorVal() {
//		login("usuario3", "usuario3");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesEx", 10);
//		driver.findElement(
//				By.xpath(".//a[contains(text(),'322')]/../following-sibling::*/"
//						+ "a[contains(text(),'Cancelar')]"))
//				.click();
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "322", 10);
//
//	}
//
//	// 16. [Rech1ViajeVal] Inscribir en un viaje un usuario que será
//	// admitido y después rechazarlo por el promotor.
//	@Test
//	public void t16_Rech1ViajeVal() {
//		login("usuario3", "usuario3");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkOpciones");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		WebElement element = driver
//				.findElement(By
//						.xpath("//td[contains(text(),'350')]/following-sibling:"
//								+ ":*/a[contains(text(),'Solicitar')]"));
//		element.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajesSin", 10);
//		SeleniumUtils.textoPresentePagina(driver, "350");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkCerrarSesion");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "login-form", 10);
//		login("usuario1", "usuario1");
//
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		By ver = By
//				.xpath("//td[contains(text(), 'Sun Aug 21 01:00:00 CEST 2016')]"
//						+ "/following-sibling::*/a["
//						+ "contains(@id, 'linkSolicitantes')]");
//		driver.findElement(ver).click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaSolicitantes", 10);
//		driver.findElement(
//				By.xpath(".//td[contains(text(),'319')]/following-sibling::*/a"
//						+ "[contains(text(),'Aceptar')]"))
//				.click();
//		;
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "319", 10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkMisViajes");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablaViajes", 10);
//		driver.findElement(By.xpath(".//a[contains(text(),'350')]")).click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "info", 10);
//		driver.findElement(By
//				.xpath(".//li[contains(text(),'Maria')]/a[contains(text(),"
//						+ "'Excluir')]")).click();
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Maria Gonzalez", 10);
//	}
//
//	// 17. [i18N1] Cambio del idioma por defecto a un segundo idioma.
//	// (Probar algunas vistas)
//	@Test
//	public void t17_i18N1() {
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
//		SeleniumUtils.textoPresentePagina(driver, "Inicie sesión");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idioma",
//				"form-cabecera:linkingles");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie:inicioGeneral",
//				10);
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkRegistrarse");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "registro", 10);
//		SeleniumUtils.textoPresentePagina(driver, "Register a new account");
//	}
//
//	// 18. [i18N2] Cambio del idioma por defecto a un segundo idioma y
//	// vuelta al idioma por defecto. (Probar algunas vistas)
//	@Test
//	public void t18_i18N2() {
//		t17_i18N1();
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idioma",
//				"form-cabecera:linkespa");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie:inicioGeneral",
//				10);
//		driver.findElement(By.xpath(".//a[@id='form-pie:inicioGeneral']"))
//				.click();
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "login-form", 10);
//		SeleniumUtils.textoPresentePagina(driver,
//				"Formulario de inicio de sesión");
//		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:opciones",
//				"form-cabecera:linkRegistrarse");
//		SeleniumUtils.EsperaCargaPagina(driver, "id", "registro", 10);
//		SeleniumUtils.textoPresentePagina(driver, "Registre una nueva cuenta");
//	}
//
//	/*
//	 * // 19. [OpFiltrado] Prueba para el filtrado opcional.
//	 * 
//	 * @Test public void t19_OpFiltrado() {
//	 * 
//	 * } // 20. [OpOrden] Prueba para la ordenación opcional.
//	 * 
//	 * @Test public void t20_OpOrden() {
//	 * 
//	 * } // 21. [OpPag] Prueba para la paginación opcional.
//	 * 
//	 * @Test public void t21_OpPag() {
//	 * 
//	 * } // 22. [OpMante] Prueba del mantenimiento programado opcional.
//	 * 
//	 * @Test public void t22_OpMante() {
//	 * 
//	 * }
//	 */

	private void login(String user, String pass) {
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		new PO_LoginForm().rellenaFormulario(driver, user, pass);
		SeleniumUtils
				.EsperaCargaPagina(driver, "id", "registradoPrincipal", 10);
	}

}