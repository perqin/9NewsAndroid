package cn.ninesmart.ninenews.data.users.repositories;

import cn.ninesmart.ninenews.data.users.models.UserModel;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public interface IUsersRepository {
    Observable<UserModel> getUser(String userId);
}
