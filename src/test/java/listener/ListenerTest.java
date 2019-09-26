package listener;

import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Duration;

import static test.BaseTest.attachmentVideo;
import static test.BaseTest.makeScreenshot;
import static util.Constants.VIDEO_RECORDING;
import static util.Wrapper.driver;


public class ListenerTest implements ITestListener {

    private int testCounter = 1;
    private Logger logger = LoggerFactory.getLogger(ListenerTest.class);

    @Override
    public void onFinish(ITestContext Result) {

    }

    @Override
    public void onStart(ITestContext Result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {
        makeScreenshot(0.5);
        if (VIDEO_RECORDING) {
            attachmentVideo(driver.stopRecordingScreen());
        }
    }

    @Override
    public void onTestFailure(ITestResult Result) {
        makeScreenshot(0.5);
        System.out.println();
        logger.info("Test " + Result.getName() + " ended with an error!\n" + Result.getThrowable());
        System.out.println();
        testCounter++;
        if (VIDEO_RECORDING) {
            attachmentVideo(driver.stopRecordingScreen());
        }
    }

    @Override
    public void onTestSkipped(ITestResult Result) {
        if (VIDEO_RECORDING) {
            driver.stopRecordingScreen();
        }
        logger.info("Missed test:" + Result.getName());
    }

    @Override
    public void onTestStart(ITestResult Result) {
        logger.info("Test No." + testCounter);
        logger.info("Launched test:" + Result.getName());
        if (VIDEO_RECORDING) {
            driver.startRecordingScreen(new AndroidStartScreenRecordingOptions()
                    .withTimeLimit(Duration.ofSeconds(500L))
                    .withVideoSize("1280x720")
                    .withBitRate(800000));
        }
    }

    @Override
    public void onTestSuccess(ITestResult Result) {
        logger.info("Successful test:" + Result.getName());
        testCounter++;
        if (VIDEO_RECORDING) {
            attachmentVideo(driver.stopRecordingScreen());
        }
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        if (VIDEO_RECORDING) {
            attachmentVideo(driver.stopRecordingScreen());
        }
    }
}