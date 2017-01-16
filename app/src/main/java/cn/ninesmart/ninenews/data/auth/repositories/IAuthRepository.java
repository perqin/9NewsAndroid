package cn.ninesmart.ninenews.data.auth.repositories;

import cn.ninesmart.ninenews.data.auth.models.AuthModel;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public interface IAuthRepository {
    Observable<AuthModel> login(String email, String password);

    AuthModel getAuth();

    boolean isLoggedIn();

    Observable<Void> removeAuth();
}
