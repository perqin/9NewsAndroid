package cn.ninesmart.ninenews.articleslist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.topicText.setText(mDataSet.get(position).getTopic());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topicText;

        public ViewHolder(View itemView) {
            super(itemView);

            topicText = (TextView) itemView.findViewById(R.id.topic_text);
        }
    }
}
