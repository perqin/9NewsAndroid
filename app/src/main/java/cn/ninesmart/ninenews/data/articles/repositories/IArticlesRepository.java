package cn.ninesmart.ninenews.data.articles.repositories;

import java.util.List;

import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public interface IArticlesRepository {
    Observable<List<ArticleModel>> getLatestArticlesList();

    Observable<List<ArticleModel>> getMoreArticlesList(long lastArticleTimeline, int page);
}
