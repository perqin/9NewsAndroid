package cn.ninesmart.ninenews.article.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.article.adapters.ArticleCommentsRecyclerAdapter;
import cn.ninesmart.ninenews.article.contracts.ArticleContract;
import cn.ninesmart.ninenews.common.EndlessScrollHelper;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.comments.models.CommentModel;
import cn.ninesmart.ninenews.utils.NaiveBBCodeParser;

public class ArticleFragment extends Fragment implements ArticleContract.View, View.OnClickListener {
    private static final String ARG_ARTICLE_ID = "ARTICLE_ID";

    private OnFragmentInteractionListener mListener;
    private ArticleContract.Presenter mPresenter;
    private String mArticleId;
    private String mTargetId;
    private ArticleCommentsRecyclerAdapter mCommentRecyclerAdapter;
    private BottomSheetBehavior mBottomSheetBehavior;
    private EndlessScrollHelper mCommentsListHelper = new EndlessScrollHelper() {
        @Override
        public void onLoadingMore() {
            setCanLoadMore(false);
            mPresenter.loadMoreArticleComments(mArticleId, mCommentRecyclerAdapter.getLastDateline(), mCommentRecyclerAdapter.getNextPager());
        }
    };

    private ImageView mCoverImage;
    private TextView mTopicText;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HtmlTextView mContentText;
    private RecyclerView mCommentsRecyclerView;
    private EditText mCommentEdit;
    private ImageButton mUndoReplyButton;
    private ImageButton mPostCommentButton;

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

    private void onWebViewReachBottom() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void onWebViewAwayBottom() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void switchToReply(CommentModel commentModel) {
        mCommentEdit.getText().clear();
        mCommentEdit.setHint(getString(R.string.reply_to_comment, commentModel.getAuthor().getNickname()));
        mUndoReplyButton.setVisibility(View.VISIBLE);
        mTargetId = commentModel.getCommentId();
    }

    private void switchToComment() {
        mCommentEdit.getText().clear();
        mCommentEdit.setHint(R.string.your_comment);
        mUndoReplyButton.setVisibility(View.GONE);
        mTargetId = mArticleId;
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
        mCommentRecyclerAdapter.setItemClickListener(new OnCommentClickListener());
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
        NestedScrollView contentScrollView = (NestedScrollView) view.findViewById(R.id.content_nested_scroll_view);
        contentScrollView.setOnScrollChangeListener(new OnContentScrollChangeListener());
        mContentText = (HtmlTextView) view.findViewById(R.id.content_text);
        LinearLayout commentsLayout = (LinearLayout) getActivity().findViewById(R.id.comments_layout);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) commentsLayout.getLayoutParams();
        mBottomSheetBehavior = (BottomSheetBehavior) params.getBehavior();
        mCommentsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.comments_recycler_view);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentsRecyclerView.setAdapter(mCommentRecyclerAdapter);
        mCommentsListHelper.register(mCommentsRecyclerView);
        mCommentEdit = (EditText) getActivity().findViewById(R.id.comment_edit);
        mUndoReplyButton = (ImageButton) getActivity().findViewById(R.id.undo_reply_button);
        mUndoReplyButton.setOnClickListener(this);
        mPostCommentButton = (ImageButton) getActivity().findViewById(R.id.post_comment_button);
        mPostCommentButton.setOnClickListener(this);

        onWebViewAwayBottom();
        switchToComment();

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
        mContentText.setHtml(NaiveBBCodeParser.toHtml(articleModel.getContent()), new HtmlHttpImageGetter(mContentText, null, true));
    }

    @Override
    public void refreshArticleComments(List<CommentModel> commentModels, long lastDateline, int nextPage) {
        mCommentRecyclerAdapter.reloadComments(commentModels, lastDateline, nextPage);
    }

    @Override
    public void appendArticleComments(List<CommentModel> commentModels, long lastDateline, int nextPage) {
        // FIXME: Structure improvement
        mCommentsListHelper.setCanLoadMore(true);
        mCommentRecyclerAdapter.appendList(commentModels, lastDateline, nextPage);
    }

    @Override
    public void postCommentSuccessfully(CommentModel commentModel) {
        mCommentRecyclerAdapter.prependNewComment(commentModel);
        mCommentsRecyclerView.smoothScrollToPosition(0);
        switchToComment();
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mCommentEdit.getWindowToken(), 0);
    }

    @Override
    public void showPostCommentFailure() {
        Toast.makeText(getContext(), R.string.fail_to_leave_comment, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommentBlankError() {
        Toast.makeText(getContext(), R.string.comment_cannot_be_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserNotLoggedInError() {
        Toast.makeText(getContext(), R.string.you_are_not_logged_in, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.post_comment_button) {
            mPresenter.postCommentToTarget(mTargetId, mCommentEdit.getText().toString());
        } else if (v.getId() == R.id.undo_reply_button) {
            switchToComment();
        }
    }

    public interface OnFragmentInteractionListener {
    }

    private class OnContentScrollChangeListener implements NestedScrollView.OnScrollChangeListener {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            View lastView = v.getChildAt(v.getChildCount() - 1);
            if (lastView.getBottom() == v.getHeight() + v.getScrollY()) {
                onWebViewReachBottom();
            } else {
                onWebViewAwayBottom();
            }
        }
    }

    private class OnCommentClickListener implements ArticleCommentsRecyclerAdapter.ItemClickListener {
        @Override
        public void onItemClick(CommentModel commentModel) {
            switchToReply(commentModel);
        }
    }
}
