package util;

public class Constants {

    public final static String BASE_APPIUM_URL = "http://127.0.0.1:4723/wd/hub";
    public final static Integer BASE_IMPLICITLY_WAIT = 25;
    public final static String APP_ACTIVITY = "net.metaquotes.ui.MainActivity";
    public final static String APP_PACKAGE = "net.metaquotes.economiccalendar";
    public final static String UDID = System.getProperty("udid");
    public final static boolean VIDEO_RECORDING = Boolean.parseBoolean(System.getProperty("video"));
}
