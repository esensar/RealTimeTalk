package com.smarthomies.realtimetalk.views.activities;

import android.app.SearchManager;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.databinding.ActivityProfileBinding;
import com.smarthomies.realtimetalk.databinding.ActivitySearchBinding;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.viewmodels.SearchViewModel;
import com.smarthomies.realtimetalk.views.activities.bindingutils.OnErrorChangedCallback;
import com.smarthomies.realtimetalk.views.adapters.UsersAdapter;
import com.smarthomies.realtimetalk.views.fragments.ProfileViewModelHolder;
import com.smarthomies.realtimetalk.views.fragments.SearchViewModelHolder;

import java.util.List;

public class ProfileActivity extends RTTActivity {

    private ProfileViewModelHolder viewModelHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add retained to fragment manager
        if(getSupportFragmentManager().findFragmentByTag(ProfileViewModelHolder.class.getName()) == null) {
            viewModelHolder = new ProfileViewModelHolder();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(viewModelHolder, SearchViewModelHolder.class.getName())
                    .commit();
        }
        else {
            viewModelHolder = (ProfileViewModelHolder)getSupportFragmentManager().findFragmentByTag(ProfileViewModelHolder.class.getName());
        }

        ActivityProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setViewModel(viewModelHolder.getProfileViewModel());


        getViewModelHolder().getProfileViewModel().getFirstNameError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilFirstName));
        getViewModelHolder().getProfileViewModel().getLastNameError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilLastName));
        getViewModelHolder().getProfileViewModel().getEmailError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilEmail));

        getViewModelHolder().getProfileViewModel().getOldPasswordError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilCurrentPassword));
        getViewModelHolder().getProfileViewModel().getPasswordError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilPassword));
        getViewModelHolder().getProfileViewModel().getPasswordConfirmationError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilConfirmPassword));

        viewModelHolder.getProfileViewModel().loadProfile();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            getViewModelHolder().getProfileViewModel().onSaveClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ProfileViewModelHolder getViewModelHolder() {
        return viewModelHolder;
    }
}
