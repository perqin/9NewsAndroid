package cn.ninesmart.ninenews.articleslist.presenters;

import cn.ninesmart.ninenews.articleslist.contracts.ArticlesListContract;
import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.articles.repositories.IArticlesRepository;
import cn.ninesmart.ninenews.data.auth.models.AuthModel;
import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;
import cn.ninesmart.ninenews.data.users.repositories.IUsersRepository;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class ArticlesListPresenter implements ArticlesListContract.Presenter {
    private IAuthRepository mAuthRepository;
    private IArticlesRepository mArticlesRepository;
    private IUsersRepository mUsersRepository;
    private ArticlesListContract.View mView;

    public ArticlesListPresenter(IAuthRepository authRepository, IArticlesRepository articlesRepository,
                                 IUsersRepository usersRepository, ArticlesListContract.View view) {
        mAuthRepository = authRepository;
        mArticlesRepository = articlesRepository;
        mUsersRepository = usersRepository;
        mView = view;
    }

    @Override
    public void reloadNewsList() {
        mView.showLoading(true, BaseView.LOADING_DEFAULT);
        mArticlesRepository.getLatestArticlesList().subscribe(articleModels -> {
            if (articleModels.isEmpty()) {
                mView.refreshNewsList(articleModels, -1, -1);
            } else {
                mView.refreshNewsList(articleModels, articleModels.get(0).pager.last_dateline, articleModels.get(0).pager.next_page);
            }
            mView.showLoading(false, BaseView.LOADING_DEFAULT);
        }, Throwable::printStackTrace);
    }

    @Override
    public void loadMoreNewsList(long lastDateline, int nextPage) {
        if (lastDateline < 0 || nextPage < 0) return;
        mArticlesRepository.getMoreArticlesList(lastDateline, nextPage).subscribe(articleModels -> {
            if (!articleModels.isEmpty()) {
                mView.appendNewsList(articleModels, articleModels.get(0).pager.last_dateline, articleModels.get(0).pager.next_page);
            }
        }, Throwable::printStackTrace);
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
