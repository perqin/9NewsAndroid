package cn.ninesmart.ninenews.common;

/**
 * Author   : perqin
 * Date     : 17-1-12
 */

public interface BaseView<T> {
    int LOADING_DEFAULT = 0;

    void setPresenter(T presenter);

    void showError(int code);

    void showLoading(boolean isLoading, int target);
}
