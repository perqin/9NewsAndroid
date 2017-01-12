package cn.ninesmart.ninenews.newslist.contracts;

import cn.ninesmart.ninenews.common.BaseView;

/**
 * Author   : perqin
 * Date     : 17-1-12
 */

public interface NewsListContract {
    interface Presenter {
        void reloadNewsList();

        void loadMoreNewsList(int offset);
    }

    interface View extends BaseView<Presenter> {
        void refreshNewsList();

        void appendNewsList();
    }
}
