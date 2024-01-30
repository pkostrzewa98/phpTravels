import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest(){

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
    public void noResultTest() {

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

