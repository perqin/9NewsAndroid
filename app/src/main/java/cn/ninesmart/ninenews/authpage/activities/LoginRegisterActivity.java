package cn.ninesmart.ninenews.authpage.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.authpage.contracts.LoginRegisterContract;
import cn.ninesmart.ninenews.authpage.presenters.LoginRegisterPresenter;
import cn.ninesmart.ninenews.authpage.views.LoginRegisterFragment;
import cn.ninesmart.ninenews.data.auth.repositories.AuthRepository;

public class LoginRegisterActivity extends AppCompatActivity implements LoginRegisterFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoginRegisterFragment fragment = (LoginRegisterFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = LoginRegisterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
        LoginRegisterContract.Presenter presenter = new LoginRegisterPresenter(
                AuthRepository.getInstance(this), fragment);
        fragment.setPresenter(presenter);
    }
}
