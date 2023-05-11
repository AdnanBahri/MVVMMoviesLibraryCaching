package com.example.movies.mvvm.library.caching.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.movies.mvvm.library.caching.adapter.MovieAdapter;
import com.example.movies.mvvm.library.caching.databinding.FragmentHomeBinding;
import com.example.movies.mvvm.library.caching.utils.ItemDecorator;
import com.google.android.material.chip.Chip;

public class HomeFragment extends Fragment {

    private static final String TAG = "Home Fragment";
    private MovieAdapter adapter;

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        adapter = new MovieAdapter(getContext());
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            adapter.setMovies(movies);
            Log.d(TAG, "onCreateView: " + movies.size());
        });
        viewModel.fetchMovies("Rating");
        viewModel.isLoading().observe(getViewLifecycleOwner(), state -> {
            if (state)
                Toast.makeText(getContext(), "It's Loading Right Now", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getContext(), "Loading is DOne", Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] filter_options = {"Release Date", "Rating", "Vote Count"};
        for (String option : filter_options) {
            Chip chip = new Chip(getContext());
            chip.setText(option);
            chip.setCheckable(true);
            chip.setChecked(option.equals("Rating"));
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) viewModel.fetchMovies(option);
                    binding.moviesRecycler.smoothScrollToPosition(0);
                }
            });
            binding.chipGroup.addView(chip);
        }
        binding.moviesRecycler.setAdapter(adapter);
        binding.moviesRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.moviesRecycler.addItemDecoration(new ItemDecorator(2, ItemDecorator.dpToPx(getContext(), 10), false));
    }
}