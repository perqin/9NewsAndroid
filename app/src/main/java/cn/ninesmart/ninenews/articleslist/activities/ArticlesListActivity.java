package cn.ninesmart.ninenews.articleslist.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.articleslist.contracts.NewsListContract;
import cn.ninesmart.ninenews.articleslist.presenters.NewsListPresenter;
import cn.ninesmart.ninenews.articleslist.views.NewsListFragment;
import cn.ninesmart.ninenews.data.articles.repositories.ArticlesRepository;
import cn.ninesmart.ninenews.data.auth.repositories.AuthRepository;
import cn.ninesmart.ninenews.data.users.repositories.UsersRepository;

public class ArticlesListActivity extends AppCompatActivity implements NewsListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NewsListFragment fragment = (NewsListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = NewsListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        NewsListContract.Presenter presenter = new NewsListPresenter(
                AuthRepository.getInstance(this), ArticlesRepository.getInstance(),
                UsersRepository.getInstance(), fragment);
        fragment.setPresenter(presenter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
