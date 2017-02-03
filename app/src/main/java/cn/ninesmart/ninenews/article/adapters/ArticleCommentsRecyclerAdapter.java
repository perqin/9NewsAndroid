package cn.ninesmart.ninenews.article.adapters;

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
import cn.ninesmart.ninenews.data.comments.models.CommentModel;

/**
 * Author   : perqin
 * Date     : 17-1-27
 */

public class ArticleCommentsRecyclerAdapter extends RecyclerView.Adapter<ArticleCommentsRecyclerAdapter.ViewHolder> {
    private ArrayList<CommentModel> mDataSet = new ArrayList<>();
    private long mLastDateline;
    private int mNextPage;

    public void reloadComments(List<CommentModel> commentModels, long lastDateline, int nextPage) {
        mDataSet.clear();
        mDataSet.addAll(commentModels);
        mLastDateline = lastDateline;
        mNextPage = nextPage;
        notifyDataSetChanged();
    }

    public void appendList(List<CommentModel> commentModels, long lastDateline, int nextPage) {
        mDataSet.addAll(commentModels);
        mLastDateline = lastDateline;
        mNextPage = nextPage;
        notifyItemRangeInserted(mDataSet.size() - commentModels.size(), commentModels.size());
    }

    public long getLastDateline() {
        return mLastDateline;
    }

    public int getNextPager() {
        return mNextPage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_article_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentModel model = mDataSet.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(model.getAuthor().getAvatarThumbSrc())
                .into(holder.avatarImage);
        holder.nicknameText.setText(model.getAuthor().getNickname());
        holder.commentText.setText(model.getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView nicknameText;
        TextView commentText;

        ViewHolder(View itemView) {
            super(itemView);

            avatarImage = (ImageView) itemView.findViewById(R.id.avatar_image);
            nicknameText = (TextView) itemView.findViewById(R.id.nickname_text);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
