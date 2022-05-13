package edu.brown.cs.student.main.UITests;

import edu.brown.cs.student.main.backendHandlers.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import spark.Spark;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SeleniumTests {

    static ChromeDriver chrome;
    static int backendPort = 4567;

    /**
     * Assigns the frontend path, runs a backend testing server, loads the data, sets up the chrome driver
     */
    @BeforeClass
    public static void setup() {
        try {
            // Run backend
            runSparkTestServer(backendPort);
            // Load data
            // Setup chrome driver
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            chrome = new ChromeDriver(options);
            chrome.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            //run local host 3000 (frontend port)
            chrome.get("http://localhost:3000/"); //("http://127.0.0.1:3000/");
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Closes the driver
     */
    @AfterClass
    public static void end(){
        chrome.close();
        chrome.quit();
    }

    /**
     * Check that quote container has correct text and button click functionality
     */
    @Test
    public void testQuoteContainer(){
        // tests that text in quote container is correct
        WebElement quoteContainer = chrome.findElement(By.className("QuoteContainer"));
        assertEquals("Find your perfect route.", quoteContainer.findElement(By.tagName("h2")).getText());
        assertEquals("Need motivation?", quoteContainer.findElement(By.tagName("p")).getText());
        assertEquals("Get Started", quoteContainer.findElement(By.tagName("button")).getText());

        // tests that when "get started" button is clicked, page is scrolled down to the map
        quoteContainer.findElement(By.tagName("button")).click();
        chrome.manage().timeouts().implicitlyWait(Duration.ofMillis(1000)); // waits
        assert(chrome.findElement(By.id("map")).isDisplayed());
    }

    /**
     * Check that title is correct.
     */
    @Test
    public void testTitle(){
        WebElement quoteContainer = chrome.findElement(By.className("title"));
        assertEquals("LOOPIFY", quoteContainer.findElement(By.tagName("textPath")).getText());
    }

    /**
     * Check that route container text is correct.
     */
    @Test
    public void testRouteContainer(){
        WebElement quoteContainer = chrome.findElement(By.className("RouteContainer"));
        assertEquals("find a loop", quoteContainer.findElement(By.tagName("h2")).getText());
    }

    /**
     * Check that map container has correct functionality
     */
    @Test
    public void testMapContainer(){
        WebElement mapContainer = chrome.findElement(By.className("MapContainer"));
        WebElement locationButton = mapContainer.findElements(By.tagName("button")).get(0);
        WebElement goButton = mapContainer.findElements(By.tagName("button")).get(1);
        WebElement map = chrome.findElement(By.id("map"));
        WebElement inputMiles = chrome.findElement(By.className("MapContainer")).findElement(By.tagName("input"));

        // tests that text is correct
        assertEquals("base:", mapContainer.findElements(By.tagName("label")).get(0).getText());
        assertEquals("miles:", mapContainer.findElements(By.tagName("label")).get(1).getText());
        assertEquals("0 miles", mapContainer.findElement(By.id("distance")).getText());
        assertEquals("use my location", locationButton.getText());
        assertEquals("go!", goButton.getText());

        // tests that buttons have correct text and click functionality
        List<WebElement> mapItems = map.findElements(By.className("gmnoprint"));
        int curLen = mapItems.size();

        // tests that marker are drawn on the map
        locationButton.click();
        chrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); // waits
        locationButton.click();
        assertEquals(curLen, map.findElements(By.className("gmnoprint")).size());
        curLen = map.findElements(By.className("gmnoprint")).size();

        // tests that route is drawn on map
        inputMiles.sendKeys("2");
        chrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(1)); // waits
        goButton.click();
        chrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); // waits
        // polyline cannot be drawn because of backend
        assertEquals(curLen, map.findElements(By.className("gmnoprint")).size());
        assertEquals(
                Double.parseDouble(inputMiles.getAttribute("value")),
                Double.parseDouble(mapContainer.findElement(By.id("distance")).getText().split(" ")[0]),
                2);
    }


    /**
     * Tests that only certain inputs are allowed in mileage input box
     */
    @Test
    public void testInput(){
        // tests that numbers can be inputted
        WebElement inputMiles = chrome.findElement(By.className("MapContainer")).findElement(By.tagName("input"));
        inputMiles.sendKeys("5");
        assertEquals("5", inputMiles.getAttribute("value"));

        // tests that numbers with decimals can be inputted
        inputMiles.clear();
        inputMiles.sendKeys("1.2");
        assertEquals("1.2", inputMiles.getAttribute("value"));

        // tests that text cannot be inputted
        inputMiles.clear();
        inputMiles.sendKeys("hi!");
        assertEquals("", inputMiles.getAttribute("value"));
    }


    /**
     * Runs the backend server for testing
     * @param port
     */
    static private void runSparkTestServer(int port) {
        Spark.port(port);
        Spark.externalStaticFileLocation("src/main/resources/static");
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        // Put Routes Here
        Spark.post("/getRoute", new GetRouteHandler());
        Spark.init();
    }
}
