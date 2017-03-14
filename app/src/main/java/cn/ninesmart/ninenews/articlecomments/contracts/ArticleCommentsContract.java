package cn.ninesmart.ninenews.articlecomments.contracts;

import java.util.List;

import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.comments.models.CommentModel;

/**
 * Author   : perqin
 * Date     : 17-3-14
 */

public interface ArticleCommentsContract {
    interface Presenter {
        void reloadArticleComments(String articleId);

        void loadMoreArticleComments(String articleId, long lastDateline, int nextPage);

        void postCommentToTarget(String targetId, String content);
    }

    interface View extends BaseView<Presenter> {
        void refreshArticleComments(List<CommentModel> commentModels, long lastDateline, int nextPage);

        void appendArticleComments(List<CommentModel> commentModels, long lastDateline, int nextPage);

        void postCommentSuccessfully(CommentModel commentModel);

        void showPostCommentFailure();

        void showCommentBlankError();

        void showUserNotLoggedInError();
    }
}
