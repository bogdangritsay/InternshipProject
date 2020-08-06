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
import com.hritsay.internsiphproject.models.FilmItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter for FilmList RecyclerView
 */
public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmViewHolder> {
    private static String IMDB_KEY = "imdbId";
    private List<FilmItem> mFilmList = Collections.emptyList();

    /**
     * ViewHolder inner class for Film item
     */
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

        /**
         * Constructor for FilmViewHolder
         * @param itemBinding item for holder
         */
        public FilmViewHolder(FilmItemBinding itemBinding) {
            super(itemBinding.getRoot());
            filmItemBinding = itemBinding;
            itemView.setOnClickListener(this);
        }
    }

    /**
     * Default constructor for FilmListAdapter
     */
    public FilmListAdapter() {}

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
        FilmItem filmItem = mFilmList.get(position);
        holder.setImdbID(filmItem.getImdbId());
        Glide.with(holder.itemView.getContext())
                .load(filmItem.getUrl())
                .placeholder(R.mipmap.poster_tmp)
                .error(R.drawable.ic_baseline_error_outline_24)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.filmItemBinding.filmImage);
        holder.filmItemBinding.filmTitle.setText(filmItem.getTitle());
        holder.filmItemBinding.filmYear.setText(filmItem.getYear());
        holder.filmItemBinding.filmActors.setText(filmItem.getActors());
    }


    @Override
    public int getItemCount() {
        return this.mFilmList.size();
    }

    /**
     * Setting FilmList in RecyclerView and notify that data set changed
     * @param mFilmList FilmList for setting
     */
    public void setmFilmList(List<FilmItem> mFilmList) {
        Collections.sort(mFilmList, new Comparator<FilmItem>() {
            @Override
            public int compare(FilmItem filmItem, FilmItem t1) {
                return filmItem.getTitle().compareTo(t1.getTitle());
            }
        });
        this.mFilmList = mFilmList;
        notifyDataSetChanged();
    }

    public List<FilmItem> getmFilmList() {
        return mFilmList;
    }
}
