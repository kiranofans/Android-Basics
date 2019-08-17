package com.example.paginglibraryimplementation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;

import java.util.List;

import Utils.BaseViewHolder;

public class PagingAdapter extends androidx.paging.PagedListAdapter
        <ArticleResponse, BaseViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ArticleResponse> newsList;

    public static final DiffUtil.ItemCallback<ArticleResponse> CALLBACK =
            new DiffUtil.ItemCallback<ArticleResponse>() {
                @Override
                public boolean areItemsTheSame(@NonNull ArticleResponse oldItem,
                                               @NonNull ArticleResponse newItem) {
                    return oldItem.getSource().getId() == newItem.getSource().getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ArticleResponse oldItem,
                                                  @NonNull ArticleResponse newItem) {
                    return true;
                }
            };

    public PagingAdapter(Context context, PagedList<ArticleResponse> newsList) {
        super(CALLBACK);
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public PagingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagingViewHolder(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.paging_items_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(newsList.get(position));
    }

    public class PagingViewHolder extends BaseViewHolder<ArticleResponse> {

        private TextView newsTitleTV;
        private ImageView imgViews;

        public PagingViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitleTV = (TextView) itemView.findViewById(R.id.txt_news_name);
            imgViews = (ImageView) itemView.findViewById(R.id.img_news_banner);


        }

        @Override
        public void bind(ArticleResponse object) {
            newsTitleTV.setText(object.getTitle());
            Glide.with(context).load(object.getUrlToImage()).into(imgViews);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


}
