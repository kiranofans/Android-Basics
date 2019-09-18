package project.android_projects.com.sslcertificatetesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseMod {
    @SerializedName("RC")
    @Expose
    private int mRC = 0;
    @SerializedName("paging")
    @Expose
    Paging mPaging = null;

    class Paging {
        @SerializedName("page")
        @Expose
        int mPage = 0;
        @SerializedName("perPage")
        @Expose
        int mPerPage = 0;
        @SerializedName("pageStart")
        @Expose
        int mPageStart = 0;
        @SerializedName("pageEnd")
        @Expose
        int mPageEnd = 0;
        @SerializedName("total")
        @Expose
        int mTotal = 0;
    }
}
