package cn.ninesmart.ninenews.authpage.presenters;

import cn.ninesmart.ninenews.authpage.contracts.LoginRegisterContract;
import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public class LoginRegisterPresenter implements LoginRegisterContract.Presenter {
    private IAuthRepository mAuthRepository;
    private LoginRegisterContract.View mView;

    public LoginRegisterPresenter(IAuthRepository authRepository, LoginRegisterContract.View view) {
        mAuthRepository = authRepository;
        mView = view;
    }
    @Override
    public void login(String email, String password) {
        mAuthRepository.login(email, password).subscribe(model -> mView.finishLogin(), Throwable::printStackTrace);
    }

    @Override
    public void register(String email, String password, String confirmPassword, String nickname) {
        mAuthRepository.register(email, password, confirmPassword, nickname).subscribe(
                model -> mView.finishRegister(),
                Throwable::printStackTrace
        );
    }
}
