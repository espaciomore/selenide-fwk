package test.com.testproject.java;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.internal.TextListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import com.codeborne.selenide.Configuration;
import com.google.gson.JsonArray;
import main.com.testproject.java.framework.config.AutoConfig;

public abstract class BaseTestClass 
{
	public final int g_SECONDS = 1; 
	public final int g_MILLISECONDS = 1000;
	
	public static void main(String args[]) throws Exception {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		
		String testType = AutoConfig.get("test.type").getAsString();
		
		switch(testType) {
		case "serial":
			BaseTestClass.runSerial(junit);
			break;
		case "parallel":
			BaseTestClass.runParallel(junit);
			break;
		default:
			throw new Exception("Unkown test type.");
		}
	}
	
	private static Class<?>[] getTestClasses() throws Exception
	{
		JsonArray v_arr = AutoConfig.get("test.classes").getAsJsonArray();
		Class<?>[] v_testClasses = new Class<?>[v_arr.size()];
		for(int i = 0; i < v_arr.size(); i++){
			v_testClasses[i] = Class.forName("test.com.testproject.java." + v_arr.get(i).getAsString());
		}
		
		return v_testClasses;
	}
	
	private static void runSerial(JUnitCore junit) throws Exception {
		junit.run(Computer.serial(),
				getTestClasses()
			);
	}

	private static void runParallel(JUnitCore junit) throws Exception {
		junit.run(ParallelComputer.methods(),
				getTestClasses()
			);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String v_browser = AutoConfig.get("webdriver.browser").getAsString();
		String v_location = "local\\resources\\" + AutoConfig.get("webdriver.driver").getAsString();
		String v_webdriver = "webdriver."+v_browser+".driver";
		Configuration.browser = v_browser;
		System.setProperty(v_webdriver, v_location);
		getWebDriver();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		step("Starting test...");
	}

	@After
	public void tearDown() throws Exception {
		step("Ending test...");
	}
	
	public static String date(String format)
	{
		return date(format, Calendar.getInstance().getTime());
	}

	public static String date(String format, int synonym, int amount)
	{
		Calendar v_cal = Calendar.getInstance();
		v_cal.add(synonym, amount);
		
		return date(format, v_cal.getTime());
	}
	
	public static String date(String format, java.util.Date date)
	{
		return new SimpleDateFormat(format).format(date);
	}
	
	public static void step(String desc)
	{
		String v_time = date("MM-dd-yy HH:mm:ss");
		System.out.println(v_time + " | " + desc);
	}
}
