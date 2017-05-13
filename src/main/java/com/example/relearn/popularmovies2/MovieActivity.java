package com.example.relearn.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import com.example.relearn.popularmovies2.sqlite.FavoriteMoviesContract;

public class MovieActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private ImageView mMoviePoster;
    private int maxScrollSize;
    String moviePath;
    private SQLiteDatabase db;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movie = getIntent().getParcelableExtra("movie");
        createDatabase();
        setUpViews();
        loadImageIntoView();

    }

    protected void createDatabase() {
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " +
                FavoriteMoviesContract.FavoriteMovies.TABLE_NAME + " (" +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_ID + " INTEGER PRIMARY KEY," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_TITLE + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_MOVIE_PATH + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_OVERVIEW + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_REL_DATE + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_RATING + " TEXT)";

        db = openOrCreateDatabase("FavoriteMoviesDB", Context.MODE_PRIVATE, null);
        try {
            db.execSQL(SQL_CREATE_FAVORITES_TABLE);
        } catch(SQLiteException se) {
            Toast.makeText(getApplicationContext(), se.toString(), Toast.LENGTH_LONG).show();
        }

    }

    protected void insertIntoDB() {
        String title = movie.getOriginal_title();
        String moviePath = movie.getBackdrop_path();
        String overview = movie.getOverview();
        String relDate = movie.getRelease_date();
        double rating = movie.getVote_average();
        int id = movie.getId();

        try {
            String query = "INSERT INTO favorites(movieId,movieTitle,moviePosterPath,movieOverview,movieReleaseDate,movieUserRating) VALUES('"
                    + id + "', '" + title + "', '" + moviePath + "', '" + overview + "', '" + relDate + "', '" + rating + "');";
            db.execSQL(query);
            Toast.makeText(getApplicationContext(), "Saved to Favourites", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "Save failed", Toast.LENGTH_LONG).show();
        }
    }

    public void fave(View v) {
        insertIntoDB();
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
