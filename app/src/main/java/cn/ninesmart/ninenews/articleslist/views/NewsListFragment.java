package cn.ninesmart.ninenews.articleslist.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.articleslist.adapters.ArticlesListRecyclerAdapter;
import cn.ninesmart.ninenews.articleslist.contracts.NewsListContract;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;

public class NewsListFragment extends Fragment implements NewsListContract.View {
    private NewsListContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private ArticlesListRecyclerAdapter mArticlesListRecyclerAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mArticlesListRecyclerView;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArticlesListRecyclerAdapter = new ArticlesListRecyclerAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener());
        mArticlesListRecyclerView = (RecyclerView)
                view.findViewById(R.id.articles_list_recycler_view);
        mArticlesListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArticlesListRecyclerView.setAdapter(mArticlesListRecyclerAdapter);

        mPresenter.reloadNewsList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(NewsListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int code) {
        Toast.makeText(getContext(), "Unexpected Error: code " + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshNewsList(List<ArticleModel> articleModels) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mArticlesListRecyclerAdapter.reloadList(articleModels);
    }

    @Override
    public void appendNewsList(List<ArticleModel> articleModels) {
        // TODO: Implement appendNewsList
        throw new RuntimeException("Method not implemented: appendNewsList");
    }

    public interface OnFragmentInteractionListener {
    }

    private class OnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mPresenter.reloadNewsList();
        }
    }
}
