package project.android_projects.com.sslcertificatetesting;

public class ApiConstants {
    public static final String ENDPOINT_PINGING = "/api/rest/v7/ping";
    public static final String ENDPOINT_APP_CONFIG = "api/v7/appconfig";

    //Response codes
    public static final int API_RC_OK = 200;
    public static final int API_RC_NO_DATA = 204;
    public static final int API_RC_INVALID_REQUEST = 400;
    public static final int API_RC_UNKNOWN_HOST = 404;
    public static final int API_RC_ACCESS_DENIED = 403;
    public static final int API_RC_TOKEN_REJECTED = 401;

    public static final int API_RC_CONNECT_TIME_OUT = -2;
    public static final int API_RC_NO_INTERNET = -1;
    public static final int API_RC_UNKNOWN_ERROR = 0;


}
