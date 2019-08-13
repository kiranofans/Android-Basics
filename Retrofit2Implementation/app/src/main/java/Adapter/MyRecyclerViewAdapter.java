package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.NewsModResponse;
import project.android_projects.com.retrofit2implementation.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>  {
    private Context mContext;
    private LayoutInflater inflater;

    private List<NewsModResponse.Article> articlesList;

    public MyRecyclerViewAdapter(Context context,List<NewsModResponse.Article> list){
        mContext = context;
        this.articlesList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        inflater = LayoutInflater.from(mContext);
        View newsView = inflater.inflate(R.layout.recview_item_layout,parent,false);

        MyViewHolder viewHolder = new MyViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsModResponse.Article article = articlesList.get(position);
        holder.titleTv.setText(article.getTitle());
        Picasso.get().load(article.getUrlToImage()).into(holder.imgView);
    }


    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTv;
        private ImageView imgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = (TextView)itemView.findViewById(R.id.news_title_tv);
            imgView = (ImageView)itemView.findViewById(R.id.recImgView);

        }
    }
}
