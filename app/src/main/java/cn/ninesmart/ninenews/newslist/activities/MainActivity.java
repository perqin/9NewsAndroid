package cn.ninesmart.ninenews.newslist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.newslist.contracts.NewsListContract;
import cn.ninesmart.ninenews.newslist.presenters.NewsListPresenter;
import cn.ninesmart.ninenews.newslist.views.NewsListFragment;

public class MainActivity extends AppCompatActivity implements NewsListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsListFragment fragment = (NewsListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = NewsListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        NewsListContract.Presenter presenter = new NewsListPresenter();
        fragment.setPresenter(presenter);
    }
}
