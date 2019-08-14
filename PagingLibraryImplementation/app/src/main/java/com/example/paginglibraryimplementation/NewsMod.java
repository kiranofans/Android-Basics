package com.example.paginglibraryimplementation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Observable;

import Models.Source;

class NewsMod extends Observable {
    @SerializedName("urlToImage")
    @Expose
    private String imgURL;

    @SerializedName("title")
    @Expose
    private String newsName;

    @SerializedName("articles")
    @Expose
    private List<ArticleMod> articleList;

    private String newsID;

    public List<ArticleMod> getArticleList() {
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
        setChanged();
        notifyObservers();
    }


    public void setArticleList(List<ArticleMod> articleList) {
        this.articleList = articleList;
    }

    public class ArticleMod {
        @SerializedName("source")
        @Expose
        private Source source;

        @SerializedName("author")
        @Expose
        private String author;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("url")
        @Expose
        private String url;

        @SerializedName("urlToImage")
        @Expose
        private String urlToImage;

        @SerializedName("publishedAt")
        @Expose
        private String publishedAt;

        @SerializedName("content")
        @Expose
        private String content;

        public ArticleMod(String title, String imgUrl) {
            this.title = title;
            this.urlToImage = imgUrl;
        }

        public ArticleMod(String title) {
            this.title = title;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

    public static final DiffUtil.ItemCallback<ArticleMod> CALLBACK =
            new DiffUtil.ItemCallback<ArticleMod>() {
                @Override
                public boolean areItemsTheSame(@NonNull ArticleMod oldItem,
                                               @NonNull ArticleMod newItem) {
                    return oldItem.getSource().getId() == newItem.getSource().getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ArticleMod oldItem,
                                                  @NonNull ArticleMod newItem) {
                    return true;
                }
            };
}
