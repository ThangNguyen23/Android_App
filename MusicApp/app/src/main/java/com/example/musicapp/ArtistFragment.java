package com.example.musicapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.musicapp.MainActivity.artists;

public class ArtistFragment extends Fragment {
    RecyclerView recyclerView;
    ArtistAdapter artistAdapter;

    public ArtistFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        if(!(artists.size() < 1)){
            artistAdapter = new ArtistAdapter(getContext(), artists);
            recyclerView.setAdapter(artistAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        return view;
    }
}