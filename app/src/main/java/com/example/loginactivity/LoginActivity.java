package com.example.loginactivity;

import static com.example.loginactivity.ApplicationComponent.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginactivity.api.TwitchAPI;
import com.example.loginactivity.api.apimodel.Top;
import com.example.loginactivity.api.apimodel.Twitch;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    @Inject
    LoginActivityMVP.Presenter presenter;

    @Inject
    TwitchAPI twitchAPI;

    EditText firstNameEditText;
    EditText lastNameEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getApplicationComponent().inject(this);

        /*
        This is the service call without RxJava.
        Call<Twitch> call = twitchAPI.getTopGames("Some header here");
        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Top> gamesList = response.body().getTop();
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                //TODO : Show network failure message
                t.printStackTrace();
            }
        });*/

        //This is the API call using RxJava
        twitchAPI.getTopGamesObservable("HeaderString")
                .flatMap(new Func1<Twitch, Observable<Top>>() {
                    @Override
                    public Observable<Top> call(Twitch twitch) {
                        return Observable.from(twitch.getTop());
                    }
                }).flatMap(new Func1<Top, Observable<String>>() {
            @Override
            public Observable<String> call(Top top) {
                return Observable.just(top.getGame().getName());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });

        firstNameEditText = findViewById(R.id.first_name_edittext);
        lastNameEditText = findViewById(R.id.last_name_edittext);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loginButtonClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getFirstName() {
        return firstNameEditText.getText().toString().trim();
    }

    @Override
    public String getLastName() {
        return lastNameEditText.getText().toString().trim();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "Error: The user is not available",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Error: The Firstname and Lastname cannot be empty",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(this, "Success: User saved",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFirstName(String firstName) {
        firstNameEditText.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        lastNameEditText.setText(lastName);
    }
}