package com.example.tubes.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes.model.Film;
import com.example.tubes.R;
import com.example.tubes.view.addedit.AddEditFragment;

import java.util.ArrayList;
import java.util.List;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {

    private Context context;
    private List<Film> filmList;

    public FilmListAdapter(Context context) {
        this.context = context;
        filmList = new ArrayList<>();
    }

    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle, tvStatus, tvEpisode;
        protected ImageView ivFilm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvStatus = itemView.findViewById(R.id.tv_card_item_status);
            this.tvTitle = itemView.findViewById(R.id.tv_card_item_title);
            this.ivFilm = itemView.findViewById(R.id.iv_card_item);
            this.tvEpisode = itemView.findViewById(R.id.tv_card_item_episode);
        }

        public void bind(int position) {
            tvTitle.setText(filmList.get(position).title);
            tvStatus.setText(filmList.get(position).status);
//            String episode = String.format("Episode: %s", filmList.get(position).episode);
//            if (!filmList.get(position).type.equals("Movie"))
//                tvEpisode.setText(episode);
            itemView.setOnClickListener(v -> {
                AddEditFragment addEditFragment = new AddEditFragment();
                Bundle itemId = new Bundle();
                itemId.putLong("FILM_ID", filmList.get(position).id);
                addEditFragment.setArguments(itemId);

                FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addEditFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
