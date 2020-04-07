package mx.tec.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.WebClient;

@SpringBootTest
class TestHtmlApplicationTests {
	private WebClient webClient;
	
	@BeforeEach
	public void init() throws Exception{
		webClient = new WebClient();
	}
	
	@AfterEach
	public void close() throws Exception{
		webClient.close();
	}

	@Test
	public void givenAClient_whenEnteringAutomationPractice_thenPageTitleIsCorrect() throws Exception{
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
		
		assertEquals("My Store", page.getTitleText());
	}
	
	@Test
	public void givenAClient_whenEnteringLoginCredentials_thenAccountPageIsDisplayed() throws Exception{
		
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("a01187827@itesm.mx");
		
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("marioandres");
		
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
	
		assertEquals("My account - My Store", resultPage.getTitleText());
		
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticationPageIsDisplayed() throws Exception{
		
			
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			
			HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
			
			HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
			emailField.setValueAttribute("a01187827@itesm.mx");
			
			HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
			passwordField.setValueAttribute("wrongpassword");
			
			HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
			HtmlPage resultPage = submitButton.click();
			
			assertEquals("Login - My Store", resultPage.getTitleText());
	
	}
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed() throws Exception{
webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("a01187827@itesm.mx");
		
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("wrongpassword");
		
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		
		 HtmlListItem  div = resultPage.getFirstByXPath("//div[@class='alert alert-danger']/ol/li");
		 
		 
		 assertEquals("Authentication failed.",div.getTextContent());
		 
	}
	@Test
	public void givenAClient_whenSearchingNotExistingProduct_thenNoResultsDisplayed() throws Exception{
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
		
		HtmlTextInput searchField = (HtmlTextInput) page.getElementById("search_query_top");
		searchField.setValueAttribute("nothing");
		
		HtmlButton searchButton = (HtmlButton) page.getElementByName("submit_search");
		
		HtmlPage searchResultPage = searchButton.click();
		
		HtmlParagraph  par = searchResultPage.getFirstByXPath("//p[@class='alert alert-warning']");
		
		assertTrue(par.getTextContent().contains("No results were found"),"what'sup kid");
		
	}
	@Test
	public void givenAClient_whenSearchingEmptyString_thenPleaseEnterDisplayed() throws Exception{
HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
		
		HtmlTextInput searchField = (HtmlTextInput) page.getElementById("search_query_top");
		searchField.setValueAttribute("");
		
		HtmlButton searchButton = (HtmlButton) page.getElementByName("submit_search");
		
		HtmlPage searchResultPage = searchButton.click();
		
		HtmlParagraph  par = searchResultPage.getFirstByXPath("//p[@class='alert alert-warning']");
		
		assertTrue(par.getTextContent().contains("Please enter a search keyword"),"what'sup kid");
	}
	@Test
	public void givenAClient_whenSigningOut_thenAuthenticationPageIsDisplayed() throws Exception{
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("a01187827@itesm.mx");
		
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("marioandres");
		
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		HtmlAnchor link = resultPage.getFirstByXPath("//a[@class='logout']");
		HtmlPage finalPage = link.click();
		assertEquals("Login - My Store", finalPage.getTitleText());
	}
}
