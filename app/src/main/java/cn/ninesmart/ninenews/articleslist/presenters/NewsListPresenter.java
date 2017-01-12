package cn.ninesmart.ninenews.articleslist.presenters;

import cn.ninesmart.ninenews.articleslist.contracts.NewsListContract;
import cn.ninesmart.ninenews.data.articles.repositories.IArticlesRepository;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    private IArticlesRepository mArticlesRepository;
    private NewsListContract.View mView;

    public NewsListPresenter(IArticlesRepository articlesRepository, NewsListContract.View view) {
        mArticlesRepository = articlesRepository;
        mView = view;
    }

    @Override
    public void reloadNewsList() {
        mArticlesRepository.getLatestArticlesList().subscribe(articleModels -> {
            mView.refreshNewsList(articleModels);
        }, throwable -> {
            throwable.printStackTrace();
        });
    }

    @Override
    public void loadMoreNewsList(int offset) {
        // TODO
        throw new RuntimeException("Method not implemented: cn.ninesmart.ninenews.newslist.presenters.NewsListPresenter");
    }
}
