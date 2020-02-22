package com.example.paginglibraryimplementation.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    //If public abstract class, the PagingAdapter cannot access the BaseViewHolder
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T object);
}
