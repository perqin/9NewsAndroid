package cn.ninesmart.ninenews.article.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.article.contracts.ArticleContract;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;

public class ArticleFragment extends Fragment implements ArticleContract.View {
    private static final String ARG_ARTICLE_ID = "ARTICLE_ID";

    private OnFragmentInteractionListener mListener;
    private ArticleContract.Presenter mPresenter;
    private String mArticleId;

    private TextView mTopicText;
    private TextView mContentText;

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

        mTopicText = (TextView) view.findViewById(R.id.topic_text);
        mContentText = (TextView) view.findViewById(R.id.content_text);

        mPresenter.reloadArticle(mArticleId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        mTopicText.setText(articleModel.getTopic());
        mContentText.setText(articleModel.getContent());
    }

    public interface OnFragmentInteractionListener {
    }
}
