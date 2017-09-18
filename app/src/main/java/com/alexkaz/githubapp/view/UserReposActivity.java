package com.alexkaz.githubapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.alexkaz.githubapp.MyApp;
import com.alexkaz.githubapp.R;
import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;
import com.alexkaz.githubapp.presenter.UserReposPresenter;
import com.alexkaz.githubapp.ui.CustomToast;
import com.alexkaz.githubapp.ui.RepoRVAdapter;
import com.alexkaz.githubapp.ui.UserInfoView;
import com.paginate.Paginate;

import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class UserReposActivity extends AppCompatActivity implements UserReposView {

    @Inject
    UserReposPresenter presenter;

    private View noConnView;
    private ProgressBar progressBar;
    private RecyclerView repoListRV;
    private RepoRVAdapter adapter;
    private UserInfoView userInfoView;

    private boolean loadingInProgress = false;
    private boolean hasLoadedAllItems = false;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_user_repos);

        ((MyApp)getApplication()).getMyComponent().inject(this);
        configureActionBar();

        initComponents();
    }

    private void configureActionBar(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);
        }
    }

    private void initComponents(){
        progressBar = findViewById(R.id.userReposPB);
        noConnView = findViewById(R.id.noConnLayout);
        userInfoView = findViewById(R.id.userInfoView);
        repoListRV = findViewById(R.id.repoListRV);


        repoListRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepoRVAdapter();
        repoListRV.setAdapter( new ScaleInAnimationAdapter( new AlphaInAnimationAdapter(adapter)));

        presenter.bindView(this);
        presenter.setUserName(getIntent().getStringExtra("userName"));
        presenter.restore();

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

        Paginate.with(repoListRV, callbacks)
                .setLoadingTriggerThreshold(1)
                .addLoadingListItem(false)
                .build();

        if (userInfoView.getValues() == null){
            presenter.loadUserInfo();
        }
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
                presenter.refresh();
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
    protected void onStop() {
        super.onStop();
        if (userInfoView.getValues() != null)
            presenter.save(userInfoView.getValues(), adapter.getItems());
    }

    @Override
    public void showUserInfo(UserEntity user) {
        userInfoView.setValues(user);
        userInfoView.show();
    }

    @Override
    public void showRepos(List<RepoEntity> userRepos) {
        if (userRepos != null){
            if (userRepos.size() == 0 || userRepos.size() % 10 != 0){hasLoadedAllItems = true;}
            adapter.add(userRepos);
        }
        repoListRV.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        loadingInProgress = false;
    }

    @Override
    public void clearUpList() {
        adapter.clear();
        hasLoadedAllItems = false;
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
    public void showNoConnectionMessage() {
        noConnView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideNoConnectionMessage() {
        noConnView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideRepos() {
        repoListRV.setVisibility(View.INVISIBLE);
    }
}
