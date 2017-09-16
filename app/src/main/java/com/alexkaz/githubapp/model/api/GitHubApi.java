package com.alexkaz.githubapp.model.api;

import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubApi {

    String CLIENT_ID = "client_id=61596873f6b9905c0cba";
    String CLIENT_SECRET = "client_secret=88a821fb26d31d2a73df042a0dd57f38e6b40424";
    String WITH_ID_AND_SECRET = "?" + CLIENT_ID + "&" + CLIENT_SECRET;

    String END_POINT = "https://api.github.com/";

    @GET("users" + WITH_ID_AND_SECRET)
    Observable<List<ShortUserEntity>> getUsers(@Query("since") int since, @Query("per_page") int perPage);

    @GET("users/{username}" + WITH_ID_AND_SECRET)
    Observable<UserEntity> getUser(@Path("username") String username);

    @GET("users/{username}/repos" + WITH_ID_AND_SECRET)
    Observable<List<RepoEntity>> getUserRepos(@Path("username") String username, @Query("page") int page, @Query("per_page") int perPage);
}
