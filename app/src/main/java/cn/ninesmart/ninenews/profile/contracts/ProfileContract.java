package cn.ninesmart.ninenews.profile.contracts;

import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.users.models.UserModel;

/**
 * Author   : perqin
 * Date     : 17-1-16
 */

public interface ProfileContract {
    interface Presenter {
        void reloadProfile();

        void signOut();
    }

    interface View extends BaseView<Presenter> {
        void refreshProfile(UserModel userModel);

        void finishSigningOut();
    }
}
