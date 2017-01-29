package cn.ninesmart.ninenews.article.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import java.util.List;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.article.adapters.ArticleCommentsRecyclerAdapter;
import cn.ninesmart.ninenews.article.contracts.ArticleContract;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.comments.models.CommentModel;

public class ArticleFragment extends Fragment implements ArticleContract.View {
    private static final String ARG_ARTICLE_ID = "ARTICLE_ID";

    private OnFragmentInteractionListener mListener;
    private ArticleContract.Presenter mPresenter;
    private String mArticleId;
    private ArticleCommentsRecyclerAdapter mCommentRecyclerAdapter;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    private ImageView mCoverImage;
    private TextView mTopicText;
    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private PicassoBbTextView mContentText;
    private WebView mContentWeb;
    private RecyclerView mCommentRecyclerView;

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
        mCommentRecyclerAdapter = new ArticleCommentsRecyclerAdapter();
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
//        mContentText = (PicassoBbTextView) view.findViewById(R.id.content_text);
        mContentWeb = (WebView) view.findViewById(R.id.content_web);
        mCommentRecyclerView = (RecyclerView) getActivity().findViewById(R.id.comments_recycler_view);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentRecyclerView.setAdapter(mCommentRecyclerAdapter);

        mPresenter.reloadArticle(mArticleId);
        mPresenter.reloadArticleComments(mArticleId);
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
        TextProcessor processor = BBProcessorFactory.getInstance().create();
        String html = processor.process(articleModel.getContent())/*.replaceAll("\\[p]", "<p>").replaceAll("\\[/p]", "</p>")*/;
        mContentWeb.loadData(html, "text/html; charset=utf-8", "UTF-8");
        mContentWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mContentText.setText(Html.fromHtml(html, new PicassoImageGetter(getContext()), null));
//        mContentText.setText(articleModel.getContent());
//        mContentText.setBbText(articleModel.getContent());
    }

    @Override
    public void refreshArticleComments(List<CommentModel> commentModels) {
        mCommentRecyclerAdapter.reloadComments(commentModels);
    }

    public interface OnFragmentInteractionListener {
    }
}
