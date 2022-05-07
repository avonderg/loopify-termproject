package edu.brown.cs.student.Main;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.chromium.ChromiumDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SeleniumTest {

    @Test
    public void testTableVisualizer() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500)); // waits
        driver.get("file:///Users/kleoku/project-2-jdoming8-kku2-ssabar/frontend/table/table.html");

        testTitle(driver);
        testDropdown(driver);
        testUpdate(driver);

        driver.quit();
    }

    /**
     * Tests that title element has the correct text.
     * @param driver
     */
    public void testTitle(ChromeDriver driver) {
        List<WebElement> title = driver.findElements(By.tagName("H1"));
        assertEquals(title.get(0).getText(), "Sprint 3 Table Visualization");
    }

    /**
     * Tests that dropdown menu options can be selected, and after selection,
     * table is visualied with correct number of rows and columns.
     * @param driver
     */
    public void testDropdown(ChromeDriver driver) {
        // dropdown menu appears when 'load data' button is clicked
        WebElement loadButton = driver.findElement(By.id("loader"));

        WebElement dropdown_div = driver.findElement(By.id("dropdown"));

        loadButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500)); // waits

        WebElement dropdown = dropdown_div.findElement(By.tagName("select"));
        List<WebElement> dropdown_menu = dropdown.findElements(By.tagName("option"));

//        for (WebElement i : dropdown_menu) {
        for (int i=1; i<dropdown_menu.size(); i++ ) {
            dropdown.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500)); // waits

            dropdown_menu.get(i).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500)); // waits

            testTable(driver, dropdown_menu.get(i));
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); // waits

            testInputs(driver);
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); // waits
        }
    }

    /**
     * Tests that table is the correct dimensions.
     * @param driver ChromeDriver
     * @param i table
     */
    public void testTable(ChromeDriver driver, WebElement i) {
        // returns table's expected dimensionality [column size, row size]
        int[] size = getSize(i.getText()); //("innerHTML"));

        // TEST: table has correct number of rows
        assertEquals(size[0],
            driver.findElements(By.xpath("/html/body/div[2]/table/tbody/tr")).size());

        // TEST: table has correct number of columns
        assertEquals(size[1],
            driver.findElements(By.xpath("/html/body/div[2]/table/tbody/tr[1]/th")).size());
    }

    /**
     * Returns table's expected dimensionality
     * @param tableName
     * @return array describing [row size, column size]
     */
    public int[] getSize(String tableName) {
        if(tableName.equals("horoscopes")) {
            return new int[] {13, 2};
        } else if (tableName.equals("tas")) {
            return new int[] {8, 3};
        } else if (tableName.equals("sqlite_sequence")) {
            return new int[] {2, 2};
        } else if (tableName.equals("ta_horoscope")) {
            return new int[] {8, 2};
        } else {
            return new int[] {6, 2};
        }
    }

    /**
     * Tests input text boxes for add and delete functionality.
     * @param driver
     */
    public void testInputs(ChromeDriver driver) {
        // get all input elements (for insert and delete functionalities)
        List<WebElement> inputs = driver.findElements(By.tagName("INPUT"));
        String text = "1";

        // User can input text in “Insert Row” text box
        inputs.get(0).click();
        inputs.get(0).sendKeys(text);
        assertEquals(text, inputs.get(0).getAttribute("value"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500)); // waits

        // User can input row number in “Delete Row” text box
        inputs.get(1).click();
        inputs.get(1).sendKeys(text);
        assertEquals(text, inputs.get(1).getAttribute("value"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500)); // waits
    }

    /**
     * Tests that table content can be changed
     * @param driver
     */
    public void testUpdate(ChromeDriver driver) {
        // User can edit data in table for update functionality
        WebElement content_div = driver.findElement(By.id("content"));
        List<WebElement> tableItems = content_div.findElements(By.tagName("TD"));

        // iterate through each element in the table and "update" with new text
        for ( WebElement i : tableItems) {
            i.click();
            i.clear();
            i.sendKeys("1");
        }

        for (WebElement i : tableItems) {
            assertEquals("1", i.getText());
        }
    }
}