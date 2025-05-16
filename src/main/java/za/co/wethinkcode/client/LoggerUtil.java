package za.co.wethinkcode.client;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    public static final Logger logger = Logger.getLogger("RobotWorldClientLogger");

    static {
        try {
            LogManager.getLogManager().reset();
            FileHandler fileHandler = new FileHandler("/Users/mxolisikhumalo/Desktop/Projects/MyProjects/Robot-World/src/main/java/za/co/wethinkcode/shared/logs/client.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}