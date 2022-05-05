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
import org.openqa.selenium.support.ui.Select;
import spark.Spark;

import java.nio.file.FileSystems;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UITestLoopify {
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
     * Checks that a random row in the tas table has the right values
     */
    @Test
    public void TARandRowTest(){
        String motivation = chrome.findElement(By.className("QuoteContainer")).getText();
        assertEquals("Find your perfect route.\n" +
                "Need motivation?\n" +
                "Get Started", motivation);
    }

//    /**
//     * Checks that the horoscopes table has the right headers
//     */
//    @Test
//    public void horoscopesHeadersTest(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("horoscopes");
//
//        // Checking that the headers have the correct labels
//        String header1 = chrome.findElement(
//                By.xpath("//tr[1]/td[1]")).getText();
//        String header2 = chrome.findElement(
//                By.xpath("//tr[1]/td[2]")).getText();
//        assertEquals("horoscope_id", header1);
//        assertEquals("horoscope", header2);
//    }
//    /**
//     * Checks that a random row in the horoscopes table has the right values
//     */
//    @Test
//    public void horoscopesRandRowTest(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("horoscopes");
//        // Checking that the headers have the correct labels
//        String header1 = chrome.findElement(
//                By.xpath("//tr[4]/td[1]")).getText();
//        String header2 = chrome.findElement(
//                By.xpath("//tr[4]/td[2]")).getText();
//        assertEquals("3", header1);
//        assertEquals("Gemini", header2);
//    }
//    /**
//     * Checks that the ta_horoscope table has the right headers
//     */
//    @Test
//    public void taHoroscopesHeadersTest(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("ta_horoscope");
//        // Checking that the headers have the correct labels
//        String header1 = chrome.findElement(
//                By.xpath("//tr[1]/td[1]")).getText();
//        String header2 = chrome.findElement(
//                By.xpath("//tr[1]/td[2]")).getText();
//
//        assertEquals("ta_id", header1);
//        assertEquals("horoscope_id", header2);
//    }
//    /**
//     * Checks that a random row in the ta_horoscope table has the right values
//     */
//    @Test
//    public void taHoroscopesRandRowTest(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("ta_horoscope");
//        // Checking that the headers have the correct labels
//        String val1 = chrome.findElement(
//                By.xpath("//tr[16]/td[1]")).getText();
//        String val2 = chrome.findElement(
//                By.xpath("//tr[16]/td[2]")).getText();
//
//        assertEquals("15", val1);
//        assertEquals("7", val2);
//    }
//
//    /**
//     * Checks that the ta table has the right number of rows
//     */
//    @Test
//    public void tasRowsNum(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("tas");
//        // Checking that all the data has been loaded (right number of rows)
//        List<WebElement> rows = chrome.findElements(By.xpath("//tr"));
//        //22 rows (including header) + the Add Row button row
//        assertEquals(23, rows.size());
//    }
//
//    /**
//     * Checks that the horoscopes table has the right number of rows
//     */
//    @Test
//    public void horoscopesRowsNum(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("horoscopes");
//        // Checking that all the data has been loaded (right number of rows)
//        List<WebElement> rows = chrome.findElements(By.xpath("//tr"));
//        //13 rows (including header) + the Add Row button row
//        assertEquals(14, rows.size());
//    }
//
//    /**
//     * Checks that the ta_horoscope table has the right number of rows
//     */
//    @Test
//    public void taHoroscopesRowsNum(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("ta_horoscope");
//        // Checking that all the data has been loaded (right number of rows)
//        List<WebElement> rows = chrome.findElements(By.xpath("//tr"));
//        //22 rows (including header) + the Add Row button row
//        assertEquals(23, rows.size());
//    }
//
//    /**
//     * Checks that the update row functionality works for the TAs table.
//     */
//    @Test
//    public void testUpdateRow(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("tas");
//        // Checking that all the data has been loaded (right number of rows)
//        WebElement id = chrome.findElement(By.xpath("//tr[2]/td[4]/td[2]/input"));
//        id.sendKeys("Ben");
//        chrome.findElement(By.xpath("//tr[2]/td/td/button")).click();
//        String ben = chrome.findElement(
//                By.xpath("//tr[2]/td[2]")).getText();
//        assertEquals("Ben", ben);
//
//        //reinsate value to "Tim"
//        id.sendKeys("Tim");
//        chrome.findElement(By.xpath("//tr[2]/td/td/button")).click();
//    }
//
//    /**
//     * Checks that the update row functionality works for the horoscopes table.
//     */
//    @Test
//    public void testAddAndDelRow(){
//        Select dropdown = new Select(chrome.findElement(By.id("dropdownMenu")));
//        chrome.findElement(By.id("loader")).click();
//
//        // selecting tas to load
//        dropdown.selectByVisibleText("horoscopes");
//        // Checking that all the data has been loaded (right number of rows)
//
//        //inputting new horoscope id
//        chrome.findElement(By.xpath("//*[@id=\"addRow\"]/td[1]/input")).
//                sendKeys("13");
//        //inputting new horoscope
//        chrome.findElement(By.xpath("//*[@id=\"addRow\"]/td[2]/input")).
//                sendKeys("Jose");
//        chrome.findElement(By.xpath("//*[@id=\"addRow\"]/td/button ")).click();
//
//        //check id of new element
//        String newElementID = chrome.findElement(
//                By.xpath("//tr[14]/td[1]")).getText();
//        assertEquals("13", newElementID);
//
//        //check horoscope name of new element
//        String horoscope = chrome.findElement(
//                By.xpath("//tr[14]/td[2]")).getText();
//        assertEquals("Jose", horoscope);
//
//        //check that the total number of rows has increased by 1 to 15
//        //make sure num rows is back down to 14
//        List<WebElement> rows = chrome.findElements(By.xpath("//tr"));
//        assertEquals(15, rows.size());
//        //delete the element we added
//        chrome.findElement(By.xpath("//tr[14]/td/button")).click();
//
//    }

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