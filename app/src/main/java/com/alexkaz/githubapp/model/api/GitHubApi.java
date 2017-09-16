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

    @GET("users/{username}")
    Observable<UserEntity> getUser(@Path("username") String username);

    @GET("users/{username}/repos")
    Observable<List<RepoEntity>> getUserRepos(@Path("username") String username, @Query("page") int page, @Query("per_page") int perPage);
}
