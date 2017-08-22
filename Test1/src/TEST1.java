import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TEST1 {
	
			WebDriver driver;
			
	@Before
	public void setUp() {
		
		//CHROME
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ariel\\Desktop\\SeleniumServers\\chromedriver.exe");
		driver= new ChromeDriver();
		
		//INGRESO A GOOGLE.COM.AR, REALIZO LA BUSQUEDA
		String url="https://www.google.com.ar";
		String busqueda="Salesforce Argentina";

		driver.get(url);
		driver.findElement(By.id("lst-ib")).sendKeys(busqueda);
		driver.findElement(By.id("hplogo")).click();
		driver.findElement(By.xpath("//*[@id='tsf']/div[2]/div[3]/center/input[1]")).click();
	}
	
	
	@Test
	public void test01(){
		System.out.println("Indicar por consola qué páginas aparecen como ANUNCION (pagas):\n\n");
		
		//BUSCO EL WE QUE CONTENGA TODOS LOS RESULTADOS DE BUSCA

		WebElement body=driver.findElement(By.id("center_col"));
		
		//LA LOGICA DE LA PAGINA ES: DIVIDO LA PANTALLA EN PARTES, DONDE LA PARTE INFERIOR Y SUPERIOR DE LA MISMA CONTIENEN LOS ANUNCIOS
		//GUARDO LOS ELEMENTOS DE LA PARTE SUPERIOR E INFERIOR DE LA PAGINA DONDE ESTAN LOS ANUNCIOS
		WebElement header=body.findElement(By.id("taw"));
		WebElement pie=body.findElement(By.id("bottomads"));
		
		//BUSCO LOS ELEMENTOS QUE SE MUESTRAN LISTADOS EN LA PAGINA, DENTRO DE LOS MISMOS HAY WE QUE CONTIENE LAS URL
		//ACLARACION: TUVE QUE REALIZAR ESTE PASO EXTRA DADO QUE AL BUSCAR LAS URL DIRECTAMENTE, ME ENCONTRE CON URLS QUE NO SE VEN.
	    //ESTO TAMBIEN ME DA MAS CONTROL SOBRE LOS WE, YA QUE OBTENGO LA LISTA EXACTA DE ELEMENTOS QUE VEO POR PANTALLA
		
		List<WebElement> headerList=header.findElements(By.className("ads-ad"));
		List<WebElement> pieList=pie.findElements(By.className("ads-ad"));
			
		//GENERO UNA LISTA DONDE GUARDARE TODAS LAS URL
		List<String> listaAnuncios=new ArrayList<String>();
		
		//RECORRO LAS LISTAS
		for (WebElement e : headerList) {
			//BUSCO EL WE QUE CONTIENE LA URL, SI NECESITO OTRO DATO QUE NO SEA LA URL SOLO DEBERIA MODIFICAR ESTA PARTE DEL CODIGO
			e=e.findElement(By.className("_WGk"));
			//AGREGO EL WE A LA LISTA
			listaAnuncios.add(e.getText());
			
		}
				
		
		for (WebElement e : pieList) {
			e=e.findElement(By.className("_WGk"));
			listaAnuncios.add(e.getText());
		}
				
		//RECORRO LA LISTA Y MUESTRO POR PANTALLA EL RESULTADO
		System.out.println("PAGINAS ANUNCIOS:");
		for (String s : listaAnuncios) {
			System.out.println(s+"\n");
		}
		
	}
	
	
	@Test 
	public void test02(){
		
		System.out.println("b) Indicar por consola URLs argentinas (contienen .ar aparecen en las primeras 5 páginas de resultados):\n\n");
  		System.out.println("URLs argentinas en las primeras 5 páginas de resultados:\n");
  		
  		//SE APLICA LA MISMA LOGICA QUE EN EL TEST01, CON LA DIFERENCIA DE QUE TAMBIEN SE BUSCA EL ELEMENTO CUERPO QUE CONTIENE
  		//LOS RESULTADOS DE BUSQUEDA QUE NO SON ANUNCIOS
  		
		for (int i = 0; i < 4; i++) {

		WebElement body=driver.findElement(By.id("center_col"));
		
		
		WebElement header=body.findElement(By.id("taw"));
		WebElement cuerpo=body.findElement(By.id("res"));
		WebElement pie=body.findElement(By.id("bottomads"));
		
		
		List<WebElement> headerList=header.findElements(By.className("ads-ad"));
		List<WebElement> cuerpoList=cuerpo.findElements(By.className("g"));
		List<WebElement> pieList=pie.findElements(By.className("ads-ad"));
			

		List<String> listaPagArgentina=new ArrayList<String>();
		
		
		for (WebElement e : headerList) {
			e=e.findElement(By.className("_WGk"));
			if (e.getText().contains(".ar")) {			//VALIDA QUE EL WE NO CONTENGA LA PALABRA .AR
				listaPagArgentina.add(e.getText());		//SI ES TRUE, LO AGREGA A LA LISTA
			}
			
		}
					
		for (WebElement e : cuerpoList) {
			e=e.findElement(By.className("_Rm"));
			if (e.getText().contains(".ar")) {
				listaPagArgentina.add(e.getText());
			}
			
		}
		for (WebElement e : pieList) {
			e=e.findElement(By.className("_WGk"));
			if (e.getText().contains(".ar")) {
				listaPagArgentina.add(e.getText());
			}
		}
				
		
		for (String s : listaPagArgentina) {			//RECORRE LA LISTA Y MUESTRA EL RESULTADO
			System.out.println(s+"\n");
		}
		driver.findElement(By.id("pnnext")).click();	//AL FIN DE CADA INTERACCION BUSCA EL BOTON SIGUIENTE Y HACE CLIC

		}

	}
		

	
	@Test
	public void test03(){
		
		System.out.println("c) Indicar por consola si Xappia.com aparece, como anuncio u orgánico, en qué página y en qué posición (puede ser que aparezca más de una vez):\n\n");

		//ESTE EJERCICIO SIGUE CON LA MISMA LOGICA QUE LOS ANTERIOR CON LA DIFERENCIA DE QUE TAMBIEN SE MARCA EL NUMERO DE PAGINA Y EL POSICIONAMIENTO

		List<String>listaXappia=new ArrayList<String>();	
		
		//LA I ME VA A INDICAR EL NUMERO DE PAGINA SUMANDOLE 1
		for (int i = 0; i < 4; i++) {
			
		//EL CONTADOR ME VA A MOSTRAR LA POSICION DE CADA ELEMENTO, SE RESETEA EN CADA ITERACION			
		//SE VA AUMENTANDO CADA VEZ QUE ITERA UNA ELEMENTO DE CUALQUIER LISTA
			
		int contador=0; 
		WebElement body=driver.findElement(By.id("center_col"));
		
		
		WebElement header=body.findElement(By.id("taw"));
		WebElement cuerpo=body.findElement(By.id("res"));
		WebElement pie=body.findElement(By.id("bottomads"));
		
		
		List<WebElement> headerList=header.findElements(By.className("ads-ad"));
		List<WebElement> cuerpoList=cuerpo.findElements(By.className("g"));
		List<WebElement> pieList=pie.findElements(By.className("ads-ad"));
			

		String palabra="xappia.com";
		
		for (WebElement e : headerList) {
			contador++;
			e=e.findElement(By.className("_WGk"));

			if (e.getText().contains(palabra)){//SI CONTIENE LA PALABRA XAPPIA LO AGREGA
				listaXappia.add("Se muestra como anuncio: "+" PAGINA:"+(i+1)+" Posicion:"+contador);
				
			}
		}
				
		for (WebElement e : cuerpoList) {
			contador++;
			e=e.findElement(By.className("_Rm"));

			if (e.getText().contains(palabra)){
				listaXappia.add("Se muestra como anuncio: "+" PAGINA:"+(i+1)+" Posicion:"+contador);
			}
		}
		
		for (WebElement e : pieList) {
			contador++;
			e=e.findElement(By.className("_WGk"));

			if (e.getText().contains(palabra)){
				listaXappia.add("Se muestra como anuncio: "+" PAGINA:"+(i+1)+" Posicion:"+contador);
			}
		}
				
		driver.findElement(By.id("pnnext")).click();
		
		}
		
		for (String e : listaXappia) {
			System.out.println(e);
		}
	}
	
	@After	
	public void tearDown(){
		driver.close();
	}

}
