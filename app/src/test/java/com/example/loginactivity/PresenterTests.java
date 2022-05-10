package com.example.loginactivity;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class PresenterTests {

    LoginActivityMVP.Model mockLoginModel;
    LoginActivityMVP.View mockLoginView;
    LoginActivityPresenter presenter;
    User user;

    @Before
    public void setup(){

        mockLoginModel = mock(LoginActivityMVP.Model.class);
        mockLoginView = mock(LoginActivityMVP.View.class);
        user = new User("Fox", "Mulder");

        presenter = new LoginActivityPresenter(mockLoginModel);
        presenter.setView(mockLoginView);

    }

    //TODO : Test fails, rename this
    @Test
    public void noInteractionWithView(){

        presenter.setView(null);

        presenter.getCurrentUser();

        verifyZeroInteractions(mockLoginView);
    }

    @Test
    public void loadUserFromRepository() {
        when(mockLoginModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();
        verify(mockLoginModel,times(1)).getUser();
        verify(mockLoginView,times(1)).setFirstName("Fox");
        verify(mockLoginView,times(1)).setLastName("Mulder");
        verify(mockLoginView,never()).showUserNotAvailable();

    }

    @Test
    public void testShowUserNotAvailable() {
        when(mockLoginModel.getUser()).thenReturn(null);

        presenter.getCurrentUser();
        verify(mockLoginView,never()).setFirstName("Fox");
        verify(mockLoginView,never()).setLastName("Mulder");
        verify(mockLoginView,times(1)).showUserNotAvailable();

    }

    @Test
    public void testShowErrorMessageEmptyFields() {

        when(mockLoginView.getFirstName()).thenReturn(null);

        presenter.loginButtonClicked();

        verify(mockLoginView,times(1)).getFirstName();
        verify(mockLoginView,never()).getLastName();
        verify(mockLoginView,times(1)).showInputError();

        when(mockLoginView.getFirstName()).thenReturn("Fox");
        when(mockLoginView.getLastName()).thenReturn(null);

        presenter.loginButtonClicked();

        verify(mockLoginView,times(2)).getFirstName();
        verify(mockLoginView,times(1)).getLastName();
        verify(mockLoginView,times(2)).showInputError();

    }

    @Test
    public void testShowSuccessMessageOnSaveSuccess() {

        when(mockLoginView.getFirstName()).thenReturn("Fox");
        when(mockLoginView.getLastName()).thenReturn("Mulder");

        presenter.loginButtonClicked();

        verify(mockLoginView,times(1)).showUserSavedMessage();

    }
}
