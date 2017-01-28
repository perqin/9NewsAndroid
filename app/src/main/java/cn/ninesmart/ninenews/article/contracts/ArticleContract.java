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
    }

    interface View extends BaseView<Presenter> {
        void refreshArticle(ArticleModel articleModel);

        void refreshArticleComments(List<CommentModel> commentModels);
    }
}
