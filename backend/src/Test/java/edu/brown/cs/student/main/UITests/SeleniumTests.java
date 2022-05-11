package edu.brown.cs.student.main.UITests;

import edu.brown.cs.student.main.backendHandlers.*;
import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
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

import static org.junit.Assert.assertEquals;

public class SeleniumTests {

    static String frontendPath;
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
            chrome.get("http://127.0.0.1:3000/");
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
     * Checks that the page has the right Title
     */
    @Test
    public void basicSeleniumTest(){
        assertEquals("React App", chrome.getTitle());
    }


    /**
     * Check that front page contains the right text
     */
    @Test
    public void checkQuoteContainer(){
        WebElement quoteContainer = chrome.findElement(By.className("QuoteContainer"));
        assertEquals("Find your perfect route", quoteContainer.findElement(By.tagName("h2")).getText());

//        assertEquals("Find your perfect route.\n" +
//                "Need motivation?\n" +
//                "Get Started", motivation);
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
        // Spark.get("/table", new TableHandler());
        Spark.post("/table", new TableHandler());
        Spark.get("/tableNames", new TableNameHandler());
        Spark.post("/insert", new InsertHandler());
        Spark.post("/update", new UpdateHandler());
        Spark.post("/delete", new DeleteHandler());
        Spark.init();
    }
}
