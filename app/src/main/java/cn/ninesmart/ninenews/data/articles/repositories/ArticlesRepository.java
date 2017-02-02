package cn.ninesmart.ninenews.data.articles.repositories;

import java.util.List;

import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.articles.stores.RemoteArticlesStore;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class ArticlesRepository implements IArticlesRepository {
    private static ArticlesRepository sInstance;

    private RemoteArticlesStore mRemote;

    public static ArticlesRepository getInstance() {
        if (sInstance == null) {
            sInstance = new ArticlesRepository();
        }
        return sInstance;
    }

    private ArticlesRepository() {
        mRemote = new RemoteArticlesStore();
    }

    @Override
    public Observable<List<ArticleModel>> getLatestArticlesList() {
        return mRemote.getLatestArticlesList();
    }

    @Override
    public Observable<List<ArticleModel>> getMoreArticlesList(long lastDateline, int page) {
        return mRemote.getMoreArticlesList(lastDateline, page);
    }

    @Override
    public Observable<ArticleModel> getArticle(String articleId) {
        return mRemote.getArticleById(articleId);
    }
}
