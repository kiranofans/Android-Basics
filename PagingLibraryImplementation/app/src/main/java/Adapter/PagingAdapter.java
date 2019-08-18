package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.example.paginglibraryimplementation.R;

import Models.ArticleResponse;
import Utils.BaseViewHolder;

public class PagingAdapter extends androidx.paging.PagedListAdapter
        <ArticleResponse, BaseViewHolder> {

    private Context context;
    private LayoutInflater inflater;

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

    public PagingAdapter(Context context) {
        super(CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public PagingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagingViewHolder(inflater.from(parent.getContext()).inflate
                (R.layout.paging_items_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ArticleResponse articleResponse = getItem(position);
        holder.bind(articleResponse);
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
}
