package main.com.testproject.java.framework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class BingPage 
{
    @FindBy(how = How.ID, using = "sb_form_q")
    public SelenideElement searchBox;

    @FindBy(how = How.ID, using = "sb_form_go")
    public SelenideElement searchButton;
    
    public BingPage waitForScreenToLoad()
    {
    	this.searchBox.shouldBe(Condition.visible);
    	this.searchButton.shouldBe(Condition.visible);

        return this;
    }

    public BingResultsPage performSearch(String q) throws Exception
    {
        this.waitForScreenToLoad();
        this.searchBox.setValue(q);
        $(By.xpath("//li[@query = '" + q + "']/div")).click();
        
        return page(BingResultsPage.class);
    }
}
