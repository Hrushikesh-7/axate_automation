import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AxateFeature {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            //Launch url
            driver.get("https://yrk.branch-master.axate.io/articles/1");

            // Wait for the iframe to be available and switch to it
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("axate-notice")));
            System.out.println("Switched to axate-notice frame");

            // Wait for the button to be clickable and then click it
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.className("button_cta")));
            button.click();
            System.out.println("WebElement located and clicked");
            //switch to default frame
            driver.switchTo().defaultContent();
            System.out.println("switched back to main window");

            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable
                    (By.xpath("//a[@class='global-submitButton']")));
            continueButton.click();

            //provide basic details inputs
            WebElement email = wait.until(ExpectedConditions.presenceOfElementLocated((By.id("email"))));
            email.sendKeys("testemail@test.com");
            WebElement country = driver.findElement(By.id("country"));
            country.click();
            country.sendKeys("United Kingdom");
            WebElement postalCode = driver.findElement(By.id("postcode"));
            postalCode.sendKeys("AL10 9AY");
            driver.findElement(By.cssSelector("input#publisher_marketing_news[value='true']")).click();
            driver.findElement(By.cssSelector("input#publisher_marketing_offers[value='false']")).click();
            driver.findElement(By.cssSelector("input#axate_marketing[value='true']")).click();
            WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
            submit.click();

            //select topup amount
            WebElement topup = wait.until(ExpectedConditions.elementToBeClickable
                    (By.xpath("//label[contains(text(),'£5.00')]")));
            topup.click();
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            //continue with voucher type as preferred payment
            WebElement voucher = wait.until(ExpectedConditions.elementToBeClickable
                    (By.xpath("//*[@class='voucher card']/div/div/span")));
            voucher.click();
            driver.findElement(By.id("voucher")).sendKeys("QA2024");
            WebElement redeem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='button']")));
            redeem.click();

            //confirm whether the full article can be viewed
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("iframe__popup_notices")));
            System.out.println("Switched to iframe__popup_notices");
            WebElement confirmation = wait.until(ExpectedConditions.presenceOfElementLocated
                    (By.xpath("//h4[contains(text(),'You are ready to read your first article.')]")));
            if (confirmation.isDisplayed()){
                System.out.println("You are ready to read your first article");
            }
            else {
                System.out.println("confirmation failed");
            }
            driver.switchTo().defaultContent();
            System.out.println("switched back to main window");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
