package com.alexkaz.githubapp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.alexkaz.githubapp.view.UsersActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null && !remoteMessage.getData().isEmpty()){
            String userId = remoteMessage.getData().get("userId");
            String  changesCount = remoteMessage.getData().get("changesCount");
            if (userId != null && changesCount != null){
                Intent intent = new Intent(UsersActivity.PUSH_NOTIFICATION);
                intent.putExtra("userId",userId);
                intent.putExtra("changesCount",changesCount);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } else {
                Log.d("myTag", "Server error");
            }
        }

    }

}
