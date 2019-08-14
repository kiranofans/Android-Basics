package com.example.paginglibraryimplementation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class NewsMod implements Parcelable {
    @SerializedName("urlToImage")
    @Expose
    private String imgURL;

    @SerializedName("title")
    @Expose
    private String newsName;

    @SerializedName("articles")
    @Expose
    private List<ArticleResponse> articleList;


    public NewsMod(){}


    private String newsID;

    protected NewsMod(Parcel in) {
        imgURL = in.readString();
        newsName = in.readString();
        newsID = in.readString();
    }

    public static final Creator<NewsMod> CREATOR = new Creator<NewsMod>() {
        @Override
        public NewsMod createFromParcel(Parcel in) {
            return new NewsMod(in);
        }

        @Override
        public NewsMod[] newArray(int size) {
            return new NewsMod[size];
        }
    };

    public List<ArticleResponse> getArticleList() {
        return articleList;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getNewsName() {
        return newsName;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }


    public void setArticleList(List<ArticleResponse> articleList) {
        this.articleList = articleList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(imgURL);
        parcel.writeValue(newsName);
        parcel.writeList(articleList);
    }

}
