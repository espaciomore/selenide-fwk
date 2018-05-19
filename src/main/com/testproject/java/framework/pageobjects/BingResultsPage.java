package main.com.testproject.java.framework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BingResultsPage 
{
    @FindBy(how = How.ID, using = "sb_form_q")
    public SelenideElement searchBox;

    @FindBy(how = How.ID, using = "sb_form_go")
    public SelenideElement searchButton;
    
    public BingResultsPage waitForScreenToLoad()
    {
    	this.searchBox.shouldBe(Condition.visible);
    	this.searchButton.shouldBe(Condition.visible);

        return this;
    }

    public Boolean hasLink(String url)
    {
        return $(By.xpath("//a[@href = '" + url + "']")).shouldBe(Condition.visible).isDisplayed();
    }
}
