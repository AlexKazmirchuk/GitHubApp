package com.alexkaz.githubapp.model.entities;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ShortUserEntity extends RealmObject {

    @SerializedName("login")
    private String login;

    @SerializedName("id")
    private int id;

    @SerializedName("avatar_url")
    private String avatarUrl;

    private int changesCount;

    public ShortUserEntity() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getChangesCount() {
        return changesCount;
    }

    public void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
    }
}
