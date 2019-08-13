package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import Model.NewsModResponse;
import project.android_projects.com.retrofit2implementation.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;

    private List<NewsModResponse.Article> articlesList;

    public MyRecyclerViewAdapter(Context context, List<NewsModResponse.Article> list) {
        mContext = context;
        this.articlesList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View newsView = inflater.inflate(R.layout.recview_item_layout, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(articlesList.get(position));
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class MyViewHolder extends BaseViewHolder<NewsModResponse.Article> {
        private TextView titleTv;
        private ImageView imgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.news_title_tv);
            imgView = (ImageView) itemView.findViewById(R.id.recImgView);

        }

        @Override
        public void bind(NewsModResponse.Article object) {
            titleTv.setText(object.getTitle());
            Glide.with(mContext).load(object.getUrlToImage()).into(imgView);
        }

    }
}
