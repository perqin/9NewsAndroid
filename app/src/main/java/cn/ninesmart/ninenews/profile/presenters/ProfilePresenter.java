package cn.ninesmart.ninenews.profile.presenters;

import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;
import cn.ninesmart.ninenews.data.users.repositories.IUsersRepository;
import cn.ninesmart.ninenews.profile.contracts.ProfileContract;

/**
 * Author   : perqin
 * Date     : 17-1-16
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private IAuthRepository mAuthRepository;
    private IUsersRepository mUsersRepository;
    private ProfileContract.View mView;

    public ProfilePresenter(IAuthRepository authRepository, IUsersRepository usersRepository, ProfileContract.View view) {
        this.mAuthRepository = authRepository;
        this.mUsersRepository = usersRepository;
        this.mView = view;
    }

    @Override
    public void reloadProfile() {
        mUsersRepository.getUser(mAuthRepository.getAuth().getUid()).subscribe(
                userModel -> mView.refreshProfile(userModel),
                Throwable::printStackTrace
        );
    }

    @Override
    public void signOut() {
        mAuthRepository.removeAuth().subscribe(
                aVoid -> mView.finishSigningOut(),
                Throwable::printStackTrace
        );
    }
}
