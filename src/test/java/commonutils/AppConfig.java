package commonutils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class AppConfig {

    private static final Logger log = LogManager.getLogger(AppConfig.class);

    private static Config defaultConfig = ConfigFactory.parseResources("env/application.ST.conf");
    private static Config config = null;
    private AppConfig() {}

    private static Config getConfig(){
        if (config==null) {
            String env = System.getProperty("env");
            config = ConfigFactory.parseResources("env/application." + env + ".conf")
                    .withFallback(defaultConfig)
                    .resolve();
            log.info("Getting Config file for Environment: {}",config.getString("name"));
        }
        return config;
    }

    public static String getTextProperty(String key){
        return getConfig().getString(key);
    }

    public static Integer getIntProperty(String key){
        return getConfig().getInt(key);
    }
}
