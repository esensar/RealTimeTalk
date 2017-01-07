package com.smarthomies.realtimetalk.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.databinding.ActivityMainBinding;
import com.smarthomies.realtimetalk.databinding.AppBarMainBinding;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.utils.RTTErrorUtil;
import com.smarthomies.realtimetalk.views.adapters.UsersAdapter;
import com.smarthomies.realtimetalk.views.fragments.MainViewModelHolder;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.CompositeException;
import rx.schedulers.Schedulers;

public class MainActivity extends RTTActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainViewModelHolder viewModelHolder;

    private Subscription logoutSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add retained to fragment manager
        if(getSupportFragmentManager().findFragmentByTag(MainViewModelHolder.class.getName()) == null) {
            viewModelHolder = new MainViewModelHolder();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(viewModelHolder, MainViewModelHolder.class.getName())
                    .commit();
        }
        else {
            viewModelHolder = (MainViewModelHolder)getSupportFragmentManager().findFragmentByTag(MainViewModelHolder.class.getName());
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        AppBarMainBinding appBarMainBinding = binding.appBarMain;

        binding.setViewModel(viewModelHolder.getMainViewModel());
        appBarMainBinding.setViewModel(viewModelHolder.getMainViewModel());

        viewModelHolder.getMainViewModel().loadContacts();
        viewModelHolder.getMainViewModel().loadProfile();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvUsersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModelHolder.getMainViewModel().getContacts().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                List<User> users = ((ObservableField<List<User>>)observable).get();
                UsersAdapter usersAdapter = new UsersAdapter(UsersAdapter.ViewType.LIST);
                usersAdapter.setUsers(users);
                recyclerView.setAdapter(usersAdapter);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void subscribeToSubjects() {
        super.subscribeToSubjects();
        logoutSubscription = getViewModelHolder().getMainViewModel().getLogoutSubject().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new LogoutSubscriber());
    }

    @Override
    protected void unsubscribeFromSubjects() {
        super.unsubscribeFromSubjects();
        if(logoutSubscription != null && !logoutSubscription.isUnsubscribed()) {
            logoutSubscription.unsubscribe();
        }
    }

    private void reconnectToLogoutSubject() {
        logoutSubscription = getViewModelHolder().getMainViewModel().createLogoutSubject().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new LogoutSubscriber());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private class LogoutSubscriber extends Subscriber<Object> {
        @Override
        public void onCompleted() {
            reconnectToLogoutSubject();
            getViewModelHolder().getMainViewModel().onLogoutDone();
        }

        @Override
        public void onError(Throwable e) {
            reconnectToLogoutSubject();
            getViewModelHolder().getMainViewModel().onLogoutDone();

            if (e instanceof CompositeException) {
                for (Throwable ex : ((CompositeException) e).getExceptions()) {
                    if(ex instanceof RuntimeException) {
                        handleException(ex.getCause());
                    }
                }
            } else {
                handleException(e);
            }
        }

        @Override
        public void onNext(Object object) {
            getViewModelHolder().getMainViewModel().onLogoutDone();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            // Handle the camera action
            getViewModelHolder().getMainViewModel().onLogoutClick();
        }

        if (id == R.id.nav_profile) {
            getViewModelHolder().getMainViewModel().onProfileClick();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public MainViewModelHolder getViewModelHolder() {
        return viewModelHolder;
    }

    private void handleException(Throwable e) {
        Toast.makeText(this, RTTErrorUtil.getErrorString(e), Toast.LENGTH_SHORT).show();
    }

}
