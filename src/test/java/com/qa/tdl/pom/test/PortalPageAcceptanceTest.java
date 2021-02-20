package com.qa.tdl.pom.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.tdl.pom.pages.PortalPage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PortalPageAcceptanceTest {
	
	// web driver init
	@Autowired
    private static WebDriver driver;
    
    // setup
    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }
    
    // TESTS IN HERE
    @Test
    public void createToDoListTest() {
    	// GIVEN - that I can navigate to the website
    	driver.get("http://127.0.0.1:9090/");
    	PortalPage website = PageFactory.initElements(driver, PortalPage.class);
    	
    	// WHEN - I enter a new to do list name
    	website.createToDoListType();
    	
    	// AND - I click the button to create a new to do list
    	website.createToDoListSubmit();
    	
    	// THEN - it exists in the database? and reads it from the database onto the page
    	website.waitToDoListRead(driver);
    	String result = website.getToDoListName();
    	String expected = "Foo";
    	
    	assertNotNull(result);
    	assertEquals(expected, result);
    }
    
    @Test
    public void readToDoListTest() {
    	// GIVEN - that I can navigate to the website
    	driver.get("http://127.0.0.1:9090/");
    	PortalPage website = PageFactory.initElements(driver, PortalPage.class);
    	
    	// WHEN - the website loads it gets a list of to-do lists from the database 
    	
    	// THEN - I can see the to-do lists on the page
    	String result = website.getToDoListName();
    	String expected = "Foo";
    	
    	assertNotNull(result);
    	assertEquals(expected, result);
    }
    
    @Test
    public void updateToDoListCancelTest() {
    	// GIVEN - that I can navigate to the website
    	driver.get("http://127.0.0.1:9090/");
    	PortalPage website = PageFactory.initElements(driver, PortalPage.class);
    	
    	// WHEN - I hit the update button
    	website.updateToDoListButton();
    	website.waitToDoListUpdate(driver);
    	
    	// AND - I cancel the update
    	website.updateToDoListCancel();
    	website.waitToDoListRead(driver);
    	
    	// THEN - the update input, submit and cancel buttons are replaced by the regular name
    	
    	// update input
    	boolean expected1 = false;
    	boolean result1;
    	try {
    		driver.findElement(By.id("update-todolist-input-1"));
    		result1 = true;
    	} catch (NoSuchElementException e) {
    		result1 = false;
    	}
  
    	assertEquals(expected1, result1);
    	
    	// submit input
    	boolean expected2 = false;
    	boolean result2;
    	try {
    		driver.findElement(By.xpath("//*[@id=\"update-todolist-form-1\"]/div/div[2]/button"));
    		result2 = true;
    	} catch (NoSuchElementException e) {
    		result2 = false;
    	}
  
    	assertEquals(expected2, result2);
    	
    	// cancel input
    	boolean expected3 = false;
    	boolean result3;
    	try {
    		driver.findElement(By.xpath("//*[@id=\"update-todolist-form-1\"]/div/div[3]/button"));
    		result3 = true;
    	} catch (NoSuchElementException e) {
    		result3 = false;
    	}
  
    	assertEquals(expected3, result3);
    	
    	// regular name
    	String result4 = website.getToDoListName();
    	String expected4 = "Foo";
    	
    	assertNotNull(result4);
    	assertEquals(expected4, result4);
    	
    }
    
    @Test
    public void updateToDoListTest() {
    	// GIVEN - that I can navigate to the website
    	driver.get("http://127.0.0.1:9090/");
    	PortalPage website = PageFactory.initElements(driver, PortalPage.class);
    	
    	// WHEN - I hit the update button
    	website.updateToDoListButton();
    	website.waitToDoListUpdate(driver);
    	
    	// AND - I enter a new name into the input
    	website.updateToDoListType();
    	
    	// AND - I submit the new name
    	website.updateToDoListSubmit();
    	website.waitToDoListRead(driver);
    	
    	// THEN - the name is updated
    	String result = website.getToDoListName();
    	String expected = "Bar";
    	
    	assertNotNull(result);
    	assertEquals(expected, result);
    }
    
    // tear down
    @AfterAll
    public static void tearDown() {
        driver.quit();
        System.out.println("driver closed");
    }
}
