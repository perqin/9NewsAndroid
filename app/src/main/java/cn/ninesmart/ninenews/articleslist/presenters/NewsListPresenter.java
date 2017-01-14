package cn.ninesmart.ninenews.articleslist.presenters;

import cn.ninesmart.ninenews.articleslist.contracts.NewsListContract;
import cn.ninesmart.ninenews.data.articles.repositories.IArticlesRepository;
import cn.ninesmart.ninenews.data.auth.models.AuthModel;
import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;
import cn.ninesmart.ninenews.data.users.repositories.IUsersRepository;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    private IAuthRepository mAuthRepository;
    private IArticlesRepository mArticlesRepository;
    private IUsersRepository mUsersRepository;
    private NewsListContract.View mView;

    public NewsListPresenter(IAuthRepository authRepository, IArticlesRepository articlesRepository,
                             IUsersRepository usersRepository, NewsListContract.View view) {
        mAuthRepository = authRepository;
        mArticlesRepository = articlesRepository;
        mUsersRepository = usersRepository;
        mView = view;
    }

    @Override
    public void reloadNewsList() {
        mArticlesRepository.getLatestArticlesList().subscribe(
                articleModels -> mView.refreshNewsList(articleModels),
                Throwable::printStackTrace);
    }

    @Override
    public void loadMoreNewsList(int offset) {
        // TODO
        throw new RuntimeException("Method not implemented: cn.ninesmart.ninenews.newslist.presenters.NewsListPresenter");
    }

    @Override
    public void updateUserProfile() {
        AuthModel authModel = mAuthRepository.getAuth();
        if (authModel == null) {
            mView.refreshNotLoggedInUserProfile();
        } else {
            mUsersRepository.getUser(authModel.getUid()).subscribe(
                    userModel -> mView.refreshLoggedInUserProfile(userModel),
                    Throwable::printStackTrace);
        }
    }

    @Override
    public void avatarClick() {
        if (mAuthRepository.isLoggedIn()) {
            mView.showUserProfilePage();
        } else {
            mView.showLoginRegisterPage();
        }
    }
}
