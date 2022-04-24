package edu.brown.cs.student.main.UITests;

import edu.brown.cs.student.main.backendHandlers.DeleteHandler;
import edu.brown.cs.student.main.backendHandlers.InsertHandler;
import edu.brown.cs.student.main.backendHandlers.TableHandler;
import edu.brown.cs.student.main.backendHandlers.TableNameHandler;
import edu.brown.cs.student.main.backendHandlers.UpdateHandler;
import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.FileSystems;
import java.time.Duration;
import java.util.List;

import spark.Spark;

public class UITest {
    static String frontendPath;
    static ChromeDriver chrome;
    static int backendPort = 4567;

    /**
     * Assigns the frontend path, runs a backend testing server, loads the data, sets up the chrome driver
     */
    @BeforeClass
    public static void setup() {
        try {
            // Assign a path for the frontend
            String absolutePath = FileSystems.getDefault().getPath("../frontend/table/table.html").normalize().toAbsolutePath().toString();
            frontendPath = "file://" + absolutePath;
            // Run backend
            runSparkTestServer(backendPort);
            // Load data
            DatabaseAccessor.load("data/recommendationdata/sql/horoscopes.sqlite3");
            // Setup chrome driver
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            chrome = new ChromeDriver(options);
            chrome.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            chrome.get(frontendPath);
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


    @Before public void init(){
        chrome.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));;
    }

    /**
     * Checks that the page has the right Title
     */
    @Test
    public void basicSeleniumTest(){
        assertEquals("Table", chrome.getTitle());
    }

    /**
     * Checks that the tas table has the right headers
     */
    @Test
    public void TAHeadersTest(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("tas");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that the headers have the correct labels
        String header1 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[1]/td[1]")).getText();
        String header2 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[1]/td[2]")).getText();
        String header3 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[1]/td[3]")).getText();
        assertEquals("id", header1);
        assertEquals("name", header2);
        assertEquals("role", header3);
    }
    /**
     * Checks that a random row in the tas table has the right values
     */
    @Test
    public void TARandRowTest(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("tas");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();

        // Checking that the headers have the correct labels
        String header1 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[16]/td[1]")).getText();
        String header2 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[16]/td[2]")).getText();
        String header3 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[16]/td[3]")).getText();
        assertEquals("15", header1);
        assertEquals("Pedro", header2);
        assertEquals("UTA", header3);
    }
    /**
     * Checks that the horoscopes table has the right headers
     */
    @Test
    public void HoroscopesHeadersTest(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("horoscopes");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that the headers have the correct labels
        String header1 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[1]/td[1]")).getText();
        String header2 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[1]/td[2]")).getText();
        assertEquals("horoscope_id", header1);
        assertEquals("horoscope", header2);
    }

    /**
     * Checks that a random row in the horoscopes table has the right values
     */
    @Test
    public void HoroscopesRandRowTest(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("horoscopes");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that the headers have the correct labels
        String header1 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[4]/td[1]")).getText();
        String header2 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[4]/td[2]")).getText();
        assertEquals("3", header1);
        assertEquals("Gemini", header2);
    }

    /**
     * Checks that the ta_horoscope table has the right headers
     */
    @Test
    public void TAHoroscopesHeadersTest(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("ta_horoscope");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that the headers have the correct labels
        String header1 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[1]/td[1]")).getText();
        String header2 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[1]/td[2]")).getText();
        assertEquals("ta_id", header1);
        assertEquals("horoscope_id", header2);
    }

    /**
     * Checks that a random row in the ta_horoscope table has the right values
     */
    @Test
    public void TAHoroscopesRandRowTest(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("ta_horoscope");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that the headers have the correct labels
        String header1 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[16]/td[1]")).getText();
        String header2 = chrome.findElement(
                By.xpath("//*[@id=\"displayTable\"]/tbody/tr[16]/td[2]")).getText();
        assertEquals("15", header1);
        assertEquals("7", header2);
    }

    /**
     * Checks that the ta table has the right number of rows
     */
    @Test
    public void TAsRowsNum(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("tas");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that all the data has been loaded (right number of rows)
        List<WebElement> rows = chrome.findElements(By.xpath("//*[@id=\"displayTable\"]/tbody/tr"));
        assertEquals(22, rows.size());
    }

    /**
     * Checks that the horoscopes table has the right number of rows
     */
    @Test
    public void HoroscopesRowsNum(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("horoscopes");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that all the data has been loaded (right number of rows)
        List<WebElement> rows = chrome.findElements(By.xpath("//*[@id=\"displayTable\"]/tbody/tr"));
        assertEquals(13, rows.size());
    }

    /**
     * Checks that the ta_horoscope table has the right number of rows
     */
    @Test
    public void TAHoroscopesRowsNum(){
        Select dropdown = new Select(chrome.findElement(By.id("tableNames")));
        // selecting tas to load
        dropdown.selectByVisibleText("ta_horoscope");
        // loading the tas table
        chrome.findElement(By.id("loader")).click();
        // Checking that all the data has been loaded (right number of rows)
        List<WebElement> rows = chrome.findElements(By.xpath("//*[@id=\"displayTable\"]/tbody/tr"));
        assertEquals(22, rows.size());
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