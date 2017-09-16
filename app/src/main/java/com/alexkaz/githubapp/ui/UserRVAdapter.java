package com.alexkaz.githubapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexkaz.githubapp.R;
import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.view.UserReposActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.UserVH> {

    private List<ShortUserEntity> users = new ArrayList<>();

    public UserRVAdapter(List<ShortUserEntity> users) {
        this.users = users;
    }

    public UserRVAdapter() {
    }

    public void add(List<ShortUserEntity> users){
        if (users != null){
            this.users.addAll(users);
            notifyDataSetChanged();
        }
    }

    public List<ShortUserEntity> getItems(){
        return users;
    }

    public void clear(){
        users.clear();
        notifyDataSetChanged();
    }

    @Override
    public UserVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserVH(v);
    }

    @Override
    public void onBindViewHolder(UserVH holder, int position) {
        ShortUserEntity user = users.get(position);
        Picasso.with(holder.context)
                .load(user.getAvatarUrl())
                .transform(new CircleTransform())
                .into(holder.userPhotoIV);
        holder.userNameTV.setText(user.getLogin());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserVH extends RecyclerView.ViewHolder {

        private Context context;

        private ImageView userPhotoIV;
        private TextView userNameTV;

        UserVH(View v) {
            super(v);
            context = v.getContext();
            userPhotoIV = v.findViewById(R.id.userPhotoIV);
            userNameTV = v.findViewById(R.id.userNameTV);
            v.findViewById(R.id.relativeLayout).setOnClickListener(event -> {
                Intent intent = new Intent(context, UserReposActivity.class);
                intent.putExtra("userName", userNameTV.getText().toString());
                context.startActivity(intent);
            });
        }
    }
}
