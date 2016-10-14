package com_t.macvision.mv_078.presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
