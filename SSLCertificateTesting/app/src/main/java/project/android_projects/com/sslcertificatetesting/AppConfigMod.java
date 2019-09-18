package project.android_projects.com.sslcertificatetesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class AppConfigMod extends ApiResponseMod {
    @SerializedName("records")
    @Expose
    AppConfig appConfig = null;

    class AppConfig{
        @SerializedName("va")
        @Expose
        private String appVersion;
        @SerializedName("vm")
        @Expose
        private String mobileVersion;
        @SerializedName("img_svr_url")
        @Expose
        private String imgServerUrl;
        @SerializedName("p_url")
        @Expose
        private String productShareUrl;
        @SerializedName("s_url")
        @Expose
        private String storeShareUrl;
        @SerializedName("s_logo")
        @Expose
        private String storeLogo;
        @SerializedName("g_url")
        @Expose
        private String groupSaleUrl;
        @SerializedName("key")
        @Expose
        private String consumerKey;
        @SerializedName("secret")
        @Expose
        private String consumerSecret;
        @SerializedName("p2v")
        @Expose
        private String pointsToValue;
        @SerializedName("forex_rate")
        @Expose
        private ExchangeRate exchangeRate;
        @SerializedName("lat")
        @Expose
        private Float lat;
        @SerializedName("lon")
        @Expose
        private Float lon;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("country")
        @Expose
        private String country;
    }

    class ExchangeRate{

        @SerializedName("base")
        String mBase = "USD";
        @SerializedName("date")
        String mDate = "";
        @SerializedName("data")
        HashMap<String, Float> mData = null;
    }
}
