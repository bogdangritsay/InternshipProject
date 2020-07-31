package com.hritsay.internsiphproject.filmlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hritsay.internsiphproject.R;
import com.hritsay.internsiphproject.databinding.FilmItemBinding;
import com.hritsay.internsiphproject.models.FilmDetailsItem;


import java.util.Collections;
import java.util.List;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmViewHolder> {
    private static String IMDB_KEY = "imdbId";
    private List<FilmDetailsItem> mFilmList = Collections.emptyList();


    static class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FilmItemBinding filmItemBinding;
        private String imdbID;

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString(IMDB_KEY, imdbID);
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_detailsFragment, bundle);
        }

        public FilmViewHolder(FilmItemBinding itemBinding) {
            super(itemBinding.getRoot());
            filmItemBinding = itemBinding;
            itemView.setOnClickListener(this);
        }

    }
    /* delete*/
    public FilmListAdapter() {
    }

    @NonNull
    @Override
    public FilmListAdapter.FilmViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FilmItemBinding recyclerItemBinding = FilmItemBinding.inflate(inflater, parent, false);
        return new FilmViewHolder(recyclerItemBinding);
    }



    @Override
    public void onBindViewHolder(@NonNull final FilmListAdapter.FilmViewHolder holder, final int position) {
        bindHolder(holder, position);
    }

    private void bindHolder(@NonNull final FilmListAdapter.FilmViewHolder holder, final int position) {
        FilmDetailsItem filmDetailsItem = mFilmList.get(position);
        holder.setImdbID(filmDetailsItem.getImdbId());
        Glide.with(holder.itemView.getContext())
                .load(filmDetailsItem.getUrl())
                .placeholder(R.drawable.film_placeholder_background)
                .error(R.drawable.ic_baseline_error_outline_24)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.filmItemBinding.filmImage);
        holder.filmItemBinding.filmTitle.setText(filmDetailsItem.getTitle());
        holder.filmItemBinding.filmYear.setText(filmDetailsItem.getYear());
        holder.filmItemBinding.filmActors.setText(filmDetailsItem.getActors());
    }


    @Override
    public int getItemCount() {
        return this.mFilmList.size();
    }

    public void setmFilmList(List<FilmDetailsItem> mFilmList) {
        this.mFilmList = mFilmList;
    }
}
