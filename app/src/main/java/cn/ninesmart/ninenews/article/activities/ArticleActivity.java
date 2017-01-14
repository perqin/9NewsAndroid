package cn.ninesmart.ninenews.article.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.article.contracts.ArticleContract;
import cn.ninesmart.ninenews.article.presenters.ArticlePresenter;
import cn.ninesmart.ninenews.article.views.ArticleFragment;

public class ArticleActivity extends AppCompatActivity implements ArticleFragment.OnFragmentInteractionListener {
    public static final String EXTRA_ARTICLE_ID = "ARTICLE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String articleId = getIntent().getStringExtra(EXTRA_ARTICLE_ID);

        ArticleFragment fragment = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = ArticleFragment.newInstance(articleId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
        ArticleContract.Presenter presenter = new ArticlePresenter();
        fragment.setPresenter(presenter);
    }
}
