package com.example.tubes.view.home;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tubes.database.AppDatabase;
import com.example.tubes.model.Film;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Film>> filmList;

    public HomeViewModel() {
        this.filmList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Film>> getFilmList() {
        return filmList;
    }

    public void refreshFilm(Context context, String type){
        new GetFilmList(context).execute(type);
    }

    private class GetFilmList extends AsyncTask<String, Void, List<Film>> {
        private Context context;

        public GetFilmList(Context context) {
            this.context = context;
        }

        @Override
        protected List<Film> doInBackground(String... strings) {
            List<Film> films = AppDatabase.getInstance(context).filmDAO().getFilmList();
            List<Film> filteredFilm = new ArrayList<>();

            if (strings[0].equals("All")){
                filteredFilm.addAll(films);
            } else {
                for (Film film : films) {
                    if (strings[0].equals(film.type))
                        filteredFilm.add(film);
                }

            }
            return filteredFilm;
        }

        @Override
        protected void onPostExecute(List<Film> films) {
            super.onPostExecute(films);
            filmList.setValue(films);
        }
    }
}
