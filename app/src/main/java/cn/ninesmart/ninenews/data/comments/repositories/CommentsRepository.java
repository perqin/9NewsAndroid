package cn.ninesmart.ninenews.data.comments.repositories;

import java.util.List;

import cn.ninesmart.ninenews.data.comments.models.CommentModel;
import cn.ninesmart.ninenews.data.comments.stores.RemoteCommentsStore;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-28
 */

public class CommentsRepository implements ICommentsRepository {
    private static CommentsRepository sInstance;

    private RemoteCommentsStore mRemote;

    public static CommentsRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CommentsRepository();
        }
        return sInstance;
    }

    private CommentsRepository() {
        mRemote = new RemoteCommentsStore();
    }

    @Override
    public Observable<List<CommentModel>> getCommentsByArticleId(String articleId) {
        return mRemote.getCommentsByArticleId(articleId);
    }
}
