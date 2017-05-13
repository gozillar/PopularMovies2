package com.example.relearn.popularmovies2.sqlite;

import android.provider.BaseColumns;

public class FavoriteMoviesContract {

    public static final class FavoriteMovies implements BaseColumns {

        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_ID = "movieId";
        public static final String COLUMN_MOVIE_PATH = "moviePosterPath";
        public static final String COLUMN_TITLE = "movieTitle";
        public static final String COLUMN_OVERVIEW = "movieOverview";
        public static final String COLUMN_REL_DATE = "movieReleaseDate";
        public static final String COLUMN_RATING = "movieUserRating";

    }

}
