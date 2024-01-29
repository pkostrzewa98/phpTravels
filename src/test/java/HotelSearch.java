import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class HotelSearch {
    @Test
    public void searchHotel(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//span[text()='Search by Hotel or City Name']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("Dubai");
        driver.findElement(By.xpath("//span[@class='select2-match' and text()='Dubai']")).click();
        driver.findElement(By.name("checkin")).sendKeys("02/02/2024");
       // driver.findElement(By.name("checkout")).sendKeys("14/02/2024");
        driver.findElement(By.name("checkout")).click();
        driver.findElement(By.xpath("/html/body/div[10]/div[1]/table/thead/tr[1]/th[3]")).click();
        driver.findElement(By.xpath("/html/body/div[10]/div[1]/table/tbody/tr[3]/td[4]")).click();
        driver.findElement(By.name("travellers")).click();
        driver.findElement(By.xpath("//button[@id='adultPlusBtn']")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();
        List<String> hotelNames = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b")).stream()
                .map(el -> el.getAttribute("textContent")).toList();

        Assert.assertEquals("Jumeirah Beach Hotel",hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower",hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana",hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth",hotelNames.get(3));

    }
    @Test
    public void noResult() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.findElement(By.name("checkin")).sendKeys("02/02/2024");
        driver.findElement(By.name("checkout")).sendKeys("14/02/2024");
        driver.findElement(By.name("travellers")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();
        WebElement noResultHeading = driver.findElement(By.xpath("//div[@class='itemscontainer']//h2"));

        Assert.assertTrue(noResultHeading.isDisplayed());
        Assert.assertEquals("No Results Found",noResultHeading.getText());
    }
    }

