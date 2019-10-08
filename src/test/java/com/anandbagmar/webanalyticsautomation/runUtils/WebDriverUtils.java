package com.anandbagmar.webanalyticsautomation.runUtils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class WebDriverUtils {
    private static Logger logger = Logger.getLogger(WebDriverUtils.class);

    public static String getPathForChromeDriver() {
        int[] versionNamesArr = getChromeVersionsFor();
        if (versionNamesArr.length > 0) {
            int highestChromeVersion = Arrays.stream(versionNamesArr).max().getAsInt();
            String message = "ChromeDriver for Chrome version " + highestChromeVersion
                    + "on device";
            logger.debug(message);
            WebDriverManager.chromedriver().version(String.valueOf(highestChromeVersion)).setup();
            return WebDriverManager.chromedriver().getBinaryPath();
        } else {
            return null;
        }
    }

    private static int[] getChromeVersionsFor() {
        CommandPrompt cmd = new CommandPrompt();
        String resultStdOut = null;
        try {
            resultStdOut = cmd.runCommandThruProcess("adb shell dumpsys package com.android.chrome | grep versionName");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] versionNamesArr = {};
        if (resultStdOut.contains("versionName=")) {
            String[] foundVersions = resultStdOut.split("\n");
            for (String foundVersion : foundVersions) {
                String version = foundVersion.split("=")[1].split("\\.")[0];
                String format = String.format("Found Chrome version - '%s'", version);
                logger.debug(format);
                versionNamesArr = ArrayUtils.add(versionNamesArr, Integer.parseInt(version));
            }
        } else {
            logger.debug(String.format("Chrome not found on device"));
        }
        return versionNamesArr;
    }

}
