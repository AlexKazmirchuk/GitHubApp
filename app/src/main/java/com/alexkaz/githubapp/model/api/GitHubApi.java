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

    String END_POINT = "https://api.github.com/";

    @GET("users")
    Observable<List<ShortUserEntity>> getUsers(@Query("since") int since, @Query("per_page") int perPage);

    @GET("users/{user}")
    Observable<UserEntity> getUser(@Path("user") String user);

    @GET("users/{user}/repos")
    Observable<List<RepoEntity>> getUserRepos(@Path("user") String user, @Query("page") int page, @Query("per_page") int perPage);
}
