package com.example.movies.mvvm.library.caching.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.movies.mvvm.library.caching.databinding.MovieItemLayoutBinding;
import com.example.movies.mvvm.library.caching.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context) {
        movies = new ArrayList<>();
        this.context = context.getApplicationContext();
    }


    public void setMovies(List<Movie> list) {
        this.movies = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MovieItemLayoutBinding binding;

        public ViewHolder(@NonNull MovieItemLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Movie movie) {
            binding.progress.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + movie.getPosterPath())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(binding.poster);
            binding.title.setText(movie.getTitle());
            binding.rating.setText(String.format("%.1f", movie.getVoteAverage()));
        }
    }
}
