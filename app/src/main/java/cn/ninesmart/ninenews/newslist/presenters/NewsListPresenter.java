package cn.ninesmart.ninenews.newslist.presenters;

import cn.ninesmart.ninenews.newslist.contracts.NewsListContract;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    @Override
    public void reloadNewsList() {
        // TODO
        throw new RuntimeException("Method not implemented: cn.ninesmart.ninenews.newslist.presenters.NewsListPresenter");
    }

    @Override
    public void loadMoreNewsList(int offset) {
        // TODO
        throw new RuntimeException("Method not implemented: cn.ninesmart.ninenews.newslist.presenters.NewsListPresenter");
    }
}
