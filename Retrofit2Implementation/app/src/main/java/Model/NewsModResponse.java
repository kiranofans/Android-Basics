
package Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsModResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticleList() {
        return articles;
    }

    public void setArticlesList(List<Article> articles) {
        this.articles = articles;
    }

    public static class Article {

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

        public Article(String title, String imgUrl){
            this.title = title;
            this.urlToImage = imgUrl;
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
}
