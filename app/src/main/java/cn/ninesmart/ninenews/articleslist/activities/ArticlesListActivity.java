package cn.ninesmart.ninenews.articleslist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.articleslist.contracts.NewsListContract;
import cn.ninesmart.ninenews.articleslist.presenters.NewsListPresenter;
import cn.ninesmart.ninenews.articleslist.views.NewsListFragment;
import cn.ninesmart.ninenews.data.articles.repositories.ArticlesRepository;

public class ArticlesListActivity extends AppCompatActivity implements NewsListFragment.OnFragmentInteractionListener {

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
        NewsListContract.Presenter presenter =
                new NewsListPresenter(ArticlesRepository.getInstance(), fragment);
        fragment.setPresenter(presenter);
    }
}
