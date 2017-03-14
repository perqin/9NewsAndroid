package cn.ninesmart.ninenews.article.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.article.contracts.ArticleContract;
import cn.ninesmart.ninenews.articlecomments.activities.ArticleCommentsActivity;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.utils.NaiveBBCodeParser;

public class ArticleFragment extends Fragment implements ArticleContract.View {
    private static final String ARG_ARTICLE_ID = "ARTICLE_ID";

    private OnFragmentInteractionListener mListener;
    private ArticleContract.Presenter mPresenter;
    private String mArticleId;

    private ImageView mCoverImage;
    private TextView mTopicText;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HtmlTextView mContentText;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(String articleId) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() != null) {
            mArticleId = getArguments().getString(ARG_ARTICLE_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCoverImage = (ImageView) getActivity().findViewById(R.id.cover_image);
        mTopicText = (TextView) getActivity().findViewById(R.id.topic_text);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.reloadArticle(mArticleId));
        mContentText = (HtmlTextView) view.findViewById(R.id.content_text);
        Button viewCommentsButton = (Button) view.findViewById(R.id.view_comments_button);
        viewCommentsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ArticleCommentsActivity.class);
            intent.putExtra(ArticleCommentsActivity.EXTRA_ARTICLE_ID, mArticleId);
            startActivity(intent);
        });

        mPresenter.reloadArticle(mArticleId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int code) {
    }

    @Override
    public void refreshArticle(ArticleModel articleModel) {
        mSwipeRefreshLayout.setRefreshing(false);
        Picasso.with(getContext()).load(articleModel.getCoverHdSrc()).into(mCoverImage);
        mTopicText.setText(articleModel.getTopic());
        mContentText.setHtml(NaiveBBCodeParser.toHtml(articleModel.getContent()), new HtmlHttpImageGetter(mContentText, null, true));
    }

    public interface OnFragmentInteractionListener {
    }
}
