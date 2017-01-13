package cn.ninesmart.ninenews.data.auth.repositories;

import android.content.Context;

import cn.ninesmart.ninenews.data.auth.models.AuthModel;
import cn.ninesmart.ninenews.data.auth.stores.LocalAuthStore;
import cn.ninesmart.ninenews.data.auth.stores.RemoteAuthStore;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class AuthRepository implements IAuthRepository {
    private static AuthRepository sInstance;
    private LocalAuthStore mLocal;
    private RemoteAuthStore mRemote;

    public static AuthRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AuthRepository(context);
        }
        return sInstance;
    }

    private AuthRepository(Context context) {
        mLocal = new LocalAuthStore(context);
        mRemote = new RemoteAuthStore();
    }

    @Override
    public Observable<AuthModel> login(String email, String password) {
        return mRemote.createAuth(email, password)
                .doOnNext(model -> mLocal.updateAuthSync(model));
    }

    @Override
    public AuthModel getAuth() {
        return mLocal.getAuth();
    }
}
