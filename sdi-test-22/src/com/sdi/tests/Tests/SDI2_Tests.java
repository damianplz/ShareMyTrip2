package com.sdi.tests.Tests;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.Factory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.google.common.util.concurrent.Service;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.persistence.TripDao;
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
	private String baseUrl;

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
		
		
		Factories.persistence.newApplicationDao().deleteTestApplications();
		Factories.persistence.newSeatDao().deleteTestSeats();
		Factories.persistence.newTripDao().deleteTestTrips();
		
		
		
		//Para el uso de los test con plugin
		baseUrl = "http://localhost:8280/sdi2-22/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

	// 3. [IdVal] Identificación de Usuario registrado con datos válidos.
	@Test
	public void t03_IdVal() {
		driver.get(baseUrl );
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		new PO_LoginForm().rellenaFormulario(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "menuPerfil", 10);
		SeleniumUtils.textoPresentePagina(driver, "user1");
	}

	// 4. [IdInval] Identificación de usuario registrado con datos inválidos.
	@Test
	public void t04_IdInval() {
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		new PO_LoginForm().rellenaFormulario(driver, "user1", "pepe");
		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 5);
		SeleniumUtils.textoPresentePagina(driver,
				"Usuario o password erróneos");
		
	}

	// 5. [AccInval] Intento de acceso con URL desde un usuario no público
	// (no // identificado). Intento de acceso a vistas de acceso privado. -
	// PASA
	@Test
	public void t05_AccInval() {
		String inval = selectedServer + "/restricted/perfil.xhtml";
		driver.get(inval);
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		SeleniumUtils.textoNoPresentePagina(driver,
				"Perfil");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		SeleniumUtils.textoPresentePagina(driver, "Formulario de login");
	}
	// 6. [RegViajeVal] Registro de un viaje nuevo con datos válidos.
	@Test
	public void t06_RegViajeVal() throws InterruptedException {
		
		TripDao tripDao = Factories.persistence.newTripDao();
		
		int numberOfTrips=tripDao.findAll().size();
		 
	    driver.get(baseUrl );
	    driver.findElement(By.id("form-cuerpo:login")).clear();
	    driver.findElement(By.id("form-cuerpo:login")).sendKeys("user1");
	    driver.findElement(By.id("form-cuerpo:password")).clear();
	    driver.findElement(By.id("form-cuerpo:password")).sendKeys("user1");
	    driver.findElement(By.id("form-cuerpo:btnLogin")).click();
	    
	    
	    //Rellena el formulario con los datos
	    SeleniumUtils.EsperaCargaPagina(driver, "id", "form-cuerpo:menuPerfil", 2);
	    driver.findElement(By.xpath("//a[@id='form-cuerpo:publicarViaje']/span")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleS")).sendKeys("test");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaCiudadesD']/div[3]/table/tbody/tr[4]/td")).click();
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaComunidadesD']/div[3]/table/tbody/tr[6]/td")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisS")).sendKeys("test");
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpS")).sendKeys("12345");
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaS")).sendKeys("30-abr-2016 15:27:36");
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleL")).sendKeys("test");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaCiudadesS']/div[3]/table/tbody/tr[5]/td")).click();
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaComunidadesA']/div[3]/table/tbody/tr[3]/td")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisL")).sendKeys("test");
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpL")).sendKeys("54321");
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaL")).sendKeys("30-abr-2016 15:27:36");
	    driver.findElement(By.id("form-cuerpo:crearviaje:limite")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:limite")).sendKeys("29-abr-2016 15:27:36");
	    driver.findElement(By.id("form-cuerpo:crearviaje:coste")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:coste")).sendKeys("20");
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxM")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxM")).sendKeys("4");
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxA")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxA")).sendKeys("4");
	    driver.findElement(By.id("form-cuerpo:crearviaje:comment")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:comment")).sendKeys("testingTest");
	    driver.findElement(By.id("form-cuerpo:crearviaje:btnCrearViaje")).click();
	    
	    
	    Thread.sleep(1000);
	    
	    //We check that the project has not been suscessfully created
	    Assert.assertTrue(numberOfTrips<tripDao.findAll().size());
	    //Factories.persistence.newTripDao().delete(trip.getId());
	    
	}

	// 7. [RegViajeInVal] Registro de un viaje nuevo con datos inválidos.
	@Test
	public void t07_RegViajeInVal() throws InterruptedException {
		
		TripDao tripDao = Factories.persistence.newTripDao();
		
		int numberOfTrips=tripDao.findAll().size();
		 
	    driver.get(baseUrl );
	    driver.findElement(By.id("form-cuerpo:login")).clear();
	    driver.findElement(By.id("form-cuerpo:login")).sendKeys("user1");
	    driver.findElement(By.id("form-cuerpo:password")).clear();
	    driver.findElement(By.id("form-cuerpo:password")).sendKeys("user1");
	    driver.findElement(By.id("form-cuerpo:btnLogin")).click();
	    
	    
	    //Rellena el formulario con los datos
	    SeleniumUtils.EsperaCargaPagina(driver, "id", "form-cuerpo:menuPerfil", 2);
	    driver.findElement(By.xpath("//a[@id='form-cuerpo:publicarViaje']/span")).click();
	    
	    driver.findElement(By.id("form-cuerpo:crearviaje:btnCrearViaje")).click();
	    
	    SeleniumUtils.EsperaCargaPaginaTiempo(driver, 3);
	    
	    SeleniumUtils.textoPresentePagina(driver, "Debe introducir un código postal");
	    SeleniumUtils.textoPresentePagina(driver, "Debe introducir un pais de llegada");
	    SeleniumUtils.textoPresentePagina(driver, "Campo requerido");
	    SeleniumUtils.textoPresentePagina(driver, "Debe introducir un país de partida");
	    SeleniumUtils.textoPresentePagina(driver, "Debe introducir una calle de partida");
	    
	    
	    
	    
	    
	    Thread.sleep(1000);
	    
	    //We check that the project has been suscessfully created
	    Assert.assertTrue(numberOfTrips==tripDao.findAll().size());
	    //Factories.persistence.newTripDao().delete(trip.getId());
		
	}

	// 8. [EditViajeVal] Edición de viaje existente con datos válidos.
	@Test
	public void t08_EditViajeVal() throws Exception {
		// Editamos el viaje de Rue de Percebe cambiando
		// el nombre de la ciudad
		String provinciaSalida="Madrid",provinciaLlegada="Albacete";
		int dia=30;
		login();
		crearViaje(provinciaSalida, provinciaLlegada,dia);
		
		//click editar
	    driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
	    driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaSalida+"\") and contains(.,\"Promotor\") and contains(.,\"OPEN\")]/td[11]")).click();
	    
	    //editarViaje
	    driver.findElement(By.id("form-cuerpo:editarviaje:calleSE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:calleSE")).sendKeys("testEdicion");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:editarviaje:listaCiudadesDE']/div[3]/table/tbody//td[text()=\""+provinciaLlegada+"\"]")).click();
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:editarviaje:listaComunidadesDE']/div[3]/table/tbody/tr[18]/td")).click();
	    driver.findElement(By.id("form-cuerpo:editarviaje:paisSE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:paisSE")).sendKeys("testEdicion");
	    driver.findElement(By.id("form-cuerpo:editarviaje:cpSE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:cpSE")).sendKeys("12345");
	    driver.findElement(By.id("form-cuerpo:editarviaje:fechaSE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:fechaSE")).sendKeys("30-abr-2016 11:27:09");
	    driver.findElement(By.id("form-cuerpo:editarviaje:calleLE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:calleLE")).sendKeys("testEdicion");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:editarviaje:listaCiudadesSE']/div[3]/table/tbody//td[text()=\""+provinciaSalida+"\"]")).click();
	    //driver.findElement(By.id("form-cuerpo:editarviaje:listaCiudadesSE_input")).click();
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:editarviaje:listaComunidadesAE']/div[3]/table/tbody/tr[18]/td")).click();
	    driver.findElement(By.id("form-cuerpo:editarviaje:paisLE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:paisLE")).sendKeys("testEdicion");
	    driver.findElement(By.id("form-cuerpo:editarviaje:cpLE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:cpLE")).sendKeys("54321");
	    driver.findElement(By.id("form-cuerpo:editarviaje:fechaLE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:fechaLE")).sendKeys("30-abr-2016 11:27:09");
	    driver.findElement(By.id("form-cuerpo:editarviaje:limiteE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:limiteE")).sendKeys("29-abr-2016 11:27:09");
	    driver.findElement(By.id("form-cuerpo:editarviaje:costeE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:costeE")).sendKeys("5");
	    driver.findElement(By.id("form-cuerpo:editarviaje:commentE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:commentE")).sendKeys("test");
	    driver.findElement(By.id("form-cuerpo:editarviaje:paxME")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:paxME")).sendKeys("4");
	    driver.findElement(By.id("form-cuerpo:editarviaje:paxAE")).clear();
	    driver.findElement(By.id("form-cuerpo:editarviaje:paxAE")).sendKeys("4");
	    driver.findElement(By.id("form-cuerpo:editarviaje:actE")).click();
	    
	    //Comprobacion de los cambios
	    driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
	    assertEquals(provinciaLlegada, driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaLlegada+"\") and contains(.,\"Promotor\")]/td[1]")).getText());
	    assertEquals(provinciaSalida, driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaSalida+"\") and contains(.,\"Promotor\")]/td[2]")).getText());
	}

	// 9. [EditViajeInVal] Edición de viaje existente con datos inválidos.
	@Test
	public void t09_EditViajeInVal() throws Exception {
		
		// Editamos el viaje de Rue de Percebe cambiando
				// el nombre de la ciudad
				String provinciaSalida="Madrid",provinciaLlegada="Albacete";
				int dia=30;
				login();
				crearViaje(provinciaSalida, provinciaLlegada,dia);
				
				//click editar
			    driver.findElement(By.
			    		xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
			    driver.findElement(By.
			    		xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaSalida+"\") and contains(.,\"Promotor\") and contains(.,\"OPEN\")]/td[11]")).click();
		
			    
			    
			    driver.findElement(By.id("form-cuerpo:editarviaje:calleSE")).clear();
			   
			   
			   
			    driver.findElement(By.id("form-cuerpo:editarviaje:paisSE")).clear();
			   
			    driver.findElement(By.id("form-cuerpo:editarviaje:cpSE")).clear();
			   
			    driver.findElement(By.id("form-cuerpo:editarviaje:fechaSE")).clear();
			   
			    driver.findElement(By.id("form-cuerpo:editarviaje:calleLE")).clear();
			   
			   
			   
			    driver.findElement(By.id("form-cuerpo:editarviaje:paisLE")).clear();
			   
			    driver.findElement(By.id("form-cuerpo:editarviaje:cpLE")).clear();
			    
			    driver.findElement(By.id("form-cuerpo:editarviaje:fechaLE")).clear();
			    
			    driver.findElement(By.id("form-cuerpo:editarviaje:limiteE")).clear();
			    
			    driver.findElement(By.id("form-cuerpo:editarviaje:costeE")).clear();
			    
			    driver.findElement(By.id("form-cuerpo:editarviaje:commentE")).clear();
			    
			    driver.findElement(By.id("form-cuerpo:editarviaje:paxME")).clear();
			    
			    driver.findElement(By.id("form-cuerpo:editarviaje:paxAE")).clear();
			    
			    driver.findElement(By.xpath(".//*[@id='form-cuerpo:editarviaje:actE']")).click();
			    
			    SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);			    
			    
			    
			    
	}

	// 10. [CancelViajeVal] Cancelación de un viaje existente por un
	// promotor.
	@Test
	public void t10_CancelViajeVal() throws Exception {
		String provinciaDeSalida="Badajoz";
		String provinciaDeLlegada="Burgos";
		int diaViaje=25;
		login();
		crearViaje(provinciaDeSalida, provinciaDeLlegada, diaViaje);
		
		driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		driver.findElement(By.xpath("//th[@id='form-cuerpo:formularioPropios:tablaMisViajes:btnTodos']/div/div[2]/span")).click();
		driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
				+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[12]//span")).click();
		driver.findElement(By.id("form-cuerpo:formularioPropios:tablaMisViajes:botonCancelarViajes")).click();
		
		//Sino recarga la pagina tras pulsar el boton no funciona
		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
		assertEquals("CANCELLED", driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
				+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[7]")).getText());
		
	}

	// 11. [CancelMulViajeVal] Cancelación de múltiples viajes existentes por un
	// promotor.
	@Test
	public void t11_CancelMulViajeVal() throws Exception {
		String provinciaDeSalida="Badajoz";
		String provinciaDeLlegada="Burgos";
		int diaViaje=25;
		login();
		crearViaje(provinciaDeSalida, provinciaDeLlegada, diaViaje);
		
		String provinciaDeSalida2="Barcelona";
		String provinciaDeLlegada2="Valencia";
		int diaViaje2=26;
		crearViaje(provinciaDeSalida2, provinciaDeLlegada2, diaViaje2);
		
		driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		driver.findElement(By.xpath("//th[@id='form-cuerpo:formularioPropios:tablaMisViajes:btnTodos']/div/div[2]/span")).click();
		driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
				+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[12]//span")).click();
		driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida2+"\")"
				+ "and contains(.,\""+provinciaDeLlegada2+"\")  and contains(.,\"Promotor\")]/td[12]//span")).click();
		driver.findElement(By.id("form-cuerpo:formularioPropios:tablaMisViajes:botonCancelarViajes")).click();
		
		//Sino recarga la pagina tras pulsar el boton no funciona
		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
		assertEquals("CANCELLED", driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
				+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[7]")).getText());
		assertEquals("CANCELLED", driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida2+"\")"
				+ "and contains(.,\""+provinciaDeLlegada2+"\")  and contains(.,\"Promotor\")]/td[7]")).getText());
	}

	// 12. [Ins1ViajeAceptVal] Inscribir en un viaje un solo usuario y ser
	// admitido por el promotor.
	@Test
	public void t12_Ins1ViajeAceptVal() throws Exception {
		
		String provinciaDeSalida="Cuenca";
		String provinciaDeLlegada="Gerona";
		int diaViaje=27;
		//Se crea el viaje al cual se le va a solicitar
		 login("user3","user3");
		 crearViaje(provinciaDeSalida,provinciaDeLlegada, diaViaje);
		 cerrarSession();
		 
		 login("user1", "user1");
		
		 driver.findElement(By.id("form-cuerpo:verTodos")).click();
		 driver.findElement(By.xpath
				 (".//*[@id='form-cuerpo:formTodos:tablaViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\") and contains(.,\""+provinciaDeLlegada+"\")]/td[8]/button")).click();
		 cerrarSession();
		 login("user3","user3");
		 driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		 driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
					+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[9]/button")).click();
		 //306 es el id del user1
		 driver.findElement(By.xpath(".//*[@id='form-cuerpo:formSolicitudes:tablaSolicitudes_data']/tr[contains(.,\"306\")]/td[3]/button")).click();
		 SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		 SeleniumUtils.textoPresentePagina(driver, "ACCEPTED");
		
	}

	// 13. [Ins2ViajeAceptVal] Inscribir en un viaje dos usuarios y ser
	// admitidos los dos por el promotor.
	@Test
	public void t13_Ins2ViajeAceptVal() throws Exception {
		
		String provinciaDeSalida="Cuenca";
		String provinciaDeLlegada="Gerona";
		int diaViaje=27;
		//Se crea el viaje al cual se le va a solicitar
		 login("user3","user3");
		 crearViaje(provinciaDeSalida,provinciaDeLlegada, diaViaje);
		 cerrarSession();
		 
		 //Peticion de solicitud 1
		 login("user1", "user1");
		
		 driver.findElement(By.id("form-cuerpo:verTodos")).click();
		 driver.findElement(By.xpath
				 (".//*[@id='form-cuerpo:formTodos:tablaViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\") and contains(.,\""+provinciaDeLlegada+"\")]/td[8]/button")).click();
		 cerrarSession();
		 
		 //Peticion de solicitud 2
		 login("user2", "user2");
			
		 driver.findElement(By.id("form-cuerpo:verTodos")).click();
		 driver.findElement(By.xpath
				 (".//*[@id='form-cuerpo:formTodos:tablaViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\") and contains(.,\""+provinciaDeLlegada+"\")]/td[8]/button")).click();
		 cerrarSession();
		 
		 //Se aceptan las peticiones
		 login("user3","user3");
		 driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		 driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
					+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[9]/button")).click();
		 //306 es el id del user1
		 driver.findElement(By.xpath(".//*[@id='form-cuerpo:formSolicitudes:tablaSolicitudes_data']/tr[contains(.,\"306\")]/td[3]/button")).click();
		 SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		 SeleniumUtils.textoPresentePagina(driver, "ACCEPTED");
		 
		 //307 id user2
		 driver.findElement(By.xpath(".//*[@id='form-cuerpo:formSolicitudes:tablaSolicitudes_data']/tr[contains(.,\"307\")]/td[3]/button")).click();
		 SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		 SeleniumUtils.textoPresentePagina(driver, "ACCEPTED");


	}

	// 14. [Ins3ViajeAceptInval] Inscribir en un viaje (2 plazas máximo)
	// dos usuarios y ser admitidos los dos y que un tercero intente inscribirse
	// en ese mismo viaje pero ya no pueda por falta de plazas.
	@Test
	public void t14_Ins3ViajeAceptInval() throws Exception {
		String provinciaDeSalida="Huesca";
		String provinciaDeLlegada="Mallorca";
		int diaViaje=27;
		int plazasDisponibles=3;
		//Se crea el viaje al cual se le va a solicitar
		 login("user3","user3");
		 crearViaje(provinciaDeSalida,provinciaDeLlegada, diaViaje,plazasDisponibles);
		 cerrarSession();
		 
		 //Peticion de solicitud 1
		 login("user1", "user1");
		
		 driver.findElement(By.id("form-cuerpo:verTodos")).click();
		 driver.findElement(By.xpath
				 (".//*[@id='form-cuerpo:formTodos:tablaViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\") and contains(.,\""+provinciaDeLlegada+"\")]/td[8]/button")).click();
		 cerrarSession();
		 
		 //Peticion de solicitud 2
		 login("user2", "user2");
			
		 driver.findElement(By.id("form-cuerpo:verTodos")).click();
		 driver.findElement(By.xpath
				 (".//*[@id='form-cuerpo:formTodos:tablaViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\") and contains(.,\""+provinciaDeLlegada+"\")]/td[8]/button")).click();
		 cerrarSession();
		 
		 //Se aceptan las peticiones
		 login("user3","user3");
		 driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		 driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
					+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[9]/button")).click();
		 //306 es el id del user1
		 driver.findElement(By.xpath(".//*[@id='form-cuerpo:formSolicitudes:tablaSolicitudes_data']/tr[contains(.,\"306\")]/td[3]/button")).click();
		 SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		 SeleniumUtils.textoPresentePagina(driver, "ACCEPTED");
		 
		 //307 id user2
		 driver.findElement(By.xpath(".//*[@id='form-cuerpo:formSolicitudes:tablaSolicitudes_data']/tr[contains(.,\"307\")]/td[3]/button")).click();
		 SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		 SeleniumUtils.textoPresentePagina(driver, "ACCEPTED");
		 cerrarSession();
		 
		//Peticion de solicitud 3
		login("user4", "user4");
				
		driver.findElement(By.id("form-cuerpo:verTodos")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "Cuenca");
		SeleniumUtils.textoNoPresentePagina(driver, "Gerona");
		

	}

	// 15. [CancelNoPromotorVal] Un usuario no promotor Cancela plaza.
	@Test
	public void t15_CancelNoPromotorVal() throws Exception {
		String provinciaDeSalida="Gerona";
		String provinciaDeLlegada="Granada";
		int diaViaje=27;
		int plazasDisponibles=3;
		//Se crea el viaje al cual se le va a solicitar
		 login("user3","user3");
		 crearViaje(provinciaDeSalida,provinciaDeLlegada, diaViaje,plazasDisponibles);
		 cerrarSession();
		 
		 //Peticion de solicitud 1
		 login("user1", "user1");
		
		 driver.findElement(By.id("form-cuerpo:verTodos")).click();
		 driver.findElement(By.xpath
				 (".//*[@id='form-cuerpo:formTodos:tablaViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\") and contains(.,\""+provinciaDeLlegada+"\")]/td[8]/button")).click();
		 //cerrarSession();
		 
		 driver.findElement(By.xpath(".//*[@id='form-pie:linkInicioPerfil']")).click();
		 
		 //login("user1", "user1");
		 driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		 driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
					+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Pasajero\")]/td[10]/button")).click();
		 driver.findElement(By.id("form-cuerpo:formularioPropios:tablaMisViajes:botonCancelarViajes")).click();
		
		 
		 assertEquals("CANCELLED", driver.findElement(By.
				 xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
					+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Pasajero\")]/td[7]")) );
		 

	}

	// 16. [Rech1ViajeVal] Inscribir en un viaje un usuario que será
	// admitido y después rechazarlo por el promotor.
	@Test
	public void t16_Rech1ViajeVal() throws Exception {
		
		String provinciaDeSalida="Cuenca";
		String provinciaDeLlegada="Gerona";
		int diaViaje=27;
		//Se crea el viaje al cual se le va a solicitar
		 login("user3","user3");
		 crearViaje(provinciaDeSalida,provinciaDeLlegada, diaViaje);
		 cerrarSession();
		 
		 login("user1", "user1");
		
		 driver.findElement(By.id("form-cuerpo:verTodos")).click();
		 driver.findElement(By.xpath
				 (".//*[@id='form-cuerpo:formTodos:tablaViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\") and contains(.,\""+provinciaDeLlegada+"\")]/td[8]/button")).click();
		 cerrarSession();
		 login("user3","user3");
		 driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		 driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
					+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[9]/button")).click();
		 //306 es el id del user1
		 driver.findElement(By.xpath(".//*[@id='form-cuerpo:formSolicitudes:tablaSolicitudes_data']/tr[contains(.,\"306\")]/td[3]/button")).click();
		 SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		 SeleniumUtils.textoPresentePagina(driver, "ACCEPTED");
		 
		 driver.findElement(By.xpath(".//*[@id='form-cuerpo:formSolicitudes:tablaSolicitudes_data']/tr[contains(.,\"306\")]/td[4]/button")).click();
		 SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		 driver.findElement(By.xpath(".//*[@id='form-pie:linkInicioPerfil']")).click();
		 driver.findElement(By.xpath("//a[@id='form-cuerpo:verPropios']/span")).click();
		 driver.findElement(By.xpath("//tbody[@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,\""+provinciaDeSalida+"\")"
					+ "and contains(.,\""+provinciaDeLlegada+"\")  and contains(.,\"Promotor\")]/td[9]/button")).click();
		 SeleniumUtils.textoNoPresentePagina(driver, "306");
		 
		 
		
		
	}

	// 17. [i18N1] Cambio del idioma por defecto a un segundo idioma.
	// (Probar algunas vistas)
	@Test
	public void t17_i18N1() {
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		SeleniumUtils.textoPresentePagina(driver, "Formulario de login");
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:subMenuIdioma",
				"form-cabecera:linkingles");
		SeleniumUtils.EsperaCargaPaginaTiempo(driver,10);
		SeleniumUtils.textoPresentePagina(driver, "Login form");
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:misubmenu1",
				"form-cabecera:linkCrearUsuario");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "registro", 10);
		SeleniumUtils.textoPresentePagina(driver, "User registration");
	}

	// 18. [i18N2] Cambio del idioma por defecto a un segundo idioma y
	// vuelta al idioma por defecto. (Probar algunas vistas)
	@Test
	public void t18_i18N2() {
		t17_i18N1();
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:subMenuIdioma",
				"form-cabecera:linkespa");
		SeleniumUtils.EsperaCargaPaginaTiempo(driver,5);
		SeleniumUtils.textoPresentePagina(driver, "Registro de usuario");
		
		SeleniumUtils.EsperaCargaPaginaTiempo(driver,5);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:subMenuIdioma",
				"form-cabecera:linkingles");
		SeleniumUtils.EsperaCargaPaginaTiempo(driver,2);
		SeleniumUtils.textoPresentePagina(driver, "User registration");
		
		SeleniumUtils.clickOnButton(driver, "form-pie:linkInicioIndex");
		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 10);
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:subMenuIdioma",
				"form-cabecera:linkespa");
		SeleniumUtils.EsperaCargaPaginaTiempo(driver,10);
		SeleniumUtils.textoPresentePagina(driver, "Registro de usuario");
			
	}

	
