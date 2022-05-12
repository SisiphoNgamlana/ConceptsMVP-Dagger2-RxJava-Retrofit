package com.example.loginactivity.api;

import com.example.loginactivity.api.apimodel.Twitch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

public interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames(@Header("Client-id") String ClientId);

    @GET("games/top")
    Observable<Twitch> getTopGamesObservable(@Header("Client-id") String ClientId);
}