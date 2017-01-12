package cn.ninesmart.ninenews.articleslist.contracts;

import java.util.List;

import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;

/**
 * Author   : perqin
 * Date     : 17-1-12
 */

public interface NewsListContract {
    interface Presenter {
        void reloadNewsList();

        void loadMoreNewsList(int offset);
    }

    interface View extends BaseView<Presenter> {
        void refreshNewsList(List<ArticleModel> articleModels);

        void appendNewsList(List<ArticleModel> articleModels);
    }
}
