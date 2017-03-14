package cn.ninesmart.ninenews.articlecomments.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.articlecomments.contracts.ArticleCommentsContract;
import cn.ninesmart.ninenews.articlecomments.presenters.ArticleCommentsPresenter;
import cn.ninesmart.ninenews.articlecomments.views.ArticleCommentsFragment;
import cn.ninesmart.ninenews.data.auth.repositories.AuthRepository;
import cn.ninesmart.ninenews.data.comments.repositories.CommentsRepository;

public class ArticleCommentsActivity extends AppCompatActivity
        implements ArticleCommentsFragment.OnFragmentInteractionListener {
    public static final String EXTRA_ARTICLE_ID = "ARTICLE_ID";

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_comments);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(R.string.comments);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_back_black_24dp);
        }

        String articleId = getIntent().getStringExtra(EXTRA_ARTICLE_ID);

        ArticleCommentsFragment fragment = (ArticleCommentsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = ArticleCommentsFragment.newInstance(articleId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
        ArticleCommentsContract.Presenter presenter = new ArticleCommentsPresenter(
                AuthRepository.getInstance(this),
                CommentsRepository.getInstance(),
                fragment
        );
        fragment.setPresenter(presenter);
    }

    @Override
    public void onCommentsCountChange(int count) {
        if (mActionBar != null) {
            mActionBar.setTitle(getString(R.string.n_comments, count));
        }
    }
}
