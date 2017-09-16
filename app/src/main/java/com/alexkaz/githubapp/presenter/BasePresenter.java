package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.view.BaseView;

public interface BasePresenter<T extends BaseView> {

    void bindView(T view);

}
