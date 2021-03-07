package com.example.homeworkthree;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class CharacterFragment extends Fragment {

    public String name;
    public String status;
    public String species;
    public String gender;
    public String originName;
    public String locationName;
    public String imageUrl;
    ArrayList<String> episodesArr = new ArrayList<String>();

    public CharacterFragment() {
        super(R.layout.fragment_character);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Grab views
        TextView characterName = getView().findViewById(R.id.characterName);
        TextView characterStatus = getView().findViewById(R.id.characterStatus);
        TextView characterSpecies = getView().findViewById(R.id.characterSpecies);
        TextView characterGender = getView().findViewById(R.id.characterGender);
        TextView characterOriginName = getView().findViewById(R.id.characterOriginName);
        TextView characterLocationName = getView().findViewById(R.id.characterLocationName);
        TextView characterEpisodes = getView().findViewById(R.id.characterEpisodes);
        ImageView image = getView().findViewById(R.id.characterImage);

        // Get data from bundle
        name = this.getArguments().getString("name");
        status = this.getArguments().getString("status");
        species = this.getArguments().getString("species");
        gender = this.getArguments().getString("gender");
        originName = this.getArguments().getString("originName");
        locationName = this.getArguments().getString("locationName");
        episodesArr = this.getArguments().getStringArrayList("episodesArr");
        imageUrl = this.getArguments().getString("imageUrl");

        // Set text views
        characterName.setText("name: " + name);
        characterStatus.setText("status: " + status);
        characterSpecies.setText("species: " + species);
        characterGender.setText("gender: " + gender);
        characterOriginName.setText("origin name: " + originName);
        characterLocationName.setText("location name: " + locationName);
        characterEpisodes.setText("episodes: " + episodesArr);

        Picasso.get().load(imageUrl).into(image);


    }


}
