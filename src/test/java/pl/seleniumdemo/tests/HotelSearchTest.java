package pl.seleniumdemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HotelSearchPage;

import java.util.List;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest(){
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        hotelSearchPage.setDates("02/02/2024","14/02/2024");
        hotelSearchPage.setTravellers();
        hotelSearchPage.performSearch();

        List<String> hotelNames = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b")).stream()
                .map(el -> el.getAttribute("textContent")).toList();

        hotelNames.forEach(System.out::println);
        Assert.assertEquals("Jumeirah Beach Hotel",hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower",hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana",hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth",hotelNames.get(3));

    }
    @Test
    public void noResultTest() {

        driver.findElement(By.name("checkin")).sendKeys("02/02/2024");
        driver.findElement(By.name("checkout")).sendKeys("14/02/2024");
        driver.findElement(By. name("travellers")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();
        WebElement noResultHeading = driver.findElement(By.xpath("//div[@class='itemscontainer']//h2"));

        Assert.assertTrue(noResultHeading.isDisplayed());
        Assert.assertEquals("No Results Found",noResultHeading.getText());
    }
    }

