package cn.ninesmart.ninenews.articleslist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class ArticlesListRecyclerAdapter extends RecyclerView.Adapter<ArticlesListRecyclerAdapter.ViewHolder> {
    private List<ArticleModel> mDataSet = new ArrayList<>();
    private OnArticleItemClickListener mListener;

    public void setOnArticleItemClickListener(OnArticleItemClickListener listener) {
        mListener = listener;
    }

    public void reloadList(List<ArticleModel> list) {
        mDataSet.clear();
        mDataSet.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_articles_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleModel model = mDataSet.get(position);
        Context context = holder.itemView.getContext();
        holder.topicText.setText(model.getTopic());
        holder.summaryText.setText(model.getSummary());
        holder.categoryText.setText(model.getCategory());
        holder.commentCountText.setText(context.getString(R.string.n_comments, model.getCommentCount()));
        holder.viewCountText.setText(context.getString(R.string.n_views, model.getViewCount()));
        Picasso.with(context).load(model.getCoverImageSrc()).into(holder.coverImage);
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onArticleItemClick(mDataSet.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topicText;
        TextView summaryText;
        TextView categoryText;
        TextView commentCountText;
        TextView viewCountText;
        ImageView coverImage;

        ViewHolder(View itemView) {
            super(itemView);

            topicText = (TextView) itemView.findViewById(R.id.topic_text);
            summaryText = (TextView) itemView.findViewById(R.id.summary_text);
            categoryText = (TextView) itemView.findViewById(R.id.category_text);
            commentCountText = (TextView) itemView.findViewById(R.id.comment_count_text);
            viewCountText = (TextView) itemView.findViewById(R.id.view_count_text);
            coverImage = (ImageView) itemView.findViewById(R.id.cover_image);
        }
    }

    public interface OnArticleItemClickListener {
        void onArticleItemClick(ArticleModel articleModel);
    }
}
