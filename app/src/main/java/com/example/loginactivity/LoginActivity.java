package com.example.loginactivity;

import static com.example.loginactivity.ApplicationComponent.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    @Inject
    LoginActivityMVP.Presenter presenter;

    EditText firstNameEditText;
    EditText lastNameEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getApplicationComponent().inject(this);

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