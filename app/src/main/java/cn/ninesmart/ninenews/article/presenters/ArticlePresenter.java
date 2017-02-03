package cn.ninesmart.ninenews.article.presenters;

import cn.ninesmart.ninenews.article.contracts.ArticleContract;
import cn.ninesmart.ninenews.data.articles.repositories.IArticlesRepository;
import cn.ninesmart.ninenews.data.comments.repositories.ICommentsRepository;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public class ArticlePresenter implements ArticleContract.Presenter {
    private IArticlesRepository mArticlesRepository;
    private ICommentsRepository mCommentsRepository;
    private ArticleContract.View mView;

    public ArticlePresenter(IArticlesRepository articlesRepository, ICommentsRepository commentsRepository, ArticleContract.View view) {
        mArticlesRepository = articlesRepository;
        mCommentsRepository = commentsRepository;
        mView = view;
    }

    @Override
    public void reloadArticle(String articleId) {
        mArticlesRepository.getArticle(articleId).subscribe(
                articleModel -> mView.refreshArticle(articleModel),
                Throwable::printStackTrace);
    }

    @Override
    public void reloadArticleComments(String articleId) {
        mCommentsRepository.getCommentsByArticleId(articleId).subscribe(commentModels -> {
            if (commentModels.isEmpty()) {
                mView.refreshArticleComments(commentModels, -1, -1);
            } else {
                mView.refreshArticleComments(commentModels, commentModels.get(0).pager.last_dateline, commentModels.get(0).pager.next_page);
            }
        }, Throwable::printStackTrace);
    }

    @Override
    public void loadMoreArticleComments(String articleId, long lastDateline, int nextPage) {
        if (lastDateline < 0 || nextPage < 0) return;
        mCommentsRepository.getMoreCommentsByArticleId(articleId, lastDateline, nextPage).subscribe(commentModels -> {
            if (!commentModels.isEmpty()) {
                mView.appendArticleComments(commentModels, commentModels.get(0).pager.last_dateline, commentModels.get(0).pager.next_page);
            }
        }, Throwable::printStackTrace);
    }
}
