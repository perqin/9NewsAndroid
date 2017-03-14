package cn.ninesmart.ninenews.articleslist.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.article.activities.ArticleActivity;
import cn.ninesmart.ninenews.articleslist.adapters.ArticlesListRecyclerAdapter;
import cn.ninesmart.ninenews.articleslist.contracts.ArticlesListContract;
import cn.ninesmart.ninenews.authpage.activities.LoginRegisterActivity;
import cn.ninesmart.ninenews.common.EndlessScrollHelper;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.users.models.UserModel;
import cn.ninesmart.ninenews.profile.activities.ProfileActivity;
import cn.ninesmart.ninenews.settings.SettingsActivity;

public class ArticlesListFragment extends Fragment
        implements ArticlesListContract.View, View.OnClickListener,
        ArticlesListRecyclerAdapter.OnArticleItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private ArticlesListContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private ArticlesListRecyclerAdapter mArticlesListRecyclerAdapter;
    private EndlessScrollHelper mArticlesListHelper = new EndlessScrollHelper() {
        @Override
        public void onLoadingMore() {
            setCanLoadMore(false);
            mPresenter.loadMoreNewsList(mArticlesListRecyclerAdapter.getLastDateline(), mArticlesListRecyclerAdapter.getNextPager());
        }
    };

    // Nav drawer
    private ImageView mAvatarImage;
    private TextView mNicknameText;
    private TextView mLevelText;
    // Fragment content
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mArticlesListRecyclerView;

    public ArticlesListFragment() {
        // Required empty public constructor
    }

    public static ArticlesListFragment newInstance() {
        return new ArticlesListFragment();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArticlesListRecyclerAdapter = new ArticlesListRecyclerAdapter();
        mArticlesListRecyclerAdapter.setOnArticleItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Nav Drawer
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = navigationView.getHeaderView(0);
        mAvatarImage = (ImageView) navHeaderView.findViewById(R.id.avatar_image);
        mAvatarImage.setOnClickListener(this);
        mNicknameText = (TextView) navHeaderView.findViewById(R.id.nickname_text);
        mLevelText = (TextView) navHeaderView.findViewById(R.id.level_text);
        // Fragment content
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener());
        mArticlesListRecyclerView = (RecyclerView)
                view.findViewById(R.id.articles_list_recycler_view);
        mArticlesListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArticlesListRecyclerView.setAdapter(mArticlesListRecyclerAdapter);

        mPresenter.reloadNewsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.updateUserProfile();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(ArticlesListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int code) {
        Toast.makeText(getContext(), "Unexpected Error: code " + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshNewsList(List<ArticleModel> articleModels, long lastDateline, int nextPage) {
        mArticlesListRecyclerAdapter.reloadList(articleModels, lastDateline, nextPage);
    }

    @Override
    public void appendNewsList(List<ArticleModel> articleModels, long lastDateline, int nextPage) {
        // FIXME: Structure improvement
        mArticlesListHelper.setCanLoadMore(true);
        mArticlesListRecyclerAdapter.appendList(articleModels, lastDateline, nextPage);
    }

    @Override
    public void refreshNotLoggedInUserProfile() {
        mNicknameText.setText(R.string.not_logged_in);
        mLevelText.setText(R.string.tap_avatar_above_to_login);
    }

    @Override
    public void refreshLoggedInUserProfile(UserModel userModel) {
        mNicknameText.setText(userModel.getNickname());
        mLevelText.setText(userModel.getLevel());
        Picasso.with(getContext()).load(userModel.getAvatarThumbSrc()).into(mAvatarImage);
    }

    @Override
    public void showLoginRegisterPage() {
        startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
    }

    @Override
    public void showUserProfilePage() {
        startActivity(new Intent(getActivity(), ProfileActivity.class));
    }

    @Override
    public void showRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
        if (isRefreshing) {
            mArticlesListHelper.unregister();
        } else {
            mArticlesListHelper.register(mArticlesListRecyclerView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar_image:
                mPresenter.avatarClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void onArticleItemClick(ArticleModel articleModel) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra(ArticleActivity.EXTRA_ARTICLE_ID, articleModel.getArticleId());
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings_action) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return false;
    }

    private class OnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mPresenter.reloadNewsList();
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
