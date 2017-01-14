package cn.ninesmart.ninenews.article.contracts;

import cn.ninesmart.ninenews.common.BaseView;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public interface ArticleContract {
    interface Presenter {}

    interface View extends BaseView<Presenter> {}
}
