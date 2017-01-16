package cn.ninesmart.ninenews.authpage.contracts;

import cn.ninesmart.ninenews.common.BaseView;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public interface LoginRegisterContract {
    interface Presenter {
        void login(String email, String password);

        void register(String email, String password, String confirmPassword, String nickname);
    }

    interface View extends BaseView<Presenter> {
        void finishLogin();

        void finishRegister();
    }
}
