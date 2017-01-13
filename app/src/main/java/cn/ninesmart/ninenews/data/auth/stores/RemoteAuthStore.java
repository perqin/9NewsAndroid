package cn.ninesmart.ninenews.data.auth.stores;

import cn.ninesmart.ninenews.common.ApplySchedulers;
import cn.ninesmart.ninenews.data.auth.models.AuthModel;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.data.network.retrofit.services.NineNewsService;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class RemoteAuthStore {
    private NineNewsService mNineNewsService;

    public RemoteAuthStore() {
        mNineNewsService = ApiFactory.getInstance().getNineNewsService();
    }

    public Observable<AuthModel> createAuth(String email, String password) {
        return mNineNewsService.login(email, password).compose(ApplySchedulers.network()).map(res -> {
            AuthModel model = new AuthModel();
            model.setUid(res.uid);
            model.setAuth(res.auth);
            return model;
        });
    }
}
