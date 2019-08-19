package Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewModel<T> extends RecyclerView.ViewHolder {
    //T means it can be any data type

    public BaseViewModel(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T obj);
}
