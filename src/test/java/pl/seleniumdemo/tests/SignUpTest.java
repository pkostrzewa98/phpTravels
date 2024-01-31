package pl.seleniumdemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;

public class SignUpTest extends BaseTest {

    @Test
    public void signUpTest() {

        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().
                filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        String lastName = "Newman";
        int randomNumber = (int) (Math.random()*1000);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.name("firstname")).sendKeys("Joe");
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys("123123123");
        driver.findElement(By.name("email")).sendKeys("test" + randomNumber + "@pmail.com");
        driver.findElement(By.name("password")).sendKeys("Test123@");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123@");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();
        WebElement heading = driver.findElement(By.xpath("//h3[@class='RTL']"));
        Assert.assertTrue(heading.getText().contains(lastName));
        Assert.assertEquals(heading.getText(), "Hi, Joe Newman");

    }

    @Test
    public void signUpEmptyFormTest() {
        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().
                filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();
        List<String> errors = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p")).stream().map(WebElement::getText).toList();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Email field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertTrue(errors.contains("The Last Name field is required."));

    }

    @Test
    public void signUpInvalidEmailTest() {

        int randomNumber = (int) (Math.random() * 1000);
        String email = "test" + randomNumber;
        String lastName = "Newman";

        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().
                filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.name("firstname")).sendKeys("Joe");
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys("123123123");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("Test123@");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123@");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        List<String> errors = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p")).stream().map(WebElement::getText).toList();

        Assert.assertTrue(errors.contains("The Email field must contain a valid email address."));

    }
}

