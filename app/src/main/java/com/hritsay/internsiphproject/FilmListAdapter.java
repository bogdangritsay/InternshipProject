package com.hritsay.internsiphproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hritsay.internsiphproject.databinding.FilmItemBinding;
import com.hritsay.internsiphproject.models.FilmItem;


import java.util.List;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmViewHolder> {
    private LayoutInflater mInflater;
    private List<FilmItem> mFilmList;
    private Context context;

    class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FilmItemBinding filmItemBinding;
        private String imdbID;

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }

        //TODO bind


        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("imdbId", imdbID);
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_detailsFragment, bundle);
        }

        public FilmViewHolder(FilmItemBinding itemBinding) {
            super(itemBinding.getRoot());
            filmItemBinding = itemBinding;
            itemView.setOnClickListener(this);
        }

    }

    public FilmListAdapter(Context context, List<FilmItem> filmList) {
        mInflater = LayoutInflater.from(context);
        this.mFilmList = filmList;
    }

    @NonNull
    @Override
    public FilmListAdapter.FilmViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        FilmItemBinding recyclerItemBinding = FilmItemBinding.inflate(inflater, parent, false);
        return new FilmViewHolder(recyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilmListAdapter.FilmViewHolder holder, final int position) {
        holder.setImdbID(mFilmList.get(position).getImdbId());
        Glide.with(context)
                .load(mFilmList.get(position).getUrl())
                .placeholder(R.drawable.film_placeholder_background)
                .error(R.drawable.ic_baseline_error_outline_24)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.filmItemBinding.filmImage);

        holder.filmItemBinding.filmTitle.setText(mFilmList.get(position).getTitle());
        holder.filmItemBinding.filmYear.setText(mFilmList.get(position).getYear());

    }


    @Override
    public int getItemCount() {
        return this.mFilmList.size();
    }



}
