package project.android_projects.com.sslcertificatetesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerLogin {
    @SerializedName("RC")
    @Expose
    private Integer rC;
    @SerializedName("records")
    @Expose
    private Records records;

    public Integer getRC() {
        return rC;
    }

    public void setRC(Integer rC) {
        this.rC = rC;
    }

    public Records getRecords() {
        return records;
    }

    public void setRecords(Records records) {
        this.records = records;
    }

}
