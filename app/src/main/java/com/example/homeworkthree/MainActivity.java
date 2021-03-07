package com.example.homeworkthree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    Button character_btn;
    Button location_btn;
    Button episode_btn;
    public static AsyncHttpClient client = new AsyncHttpClient();
    public String getCharacterUrl;
    ArrayList<String> episodesArr = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grabbing buttons
        character_btn = findViewById(R.id.character_btn);
        location_btn = findViewById(R.id.location_btn);
        episode_btn = findViewById(R.id.episode_btn);


        //on click listeners to load respective Fragments
        character_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCharacter();
            }
        });
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFragment(new LocationFragment(), R.id.fragContainer);
            }
        });
        episode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFragment(new EpisodeFragment(), R.id.fragContainer);
            }
        });

    }

    public void requestCharacter() {
        getCharacterUrl = "https://rickandmortyapi.com/api/character/";
        Bundle bundle = new Bundle();

        // Generate url
        getCharacterUrl += Integer.toString(ThreadLocalRandom.current().nextInt(1, 670 + 1));
        Log.d("characterURl", getCharacterUrl);

        //API Call
        client.addHeader("Accept", "application/json");
        client.get(getCharacterUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    // Name, status, species, gender, origin name, location name, an image, and a list of episode numbers this character
                    bundle.putString("name", json.getString("name"));
                    bundle.putString("status", json.getString("status"));
                    bundle.putString("species", json.getString("species"));
                    bundle.putString("gender", json.getString("gender"));

                    JSONObject origin = json.getJSONObject("origin");
                    bundle.putString("originName", origin.getString("name"));


                    JSONObject location = json.getJSONObject("location");
                    bundle.putString("locationName", location.getString("name"));

                    bundle.putString("imageUrl", json.getString("image"));


                    JSONArray episodes = json.getJSONArray("episode");
                    for (int i = 0; i < episodes.length(); i++) {
                        String episodeUrl = episodes.getString(i);
                        String[] splitEp = episodeUrl.split("/");
                        int id = Integer.parseInt(splitEp[splitEp.length - 1]);
                        episodesArr.add(Integer.toString(id));
                    }
                    bundle.putStringArrayList("episodesArr", episodesArr);

                    // Load Fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragContainer, CharacterFragment.class, bundle)
                            .commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error /character/id", new String(responseBody));

            }
        });
    }

}