package project.android_projects.com.sslcertificatetesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class AppConfigMod extends ApiResponseMod {
    /** Enclosing class */
    @SerializedName("records")
    @Expose
    AppConfig appConfig = null;

    /** Nested class */
    static class AppConfig{
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

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getMobileVersion() {
            return mobileVersion;
        }

        public void setMobileVersion(String mobileVersion) {
            this.mobileVersion = mobileVersion;
        }

        public String getImgServerUrl() {
            return imgServerUrl;
        }

        public void setImgServerUrl(String imgServerUrl) {
            this.imgServerUrl = imgServerUrl;
        }

        public String getProductShareUrl() {
            return productShareUrl;
        }

        public void setProductShareUrl(String productShareUrl) {
            this.productShareUrl = productShareUrl;
        }

        public String getStoreShareUrl() {
            return storeShareUrl;
        }

        public void setStoreShareUrl(String storeShareUrl) {
            this.storeShareUrl = storeShareUrl;
        }

        public String getStoreLogo() {
            return storeLogo;
        }

        public void setStoreLogo(String storeLogo) {
            this.storeLogo = storeLogo;
        }

        public String getGroupSaleUrl() {
            return groupSaleUrl;
        }

        public void setGroupSaleUrl(String groupSaleUrl) {
            this.groupSaleUrl = groupSaleUrl;
        }

        public String getConsumerKey() {
            return consumerKey;
        }

        public void setConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
        }

        public String getConsumerSecret() {
            return consumerSecret;
        }

        public void setConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
        }

        public String getPointsToValue() {
            return pointsToValue;
        }

        public void setPointsToValue(String pointsToValue) {
            this.pointsToValue = pointsToValue;
        }

        public ExchangeRate getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(ExchangeRate exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }

        public Float getLon() {
            return lon;
        }

        public void setLon(Float lon) {
            this.lon = lon;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
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
