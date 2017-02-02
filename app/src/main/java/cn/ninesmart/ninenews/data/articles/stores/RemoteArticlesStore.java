package cn.ninesmart.ninenews.data.articles.stores;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cn.ninesmart.ninenews.common.ApplySchedulers;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.network.body.GetArticlesRes;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.data.network.retrofit.services.NineNewsService;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class RemoteArticlesStore {
    private NineNewsService mNineNewsService;

    public RemoteArticlesStore() {
        mNineNewsService = ApiFactory.getInstance().getNineNewsService();
    }

    public Observable<List<ArticleModel>> getLatestArticlesList() {
        return mNineNewsService.getArticles().compose(ApplySchedulers.network())
                .map(this::parseArticlesListBody);
    }

    public Observable<List<ArticleModel>> getMoreArticlesList(long lastDateline, int nextPage) {
        return mNineNewsService.getMoreArticles(lastDateline, nextPage).compose(ApplySchedulers.network())
                .map(this::parseArticlesListBody);
    }

    public Observable<ArticleModel> getArticleById(String articleId) {
        return mNineNewsService.getArticleById(articleId).compose(ApplySchedulers.network()).map(res -> {
            ArticleModel model = new ArticleModel();
            model.setArticleId(res.article._id);
            model.setTopic(res.article.topic);
            model.setContent(res.article.content);
            if (!res.article.attachments.isEmpty()) {
                model.setCoverHdSrc(Uri.parse(res.article.attachments.get(0).url));
            }
            return model;
        });
    }

    private List<ArticleModel> parseArticlesListBody(GetArticlesRes res) {
        List<ArticleModel> articles = new ArrayList<>();
        for (GetArticlesRes.Article a : res.articles) {
            ArticleModel article = new ArticleModel();
            article.setArticleId(a._id);
            article.setTopic(a.topic);
            article.setSummary(a.summary);
            article.setCategory(a.category);
            article.setCommentCount(a.comments);
            article.setViewCount(a.views);
            if (!a.attachments.isEmpty()) {
                article.setCoverThumbSrc(Uri.parse(a.attachments.get(0).thumb));
            }
            // FIXME: Need better solution to deal with such paging API
            article.pager = res.pager;
            articles.add(article);
        }
        return articles;
    }
}
