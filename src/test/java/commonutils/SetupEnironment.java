package commonutils;

public class SetupEnironment {

    public static String getBaseEndpoint()
    {
        return AppConfig.getTextProperty(AppConstants.APIEndPoint);
    }

    public static String getKey()
    {
        return AppConfig.getTextProperty(AppConstants.APP_KEY);
    }
}
