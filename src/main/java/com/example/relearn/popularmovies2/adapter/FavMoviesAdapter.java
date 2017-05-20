package com.example.relearn.popularmovies2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.relearn.popularmovies2.MovieActivity;
import com.example.relearn.popularmovies2.R;
import com.example.relearn.popularmovies2.model.FavMovie;
import com.example.relearn.popularmovies2.sqlite.FavoriteMoviesContract;

import java.util.ArrayList;
import java.util.List;

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.FavMoviesViewHolder> {

    static Context mContext;
    static List<FavMovie> favList;

    public FavMoviesAdapter(Context context, List<FavMovie> favList) {
        this.favList = new ArrayList<FavMovie>();
        this.mContext = context;
        this.favList = favList;
    }

    @Override
    public FavMoviesAdapter.FavMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new FavMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavMoviesViewHolder holder, int position) {

        final FavMovie currentMovie = favList.get(position);

        String posterPath = "http://image.tmdb.org/t/p/w342";

        Glide.with(holder.itemView.getContext())
                .load(posterPath + currentMovie.getBackdrop_path())
                .into(holder.movieImage);

        holder.movieTitle.setText(currentMovie.getOriginal_title());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieActivity.class);
                //intent.putStringArrayListExtra("movie", currentMovie);
                //intent.putExtra("movie", currentMovie);
                //context.startActivity(intent);
                }
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    static class FavMoviesViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImage;
        private TextView movieTitle;

        public FavMoviesViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movie_image);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        }

    }

}