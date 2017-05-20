package com.example.relearn.popularmovies2;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.relearn.popularmovies2.model.Movie;
import com.example.relearn.popularmovies2.sqlite.FavoritesDBHelper;

import android.database.SQLException;

public class MovieActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private ImageView mMoviePoster;
    private int maxScrollSize;
    FavoritesDBHelper helper;
    String moviePath;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movie = getIntent().getParcelableExtra("movie");

        setUpViews();
        loadImageIntoView();
    }

    public void fave(View v) {
        helper = new FavoritesDBHelper(this);

        String title = movie.getOriginal_title();
        String moviePath = movie.getBackdrop_path();
        String overview = movie.getOverview();
        String relDate = movie.getRelease_date();
        double rating = movie.getVote_average();
        int id = movie.getId();

        try {
            helper.insertIntoDB(id, title, moviePath, overview, relDate, rating);
            Toast.makeText(getApplicationContext(), "Saved to Favourites", Toast.LENGTH_SHORT).show();
        } catch (SQLException se) {
            Toast.makeText(getApplicationContext(), "Save failed", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (maxScrollSize == 0)
            maxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / maxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mMoviePoster.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mMoviePoster.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    void setUpViews() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.materialup_viewpager);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        mMoviePoster = (ImageView)findViewById(R.id.moviePoster);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appBarLayout.addOnOffsetChangedListener(this);
        maxScrollSize = appBarLayout.getTotalScrollRange();

        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Trailers"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void loadImageIntoView() {
        moviePath = "http://image.tmdb.org/t/p/w342";
        Glide.with(this).load(moviePath + movie.getBackdrop_path()).into(mMoviePoster);
    }

}
