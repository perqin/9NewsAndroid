package cn.ninesmart.ninenews.data.users.stores;

import android.net.Uri;

import cn.ninesmart.ninenews.common.ApplySchedulers;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.data.network.retrofit.services.NineNewsService;
import cn.ninesmart.ninenews.data.users.models.UserModel;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public class RemoteUsersStore {
    private final NineNewsService mNineNewsService;

    public RemoteUsersStore() {
        mNineNewsService = ApiFactory.getInstance().getNineNewsService();
    }

    public Observable<UserModel> getUserById(String userId) {
        return mNineNewsService.getUserById(userId).compose(ApplySchedulers.network()).map(res -> {
            UserModel model = new UserModel();
            model.setUserId(res.member._id);
            model.setNickname(res.member.nickname);
            model.setLevel(res.member.group);
            model.setAvatarThumbSrc(Uri.parse(res.member.avatar));
            return model;
        });
    }
}
