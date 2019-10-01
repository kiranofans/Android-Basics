package project.android_projects.com.sslcertificatetesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Records {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("secret")
    @Expose
    private String secret;

    private String tokenType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTokenType(){
        if(!Character.isUpperCase(tokenType.charAt(0))){
            tokenType = Character.toString(tokenType.charAt(0))
                    .toUpperCase() + tokenType.substring(1);
        }
        return tokenType;
    }
}
