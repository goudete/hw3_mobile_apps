package com.example.homeworkthree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EpisodeFragment extends Fragment {

    public String name;
    public String episode;
    public String airDate;
    ArrayList<String> charactersArr = new ArrayList<String>();

    public EpisodeFragment() {
        super(R.layout.fragment_episode);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Grab views
        TextView Name = getView().findViewById(R.id.episodeName);
        TextView Episode = getView().findViewById(R.id.episodeNumber);
        TextView AirDate = getView().findViewById(R.id.airedOn);
        TextView CharactersArr = getView().findViewById(R.id.episodeCharacters);

        // Get data from bundle
        name = this.getArguments().getString("name");
        episode = this.getArguments().getString("episode");
        airDate = this.getArguments().getString("air_date");
        charactersArr = this.getArguments().getStringArrayList("characters");

        // Set text views
        Name.setText("name: " + name);
        Episode.setText("episode: " + episode);
        AirDate.setText("air date: " + airDate);
        CharactersArr.setText("character ids: " + charactersArr);


    }


}