package cn.ninesmart.ninenews.article.contracts;

import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public interface ArticleContract {
    interface Presenter {
        void reloadArticle(String articleId);
    }

    interface View extends BaseView<Presenter> {
        void refreshArticle(ArticleModel articleModel);
    }
}
