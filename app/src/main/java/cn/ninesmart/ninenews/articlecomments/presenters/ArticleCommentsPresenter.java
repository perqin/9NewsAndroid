package cn.ninesmart.ninenews.articlecomments.presenters;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.ninesmart.ninenews.articlecomments.contracts.ArticleCommentsContract;
import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;
import cn.ninesmart.ninenews.data.comments.models.CommentModel;
import cn.ninesmart.ninenews.data.comments.repositories.ICommentsRepository;
import cn.ninesmart.ninenews.data.users.models.UserModel;
import cn.ninesmart.ninenews.utils.DeviceUtils;

/**
 * Author   : perqin
 * Date     : 17-3-14
 */

public class ArticleCommentsPresenter implements ArticleCommentsContract.Presenter {
    private IAuthRepository mAuthRepository;
    private ICommentsRepository mCommentsRepository;
    private ArticleCommentsContract.View mView;

    public ArticleCommentsPresenter(IAuthRepository authRepository, ICommentsRepository commentsRepository, ArticleCommentsContract.View view) {
        mAuthRepository = authRepository;
        mCommentsRepository = commentsRepository;
        mView = view;
    }

    @Override
    public void reloadArticleComments(String articleId) {
        mCommentsRepository.getCommentsByArticleId(articleId).subscribe(commentModels -> {
            if (commentModels.isEmpty()) {
                mView.refreshArticleComments(commentModels, -1, -1);
            } else {
                mView.refreshArticleComments(commentModels, commentModels.get(0).pager.last_dateline, commentModels.get(0).pager.next_page);
            }
        }, Throwable::printStackTrace);
    }

    @Override
    public void loadMoreArticleComments(String articleId, long lastDateline, int nextPage) {
        if (lastDateline < 0 || nextPage < 0) return;
        mCommentsRepository.getMoreCommentsByArticleId(articleId, lastDateline, nextPage).subscribe(commentModels -> {
            if (!commentModels.isEmpty()) {
                mView.appendArticleComments(commentModels, commentModels.get(0).pager.last_dateline, commentModels.get(0).pager.next_page);
            }
        }, Throwable::printStackTrace);
    }

    @Override
    public void postCommentToTarget(String targetId, String content) {
        if (content.isEmpty()) {
            mView.showCommentBlankError();
            return;
        }
        if (!mAuthRepository.isLoggedIn()) {
            mView.showUserNotLoggedInError();
            return;
        }
        String auth;
        try {
            auth = URLDecoder.decode(mAuthRepository.getAuth().getAuth(), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            auth = mAuthRepository.getAuth().getAuth();
        }
        mCommentsRepository.postCommentToTarget(auth, targetId, content, DeviceUtils.getDeviceName()).subscribe(aVoid -> {
            CommentModel commentModel = new CommentModel();
            commentModel.setContent(content);
            UserModel userModel = new UserModel();
            commentModel.setAuthor(userModel);
            mView.postCommentSuccessfully(commentModel);
        }, throwable -> {
            throwable.printStackTrace();
            mView.showPostCommentFailure();
        });
    }
}
