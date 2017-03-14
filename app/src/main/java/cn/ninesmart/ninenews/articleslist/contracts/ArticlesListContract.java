package cn.ninesmart.ninenews.articleslist.contracts;

import java.util.List;

import cn.ninesmart.ninenews.common.BaseView;
import cn.ninesmart.ninenews.data.articles.model.ArticleModel;
import cn.ninesmart.ninenews.data.users.models.UserModel;

/**
 * Author   : perqin
 * Date     : 17-1-12
 */

public interface ArticlesListContract {
    interface Presenter {
        void reloadNewsList();

        void loadMoreNewsList(long lastDateline, int nextPage);

        void updateUserProfile();

        void avatarClick();
    }

    interface View extends BaseView<Presenter> {
        void refreshNewsList(List<ArticleModel> articleModels, long lastDateline, int nextPage);

        void appendNewsList(List<ArticleModel> articleModels, long lastDateline, int nextPage);

        void refreshNotLoggedInUserProfile();

        void refreshLoggedInUserProfile(UserModel userModel);

        void showLoginRegisterPage();

        void showUserProfilePage();
    }
}
