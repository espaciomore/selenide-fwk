package test.com.testproject.java;

import org.junit.Assert;
import org.junit.Test;

import com.codeborne.selenide.Configuration;

import main.com.testproject.java.framework.config.AutoConfig;
import main.com.testproject.java.framework.pageobjects.BingResultsPage;
import main.com.testproject.java.framework.pageobjects.BingPage;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class BingTest extends BaseTestClass
{
	@Test
	public void test_that_user_can_search_orders_by_date() throws Exception {
        try
        {
            step("Open the Bing Search site");
        	open(AutoConfig.get("bing.url").getAsString());
            BingPage v_bingPage = page(BingPage.class);
            v_bingPage.waitForScreenToLoad();

            step("Search for \"Selenide\"");
            Configuration.timeout = 30 * g_MILLISECONDS;
            BingResultsPage v_resultsPage = v_bingPage.performSearch("selenide");

            step("Wait for Results");
            Boolean hasResults = v_resultsPage.hasLink("http://selenide.org/");
            Assert.assertTrue("Bing has results", hasResults);
        }
        catch (Exception ex)
        {
            Assert.fail(ex.getMessage());
        }
	}
}
