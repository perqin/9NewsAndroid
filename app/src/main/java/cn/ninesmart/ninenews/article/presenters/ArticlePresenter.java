package cn.ninesmart.ninenews.article.presenters;

import cn.ninesmart.ninenews.article.contracts.ArticleContract;
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
        mArticlesRepository.getArticle(articleId).subscribe(
                articleModel -> mView.refreshArticle(articleModel),
                Throwable::printStackTrace);
    }
}
