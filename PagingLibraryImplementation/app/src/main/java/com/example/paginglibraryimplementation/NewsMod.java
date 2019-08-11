package com.example.paginglibraryimplementation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Observable;

class NewsMod extends Observable {
    private String imgURL, newsName, newsID;

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

    public void setNewsID(String newsID) {
        this.newsID = newsID;
        setChanged();
        notifyObservers();
    }

    public static final DiffUtil.ItemCallback<NewsMod> CALLBACK = new DiffUtil.ItemCallback<NewsMod>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsMod oldItem, @NonNull NewsMod newItem) {
            return oldItem.getNewsID()==newItem.getNewsID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsMod oldItem, @NonNull NewsMod newItem) {
            return true;
        }
    };

    public class ApiResponse{
        public List<NewsMod> newsList;
        public boolean hasMore;
        public int quota_max;
        public int quota_remaining;
    }
}
