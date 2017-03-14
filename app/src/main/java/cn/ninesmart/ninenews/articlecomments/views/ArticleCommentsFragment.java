package cn.ninesmart.ninenews.articlecomments.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.article.adapters.ArticleCommentsRecyclerAdapter;
import cn.ninesmart.ninenews.articlecomments.contracts.ArticleCommentsContract;
import cn.ninesmart.ninenews.common.EndlessScrollHelper;
import cn.ninesmart.ninenews.data.comments.models.CommentModel;

public class ArticleCommentsFragment extends Fragment
        implements ArticleCommentsContract.View, View.OnClickListener {
    private static final String ARG_ARTICLE_ID = "ARTICLE_ID";

    private OnFragmentInteractionListener mListener;
    private ArticleCommentsContract.Presenter mPresenter;
    private String mArticleId;
    private String mTargetId;
    private ArticleCommentsRecyclerAdapter mCommentRecyclerAdapter;
    private EndlessScrollHelper mCommentsListHelper = new EndlessScrollHelper() {
        @Override
        public void onLoadingMore() {
            setCanLoadMore(false);
            mPresenter.loadMoreArticleComments(mArticleId, mCommentRecyclerAdapter.getLastDateline(), mCommentRecyclerAdapter.getNextPager());
        }
    };

    private ActionBar mActionBar;
    private RecyclerView mCommentsRecyclerView;
    private EditText mCommentEdit;
    private ImageButton mUndoReplyButton;
    private ImageButton mPostCommentButton;

    public ArticleCommentsFragment() {
        // Required empty public constructor
    }

    public static ArticleCommentsFragment newInstance(String articleId) {
        ArticleCommentsFragment fragment = new ArticleCommentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_article_comments, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCommentsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.comments_recycler_view);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentsRecyclerView.setAdapter(mCommentRecyclerAdapter);
        mCommentsListHelper.register(mCommentsRecyclerView);
        mCommentEdit = (EditText) getActivity().findViewById(R.id.comment_edit);
        mUndoReplyButton = (ImageButton) getActivity().findViewById(R.id.undo_reply_button);
        mUndoReplyButton.setOnClickListener(this);
        mPostCommentButton = (ImageButton) getActivity().findViewById(R.id.post_comment_button);
        mPostCommentButton.setOnClickListener(this);

        switchToComment();

        mPresenter.reloadArticleComments(mArticleId);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ArticleCommentsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int code) {
    }

    @Override
    public void showLoading(boolean isLoading, int target) {
    }

    @Override
    public void refreshArticleComments(List<CommentModel> commentModels, long lastDateline, int nextPage) {
        mCommentRecyclerAdapter.reloadComments(commentModels, lastDateline, nextPage);
        mListener.onCommentsCountChange(commentModels.size());
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
        void onCommentsCountChange(int count);
    }

    private class OnCommentClickListener implements ArticleCommentsRecyclerAdapter.ItemClickListener {
        @Override
        public void onItemClick(CommentModel commentModel) {
            switchToReply(commentModel);
        }
    }
}
