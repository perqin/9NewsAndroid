package cn.ninesmart.ninenews.article.contracts;

import java.util.List;

import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.comments.models.CommentModel;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public interface ArticleContract {
    interface Presenter {
        void reloadArticle(String articleId);

        void reloadArticleComments(String articleId);

        void loadMoreArticleComments(String articleId, long lastDateline, int nextPage);

        void postCommentToTarget(String targetId, String content);
    }

    interface View extends BaseView<Presenter> {
        void refreshArticle(ArticleModel articleModel);

        void refreshArticleComments(List<CommentModel> commentModels, long lastDateline, int nextPage);

        void appendArticleComments(List<CommentModel> commentModels, long lastDateline, int nextPage);

        void postCommentSuccessfully(CommentModel commentModel);

        void showPostCommentFailure();

        void showCommentBlankError();

        void showUserNotLoggedInError();
    }
}
