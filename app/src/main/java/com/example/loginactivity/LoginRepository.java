package com.example.loginactivity;

public interface LoginRepository {

    User getUser();

    void saveUser(User user);
}
