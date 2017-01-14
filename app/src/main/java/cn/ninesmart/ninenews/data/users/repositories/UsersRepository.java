package cn.ninesmart.ninenews.data.users.repositories;

import cn.ninesmart.ninenews.data.users.models.UserModel;
import cn.ninesmart.ninenews.data.users.stores.RemoteUsersStore;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public class UsersRepository implements IUsersRepository {
    private static UsersRepository sInstance;
    private RemoteUsersStore mRemote;

    public static UsersRepository getInstance() {
        if (sInstance == null) {
            sInstance = new UsersRepository();
        }
        return sInstance;
    }

    private UsersRepository() {
        mRemote = new RemoteUsersStore();
    }

    @Override
    public Observable<UserModel> getUser(String userId) {
        return mRemote.getUserById(userId);
    }
}
