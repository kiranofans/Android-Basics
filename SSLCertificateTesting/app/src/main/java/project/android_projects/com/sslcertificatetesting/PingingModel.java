package project.android_projects.com.sslcertificatetesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PingingModel {

    //{"RC":200}
    @SerializedName("RC")
    @Expose
    private int response_code;

    public int getRC() {
        return response_code;
    }

    public void setRC(Integer rC) {
        this.response_code = rC;
    }
}