//	 // 19. [OpFiltrado] Prueba para el filtrado opcional.
//	 @Test public void t19_OpFiltrado() throws InterruptedException {
//	 
//		//Vamos a la vista de filtrado-ordenacion-paginacion
//		 	login("user1", "user1");
//		 	SeleniumUtils.clickOnButton(driver, "form-cuerpo:verTodos");
//		 
//			//Esperamos que aparezca el elemento filter			
//			List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "ui-column-filter", 2); 
//			
//			//Pinchamos en segundo filtro (el de año)
//			elementos.get(1).click();
//
//			//Escribimos en el campo tecla a tecla. Eso lo hace builder.sendKeys. (No vale el sendKeys de WebElement)
//			Actions builder = new Actions(driver);	    
//			builder.sendKeys("1984").perform(); // moveToElement(hoverElement).perform();
//
//			//Esperar porque se recargue la página el resultado del filtrado
//			Thread.sleep(1000);
//
//			//COmo los datos son aleatorios es imposible saber lo que
//			//vamos a encontrarnos pero podríamos...
//			//Contar el número de filas resultados del filtrado (POr ejemplo  4)
//			//tr[contains(@class, 'datatable-selectable')]
//			//By busqueda = By.xpath("//tr[contains(@class, 'datatable-selectable')]");
//			//campos = driver.findElements(busqueda);			
//			//assertTrue("Filtrado incorrecto", campos.size()== 4);	
//		 
//	 }
	  // 20. [OpOrden] Prueba para la ordenación opcional.
