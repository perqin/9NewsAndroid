package cn.ninesmart.ninenews.data.comments.repositories;

import java.util.List;

import cn.ninesmart.ninenews.data.comments.models.CommentModel;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-28
 */

public interface ICommentsRepository {
    Observable<List<CommentModel>> getCommentsByArticleId(String articleId);

    Observable<List<CommentModel>> getMoreCommentsByArticleId(String articleId, long lastDateline, int nextPage);

    Observable<Void> postCommentToArticle(String token, String articleId, String content, String model);
}
