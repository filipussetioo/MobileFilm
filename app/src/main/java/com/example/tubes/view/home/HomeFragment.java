package com.example.tubes.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.tubes.adapter.FilmListAdapter;
import com.example.tubes.R;
import com.example.tubes.util.DummyData;
import com.example.tubes.view.addedit.AddEditFragment;
import com.example.tubes.view.addedit.AddEditViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FilmListAdapter homeListAdapter;
    private RecyclerView rvCard;
    private FloatingActionButton fabAdd;
    private ArrayAdapter<CharSequence> typeListAdapter;
    private AutoCompleteTextView dropdownType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeListAdapter = new FilmListAdapter(getActivity());
        rvCard = view.findViewById(R.id.rv_card);
        fabAdd = view.findViewById(R.id.fab_add);
        dropdownType = view.findViewById(R.id.dropdown_type_home);

        rvCard.setAdapter(this.homeListAdapter);
        rvCard.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true));
        rvCard.setHasFixedSize(true);

        fabAdd.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AddEditFragment())
                    .addToBackStack(null)
                    .commit();
        });

        dropdownType.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0){
                homeViewModel.refreshFilm(getActivity(), "All");
            } else if (position == 1){
                homeViewModel.refreshFilm(getActivity(), "Movie");
            } else if (position == 2){
                homeViewModel.refreshFilm(getActivity(), "Series");
            }
        });
        homeViewModel.refreshFilm(getActivity(), "All");
        homeViewModel.getFilmList().observe(requireActivity(), films -> homeListAdapter.setFilmList(films));
    }

    @Override
    public void onResume() {
        super.onResume();

        typeListAdapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item_list, getResources().getStringArray(R.array.display_type));
        dropdownType.setAdapter(typeListAdapter);
    }
}