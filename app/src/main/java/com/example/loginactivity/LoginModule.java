package com.example.loginactivity;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    public LoginActivityMVP.Presenter providePresenter(LoginActivityMVP.Model model){
        return new LoginActivityPresenter(model);
    }

    @Provides
    public LoginActivityMVP.Model provideModel(LoginRepository loginRepository){
        return new LoginModel(loginRepository);
    }

    @Provides
    public LoginRepository providesLoginRepository(){
        return new MemoryRepository();
    }
}
