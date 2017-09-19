package com.alexkaz.githubapp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.alexkaz.githubapp.MyApp;
import com.alexkaz.githubapp.R;
import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.presenter.UsersPresenter;
import com.alexkaz.githubapp.ui.CustomToast;
import com.alexkaz.githubapp.ui.UserRVAdapter;
import com.paginate.Paginate;

import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class UsersActivity extends AppCompatActivity implements UsersView {

    public static final String PUSH_NOTIFICATION = "pushNotification";

    @Inject
    UsersPresenter presenter;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private UserRVAdapter adapter;
    private View noConnView;

    private BroadcastReceiver receiver;

    private boolean loadingInProgress = false;
    private boolean hasLoadedAllItems = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ((MyApp)getApplication()).getMyComponent().inject(this);
        initComponents();
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent != null){
            try {
                int userId = Integer.parseInt(intent.getStringExtra("userId"));
                int changesCount = Integer.parseInt(intent.getStringExtra("changesCount"));
                adapter.updateItemChangesCount(userId, changesCount);
            } catch (NumberFormatException e){
                Log.d("myTag", "wrong data from server");
            }
        }
    }

    private void initComponents() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycleView);
        noConnView = findViewById(R.id.noConnLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserRVAdapter(((MyApp)getApplication()).getMyComponent().realmHelper());
        recyclerView.setAdapter( new ScaleInAnimationAdapter( new AlphaInAnimationAdapter(adapter)));

        presenter.bindView(this);
        presenter.restoreState();

        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                if (adapter.getItemCount() > 0){
                    presenter.loadNextPage();
                    loadingInProgress = true;
                }
            }

            @Override
            public boolean isLoading() {
                return loadingInProgress;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAllItems;
            }
        };

        Paginate.with(recyclerView, callbacks)
                .setLoadingTriggerThreshold(1)
                .addLoadingListItem(false)
                .build();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleIntent(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_repos_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                presenter.reload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.saveState(adapter.getItems());
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWarningMessage(String message) {
        new CustomToast(this).showMessage(message);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showUsers(List<ShortUserEntity> users) {
        if (users != null){
            if (users.size() == 0 || users.size() < 8){hasLoadedAllItems = true;}
            adapter.add(users);
        }
        recyclerView.setVisibility(View.VISIBLE);
        loadingInProgress = false;
    }

    @Override
    public void clearUpList() {
        adapter.clear();
        hasLoadedAllItems = false;
    }

    @Override
    public void showNoConnectionMessage() {
        noConnView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConnectionMessage() {
        noConnView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideRepos() {
        recyclerView.setVisibility(View.INVISIBLE);
    }
}
