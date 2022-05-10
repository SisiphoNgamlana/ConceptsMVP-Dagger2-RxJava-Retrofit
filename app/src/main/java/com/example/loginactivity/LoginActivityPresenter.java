package com.example.loginactivity;


import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

public class LoginActivityPresenter implements LoginActivityMVP.Presenter {

    @Nullable
    private LoginActivityMVP.View view;
    private final LoginActivityMVP.Model model;

    public LoginActivityPresenter(LoginActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(LoginActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        if (view != null) {
            if (StringUtils.isEmpty(view.getFirstName()) || StringUtils.isEmpty(view.getLastName())) {
                view.showInputError();
            } else {
                model.createUser(view.getFirstName(), view.getLastName());
                view.showUserSavedMessage();
            }
        }
    }

    @Override
    public void getCurrentUser() {
        if (view != null) {
            User user = model.getUser();
            if (user != null) {
                view.setFirstName(user.getName());
                view.setLastName(user.getLastName());
            } else {
                view.showUserNotAvailable();
            }
        }
    }
}
