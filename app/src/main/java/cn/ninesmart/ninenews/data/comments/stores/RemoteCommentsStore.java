package cn.ninesmart.ninenews.data.comments.stores;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cn.ninesmart.ninenews.common.ApplySchedulers;
import cn.ninesmart.ninenews.data.comments.models.CommentModel;
import cn.ninesmart.ninenews.data.network.body.GetCommentsIdRes;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.data.network.retrofit.services.NineNewsService;
import cn.ninesmart.ninenews.data.users.models.UserModel;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-28
 */

public class RemoteCommentsStore {
    private NineNewsService mService;

    public RemoteCommentsStore() {
        mService = ApiFactory.getInstance().getNineNewsService();
    }

    public Observable<List<CommentModel>> getCommentsByArticleId(String articleId) {
        return mService.getCommentsByArticleId(articleId).compose(ApplySchedulers.network()).map(res -> {
            ArrayList<CommentModel> commentModels = new ArrayList<>();
            for (GetCommentsIdRes.Comment comment : res.comments) {
                CommentModel commentModel = new CommentModel();
                commentModel.setCommentId(comment.id);
                commentModel.setContent(comment.content);
                UserModel userModel = new UserModel();
                userModel.setUserId(comment.user._id);
                userModel.setNickname(comment.user.nickname);
                userModel.setAvatarThumbSrc(Uri.parse(comment.user.avatar));
                commentModel.setAuthor(userModel);
                commentModels.add(commentModel);
            }
            return commentModels;
        });
    }
}
