import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static java.awt.SystemColor.text;
import static org.bouncycastle.cms.RecipientId.password;

// Test scenario - Non-matching passwords
// 1. Go to Login page
// 2. Click on 'Register'
// 3. Validate the url has changed to /register
// 4. Validate that the confirm field is shown
// 5. Enter username
// 6. Enter email
// 7. Enter password
// 8. Enter a different password in confirm field
// 9. Check that sign "Passwords do not match!" has appeared
// 10. Click sign in button
// 11. Validate that a pop-up appears indicating that registration has failed


public class invalidPassword {

    ChromeDriver driver;
    String registerUrl = http://training.skillo-bg.com:4200/users/register";
    String homeUrl = http://training.skillo-bg.com:4200/posts/all";

    @BeforeMethod
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(homeUrl);
    }
    @Parameters({"username", "email", "password", "wrongPassword"})
    @Test
    public void InvalidRegisterUser(String username, String email, String password, String wrongPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10

        System.out.println("1. Go to Login page");
        clickElement(By.id("nav-link-login"), 5, 100);

        System.out.println("2. Click on 'Register'");
        clickElement(By.linkText("Register"), 5);

        System.out.println("3. Validate the url has changed to /register");
        wait.until(ExpectedConditions.urlToBe(registerUrl));

        System.out.println("4. Validate that the confirm field is shown");
        WebElement confirmPassField = driver.findElement(By.name("verify-password"));
        wait.until(ExpectedConditions.visibilityOf(confirmPassField));

        System.out.println("5. Enter username");
        WebElement usernameField = driver.findElement(By.name("username"));
        enterTextField(usernameField, username);

        System.out.println("6. Enter email");
        WebElement emailField = driver.findElement(By.cssSelector("input[formcontrolname='email']"));
        enterTextField(emailField, email);

        System.out.println("7. Enter password");
        WebElement passField = driver.findElement(By.id("defaultRegisterFormPassword"));
        enterTextField(passField, password);

        System.out.println("8. Enter a different password in confirm field");
        WebElement confirmPassField = driver.findElement(By.name("verify-password"));
        enterTextField(confirmPassField, wrongPassword);

        System.out.println("9. Check that sign 'Passwords do not match!' has appeared");
        WebElement InvalidPassField = driver.findElement(By.xpath("//input[contains(.,'Passwords do not match!')]"));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(InvalidPassField.isDisplayed(), "Passwords do not match!' is not visible");

        System.out.println("10. Click sign in button");
        clickElement (By.id("sign-in-button"), 3);

        System.out.println("Validate that a pop-up appears indicating that registration has failed ");
        WebElement toastMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-bottom-right toast-container")));
        String toastMsgText = toastMsg.getText();
        Assert.assertEquals(toastMsgText, "Registration failed!", "Registration was successful");

    }

    private WebElement clickElement (By locator, int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        return element;
    }

    private WebElement clickElement(By locator, int totalTimeoutSec, int retryTimeoutMs) {
        FluentWait<ChromeDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(totalTimeoutSec))
                .pollingEvery(Duration.ofMillis(retryTimeoutMs))
                .ignoring(TimeoutException.class);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        return element;
    }

    private void enterTextField(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
        Boolean isValid = element.getAttribute("class").contains("is-valid");
        Assert.assertTrue(isValid, "The field is invalid!");


    }
    @AfterMethod
    public void teardown() {
        driver.close();
    }
}


