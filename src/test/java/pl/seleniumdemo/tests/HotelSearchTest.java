package pl.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.ResultsPage;
import pl.seleniumdemo.utils.ExcelReader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest() {
        ExtentTest test = extentReports.createTest("searchHotelTest");
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        test.log(Status.PASS,"Setting city done");
        hotelSearchPage.setDates("02/02/2024", "14/02/2024");
        test.log(Status.PASS,"Setting dates done");
        hotelSearchPage.setTravellers(1, 2);
        test.log(Status.PASS,"Setting travelers done");
        hotelSearchPage.performSearch();
        test.log(Status.PASS,"Performing search done");
        test.log(Status.PASS,"Screenshot", MediaEntityBuilder.createScreenCaptureFromPath("src/test/resources/screenshots/screenshot.png").build());

        ResultsPage resultsPage = new ResultsPage(driver);

        List<String> hotelNames = resultsPage.getHotelNames();

        hotelNames.forEach(System.out::println);
        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower", hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNames.get(3));
        test.log(Status.PASS,"Assertions passed");
    }

    @Test
    public void noResultTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setDates("02/02/2024", "14/02/2024");
        hotelSearchPage.setTravellers(0, 1);
        hotelSearchPage.performSearch();


        ResultsPage resultsPage = new ResultsPage(driver);

        Assert.assertTrue(resultsPage.resultHeading.isDisplayed());
        Assert.assertEquals(resultsPage.getHeadingText(), "No Results Found");
    }

    @Test(dataProvider = "data")
    public void searchHotelTestWithDataProvider(String city, String hotel) {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity(city);
        hotelSearchPage.setDates("02/02/2024", "14/02/2024");
        hotelSearchPage.setTravellers(1, 2);
        hotelSearchPage.performSearch();

        ResultsPage resultsPage = new ResultsPage(driver);
        List<String> hotelNames = resultsPage.getHotelNames();

        hotelNames.forEach(System.out::println);
        Assert.assertEquals(hotelNames.get(0), hotel);

    }
    @DataProvider
    public Object[][] data() throws IOException {
        return ExcelReader.readExcel("testData.xlsx");
    }
}