//	 /* 
//	 * @Test public void t20_OpOrden() {
//	 * 
//	 * } // 21. [OpPag] Prueba para la paginación opcional.
//	 * 
//	 * @Test public void t21_OpPag() {
//	 * 
//	 * } 
//	 */
	
	// 2e. [AddTrip] Añade un viaje.
	 @Test public void addTrip() throws Exception {
		 //Query para eliminar test
		 //DELETE FROM "PUBLIC"."TTRIPS" WHERE COMMENTS LIKE '%test%'
		 //@id='form-cuerpo:formularioPropios:tablaMisViajes_data']/tr[contains(.,"Oviedo")]/td[11] 
		 //para editar 
		 login();
		 crearViaje("Toledo","Avila",5);
	}
	 
	private void crearViaje(String provinciaSalida,String provinciaLlegada,int dia) throws Exception {
	    
	    driver.findElement(By.xpath("//a[@id='form-cuerpo:publicarViaje']/span")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleS")).sendKeys("test1");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaCiudadesD']/div[3]/table/tbody//td[text()=\""+provinciaLlegada+"\"]")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:listaComunidadesD_filter")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:listaComunidadesD_filter")).sendKeys("ast");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaComunidadesD']/div[3]/table/tbody/tr[18]/td")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisS")).sendKeys("test1");
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpS")).sendKeys("12345");
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaS")).sendKeys(dia+"-may-2016 20:58:35");
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleL")).sendKeys("test1");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaCiudadesS']/div[3]/table/tbody//td[text()=\""+provinciaSalida+"\"]")).click();
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaComunidadesA']/div[3]/table/tbody/tr[2]/td")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisL")).sendKeys("test1");
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpL")).sendKeys("54321");
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaL")).sendKeys(dia+"-may-2016 20:58:35");
	    driver.findElement(By.id("form-cuerpo:crearviaje:limite")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:limite")).sendKeys((dia-1)+"-may-2016 20:58:35");
	    driver.findElement(By.id("form-cuerpo:crearviaje:coste")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:coste")).sendKeys("30");
	    driver.findElement(By.id("form-cuerpo:crearviaje:comment")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:comment")).sendKeys("test");
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxM")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxM")).sendKeys("4");
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxA")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxA")).sendKeys("4");
	    driver.findElement(By.id("form-cuerpo:crearviaje:btnCrearViaje")).click();
	  }
	
	private void crearViaje(String provinciaSalida,String provinciaLlegada,int dia,int plazas) throws Exception {
	    
	    driver.findElement(By.xpath("//a[@id='form-cuerpo:publicarViaje']/span")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleS")).sendKeys("test1");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaCiudadesD']/div[3]/table/tbody//td[text()=\""+provinciaLlegada+"\"]")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:listaComunidadesD_filter")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:listaComunidadesD_filter")).sendKeys("ast");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaComunidadesD']/div[3]/table/tbody/tr[18]/td")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisS")).sendKeys("test1");
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpS")).sendKeys("12345");
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaS")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaS")).sendKeys(dia+"-may-2016 20:58:35");
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:calleL")).sendKeys("test1");
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaCiudadesS']/div[3]/table/tbody//td[text()=\""+provinciaSalida+"\"]")).click();
	    driver.findElement(By.xpath("//div[@id='form-cuerpo:crearviaje:listaComunidadesA']/div[3]/table/tbody/tr[2]/td")).click();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paisL")).sendKeys("test1");
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:cpL")).sendKeys("54321");
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaL")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:fechaL")).sendKeys(dia+"-may-2016 20:58:35");
	    driver.findElement(By.id("form-cuerpo:crearviaje:limite")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:limite")).sendKeys((dia-1)+"-may-2016 20:58:35");
	    driver.findElement(By.id("form-cuerpo:crearviaje:coste")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:coste")).sendKeys("30");
	    driver.findElement(By.id("form-cuerpo:crearviaje:comment")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:comment")).sendKeys("test");
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxM")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxM")).sendKeys(String.valueOf(plazas));
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxA")).clear();
	    driver.findElement(By.id("form-cuerpo:crearviaje:paxA")).sendKeys(String.valueOf(plazas));
	    driver.findElement(By.id("form-cuerpo:crearviaje:btnCrearViaje")).click();
	  }
	 
	private void login(String user,String password) {
		driver.get(baseUrl );
	    driver.findElement(By.id("form-cuerpo:login")).clear();
	    driver.findElement(By.id("form-cuerpo:login")).sendKeys(user);
	    driver.findElement(By.id("form-cuerpo:password")).clear();
	    driver.findElement(By.id("form-cuerpo:password")).sendKeys(password);
	    driver.findElement(By.id("form-cuerpo:btnLogin")).click();
	}
	
	private void login() {
		driver.get(baseUrl );
	    driver.findElement(By.id("form-cuerpo:login")).clear();
	    driver.findElement(By.id("form-cuerpo:login")).sendKeys("user1");
	    driver.findElement(By.id("form-cuerpo:password")).clear();
	    driver.findElement(By.id("form-cuerpo:password")).sendKeys("user1");
	    driver.findElement(By.id("form-cuerpo:btnLogin")).click();
	}
	
	private void cerrarSession(){
		SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:misubmenu1",
				"form-cabecera:linkCerrarSesion");
		//driver.findElement(By.xpath("//a[@id='form-cabecera:linkCerrarSesion']/span")).click();
	    SeleniumUtils.EsperaCargaPaginaTiempo(driver, 2);
		assertEquals("Formulario de login", driver.findElement(By.cssSelector("th.title")).getText());
		
	   
	}

}