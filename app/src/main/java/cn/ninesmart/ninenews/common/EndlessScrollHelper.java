package cn.ninesmart.ninenews.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Author   : perqin
 * Date     : 17-2-3
 */

public abstract class EndlessScrollHelper {
    private boolean mRegistered;
    private boolean mCanLoadMore;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.OnScrollListener mListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mCanLoadMore) {
                if (mLayoutManager.findLastVisibleItemPosition() + mLayoutManager.getChildCount() >= mLayoutManager.getItemCount()) {
                    onLoadingMore();
                }
            }
        }
    };

    protected EndlessScrollHelper() {
        mRegistered = false;
    }

    public abstract void onLoadingMore();

    public void register(RecyclerView recyclerView) {
        if (mRegistered) {
            unregister();
        }
        mRegistered = true;
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        mRecyclerView = recyclerView;
        mRecyclerView.addOnScrollListener(mListener);
        setCanLoadMore(true);
    }

    public void unregister() {
        mRegistered =false;
        mLayoutManager = null;
        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(mListener);
            mRecyclerView = null;
        }
    }

    public void setCanLoadMore(boolean canLoadMore) {
        mCanLoadMore = canLoadMore;
    }
}
