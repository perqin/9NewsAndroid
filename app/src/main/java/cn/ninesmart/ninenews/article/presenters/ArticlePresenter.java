package cn.ninesmart.ninenews.article.presenters;

import cn.ninesmart.ninenews.article.contracts.ArticleContract;
import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.articles.repositories.IArticlesRepository;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public class ArticlePresenter implements ArticleContract.Presenter {
    private IArticlesRepository mArticlesRepository;
    private ArticleContract.View mView;

    public ArticlePresenter(IArticlesRepository articlesRepository, ArticleContract.View view) {
        mArticlesRepository = articlesRepository;
        mView = view;
    }

    @Override
    public void reloadArticle(String articleId) {
        mView.showLoading(true, BaseView.LOADING_DEFAULT);
        mArticlesRepository.getArticle(articleId).subscribe(articleModel -> {
            mView.refreshArticle(articleModel);
            mView.showLoading(false, BaseView.LOADING_DEFAULT);
        }, Throwable::printStackTrace);
    }
}
