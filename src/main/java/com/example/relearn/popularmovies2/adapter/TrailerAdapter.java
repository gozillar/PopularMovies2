package com.example.relearn.popularmovies2.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.relearn.popularmovies2.MovieActivity;
import com.example.relearn.popularmovies2.R;
import com.example.relearn.popularmovies2.model.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailersViewHolder> {

    private static final String TAG = TrailerAdapter.class.getSimpleName();
    private List<Trailer> trailerList;

    public TrailerAdapter(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_card, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailersViewHolder holder, int position) {

        final Trailer currentTrailer = trailerList.get(position);

        holder.trailerName.setText(currentTrailer.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + currentTrailer.getKey()));
                context.startActivity(i);
                /*
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra("trailer", currentTrailer);
                context.startActivity(intent);*/
            }
        });
    }

    private void startVideo(String videoID) {

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {

        private TextView trailerName;

        public TrailersViewHolder(View itemView) {
            super(itemView);
            trailerName = (TextView) itemView.findViewById(R.id.trailerName);
        }

    }

}
