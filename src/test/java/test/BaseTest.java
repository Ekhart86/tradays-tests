package test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import util.Wrapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static util.Constants.*;
import static util.SizeReducer.resize;


public class BaseTest extends Wrapper {

    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite()
    public void driverInit() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability("deviceName", "defaultDeviceName");
        capabilities.setCapability("udid", UDID);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 80000);
        capabilities.setCapability("appPackage", APP_PACKAGE);
        capabilities.setCapability("appActivity", APP_ACTIVITY);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        driver = new AndroidDriver<>(new URL(BASE_APPIUM_URL), capabilities);
        driver.manage().timeouts().implicitlyWait(BASE_IMPLICITLY_WAIT, TimeUnit.SECONDS);
        initPage();
    }


    @AfterMethod(alwaysRun = true)
    public static void resetApp() {
        driver.resetApp();
    }


    @Step("{0}")
    public void step(String title, Runnable code) {
        logger.info(title);
        code.run();
        makeScreenshot(0.3);
    }

    /**
     * Сделать скриншот
     * @param percentage изменить размер изображения, 1.0 исходный размер
     */
    public static void makeScreenshot(double percentage) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = new byte[0];
        try {
            fileContent = toByteArrayAutoClosable(resize(scrFile, percentage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        attachScreenshot(fileContent, "Скриншот");

    }

    private static byte[] toByteArrayAutoClosable(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        }
    }

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] attachScreenshot(final byte[] bytes, final String name) {
        return bytes;
    }


    @Attachment(value = "Видеозапись выполнения теста", type = "video/mp4")
    public static byte[] attachmentVideo(String video) {
        return Base64.getDecoder().decode(video);
    }

}
