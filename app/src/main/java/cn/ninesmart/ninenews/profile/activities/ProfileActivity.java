package cn.ninesmart.ninenews.profile.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.data.auth.repositories.AuthRepository;
import cn.ninesmart.ninenews.data.users.repositories.UsersRepository;
import cn.ninesmart.ninenews.profile.contracts.ProfileContract;
import cn.ninesmart.ninenews.profile.presenters.ProfilePresenter;
import cn.ninesmart.ninenews.profile.views.ProfileFragment;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_black_24dp);
        }

        ProfileFragment fragment = (ProfileFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = ProfileFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
        ProfileContract.Presenter presenter = new ProfilePresenter(
                AuthRepository.getInstance(this),
                UsersRepository.getInstance(),
                fragment);
        fragment.setPresenter(presenter);
    }
}
