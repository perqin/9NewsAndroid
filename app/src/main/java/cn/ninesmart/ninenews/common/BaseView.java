package cn.ninesmart.ninenews.common;

/**
 * Author   : perqin
 * Date     : 17-1-12
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showError(int code);
}
