package cn.ninesmart.ninenews.data.articles.repositories;

import java.util.List;

import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.articles.stores.RemoteArticlesStore;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.data.network.retrofit.services.NineNewsService;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class ArticlesRepository implements IArticlesRepository {
    private static ArticlesRepository sInstance;

    private NineNewsService mNineNewsService;

    private RemoteArticlesStore mRemote;

    public static ArticlesRepository getInstance() {
        if (sInstance == null) {
            sInstance = new ArticlesRepository();
        }
        return sInstance;
    }

    private ArticlesRepository() {
        mNineNewsService = ApiFactory.getInstance().getNineNewsService();
    }

    @Override
    public Observable<List<ArticleModel>> getLatestArticlesList() {
        // TODO
        throw new RuntimeException("Method not implemented: cn.ninesmart.ninenews.data.articles.repositories.ArticlesRepository");
    }

    @Override
    public Observable<List<ArticleModel>> getMoreArticlesList(long lastArticleTimeline, int page) {
        // TODO
        throw new RuntimeException("Method not implemented: cn.ninesmart.ninenews.data.articles.repositories.ArticlesRepository");
    }
}
