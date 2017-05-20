package com.example.relearn.popularmovies2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.relearn.popularmovies2.adapter.FavMoviesAdapter;
import com.example.relearn.popularmovies2.model.FavMovie;
import com.example.relearn.popularmovies2.sqlite.FavoritesDBHelper;

import java.util.List;

public class FavMovieActivity extends AppCompatActivity {

    RecyclerView rRecyclerView;
    FavMoviesAdapter favMoviesAdapter;
    FavoritesDBHelper helper;
    List<FavMovie> dbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_main);

        setUpViews();

        loadDBData();
    }

    void setUpViews() {
        rRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rRecyclerView.setLayoutManager(gridLayoutManager);
        rRecyclerView.setHasFixedSize(true);
        rRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
    }

    void loadDBData() {
        helper = new FavoritesDBHelper(this);
        dbList = helper.getDataFromDB();
        favMoviesAdapter = new FavMoviesAdapter(this, dbList);
        rRecyclerView.setAdapter(favMoviesAdapter);
    }
}
