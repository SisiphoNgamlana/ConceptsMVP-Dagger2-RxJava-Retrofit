package com.example.loginactivity;

import android.app.Application;

import com.example.loginactivity.api.apimodel.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class, ApiModule.class})
public interface ApplicationComponent {

    class App extends Application {
        /*
        This is another way to create the application component/ applicationGraph
        private ApplicationComponent applicationComponent = DaggerApplicationComponent.create();
        */
        private ApplicationComponent applicationComponent;

        @Override
        public void onCreate() {
            super.onCreate();

            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .loginModule(new LoginModule())
                    .apiModule(new ApiModule())
                    .build();
        }

        public ApplicationComponent getApplicationComponent() {
            return applicationComponent;
        }
    }

    void inject(LoginActivity target);
}
