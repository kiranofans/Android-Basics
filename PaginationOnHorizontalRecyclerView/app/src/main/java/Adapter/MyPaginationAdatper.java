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

import Models.NewsMod;
import project.android_projects.com.paginationonhorizontalrecyclerview.R;

import static Utils.AppConstants.HORIZONTAL_VIEW_TYPE;

public class MyPaginationAdatper extends RecyclerView.Adapter<BaseViewModel>{
    private Context context;

    private List<NewsMod.ArticleMod> articleList;
    private Callback callback;

    private boolean isLoaderVisible = false;

    private static final int VERTICAL_VIEW_TYPE = 20;

    public MyPaginationAdatper(Context context, List<NewsMod.ArticleMod> list){
        articleList = list;
        this.context = context;
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @NonNull
    @Override
    public BaseViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType){
            case HORIZONTAL_VIEW_TYPE://Normal type
                return new HorizontalViewHolder(LayoutInflater.from
                        (parent.getContext()).inflate(R.layout.item_layout,
                        parent,false));

            case VERTICAL_VIEW_TYPE://loading type
                return  new EmptyViewHolder(LayoutInflater.from
                        (parent.getContext()).inflate(R.layout.item_loading,
                        parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewModel holder, int position) {
        holder.bind(articleList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if(isLoaderVisible){//false
            return position == articleList.size() - 1 ? VERTICAL_VIEW_TYPE : HORIZONTAL_VIEW_TYPE;
        }else{
            return HORIZONTAL_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return articleList == null? 0 : articleList.size();
    }

    public void add(NewsMod.ArticleMod response){
        articleList.add(response);
        notifyItemInserted(articleList.size() - 1);
    }

    public void addAll(List<NewsMod.ArticleMod> items){
        for(NewsMod.ArticleMod response : items){
            add(response);
        }
    }

    public void remove(NewsMod.ArticleMod items){
        int pos = articleList.indexOf(items);
        if(pos > -1){
            articleList.remove(pos);
            notifyItemInserted(pos);
        }
    }

    public void addLoading(){
        isLoaderVisible = true;
        add(new NewsMod.ArticleMod());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = articleList.size() - 1;
        /*NewsMod.ArticleMod item =
        if (item != null) {*/
            articleList.remove(position);
            notifyItemRemoved(position);
        //}
    }

    /*public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }*/

    public class HorizontalViewHolder extends BaseViewModel<NewsMod.ArticleMod>{
        TextView titleTv, contentTV;
        ImageView newsImgView;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.title_tv);
            contentTV = itemView.findViewById(R.id.content_tv);
            newsImgView = itemView.findViewById(R.id.news_image_view);
        }

        @Override
        public void bind(NewsMod.ArticleMod obj) {
            titleTv.setText(obj.getTitle());
            contentTV.setText(obj.getDescription());
            Glide.with(context).load(obj.getUrlToImage()).into(newsImgView);
        }
    }

    public class EmptyViewHolder extends BaseViewModel<NewsMod.ArticleMod>{

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        public void bind(NewsMod.ArticleMod obj) {
        }
    }


    public interface Callback{
        void onRepoEmptyViewRetryClick();
    }
}
