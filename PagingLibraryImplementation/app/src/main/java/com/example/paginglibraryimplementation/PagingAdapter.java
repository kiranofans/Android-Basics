package com.example.paginglibraryimplementation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PagingAdapter extends androidx.paging.PagedListAdapter<NewsMod,PagingAdapter.PagingViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<NewsMod> newsList;

    public PagingAdapter (Context context,List<NewsMod> newsList){
        super(NewsMod.CALLBACK);
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public PagingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagingViewHolder(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.paging_items_layout,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PagingViewHolder holder, int position) {
        NewsMod newsMod = getItem(position);
        if(newsMod!=null){
            holder.newsTitleTV.setText(newsMod.getNewsName());
            Picasso.get().load(newsMod.getImgURL()).centerCrop().into(holder.imgViews);
        }else{
            Toast.makeText(context,"News is null",Toast.LENGTH_SHORT).show();
        }
    }

    public class PagingViewHolder extends RecyclerView.ViewHolder{

        private TextView newsTitleTV;
        private ImageView imgViews;

        public PagingViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitleTV = (TextView)itemView.findViewById(R.id.txt_news_name);
            imgViews = (ImageView)itemView.findViewById(R.id.img_news_banner);


        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
