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
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    Button character_btn;
    Button location_btn;
    Button episode_btn;
    public static AsyncHttpClient client = new AsyncHttpClient();
    public int totalCharacters;
    public int characterId;
    public int totalEpisodes;
    public int episodeId;
    public String getCharacterUrl;
    public String getEpisodesUrl;
    ArrayList<String> episodesArr = new ArrayList<String>();
    ArrayList<String> charactersArr = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting total Characters & episodes from api
        getTotalCharacters();
        getTotalEpisodes();

        // Grabbing buttons
        character_btn = findViewById(R.id.character_btn);
        location_btn = findViewById(R.id.location_btn);
        episode_btn = findViewById(R.id.episode_btn);


        //on click listeners to load respective Fragments
        character_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCharacter();
            }
        });
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocations();
            }
        });
        episode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEpisode();
            }
        });

    }
    public void getTotalCharacters() {
        getCharacterUrl = "https://rickandmortyapi.com/api/character/";

        // Get total number of characters
        client.addHeader("Accept", "application/json");
        client.get(getCharacterUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    JSONObject info = json.getJSONObject("info");
                    totalCharacters = info.getInt("count");
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
    public void getCharacter() {
        getCharacterUrl = "https://rickandmortyapi.com/api/character/";
        Bundle bundle = new Bundle();

        // Generate url
        Random random = new Random();
        characterId = random.nextInt(totalCharacters - 1) + 1;

        getCharacterUrl += Integer.toString(characterId);
        Log.d("characterURl", getCharacterUrl);

        //API Call
        client.addHeader("Accept", "application/json");
        client.get(getCharacterUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    // Clear previous elements stored in bundle

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
                    bundle.remove("episodesArr");
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
    public void getLocations() {
        // Load Fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, LocationFragment.class, null)
                .commit();
    }
    public void getTotalEpisodes() {
        getEpisodesUrl = "https://rickandmortyapi.com/api/episode/";

        // Get total number of characters
        client.addHeader("Accept", "application/json");
        client.get(getEpisodesUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    JSONObject info = json.getJSONObject("info");
                    totalEpisodes = info.getInt("count");
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
    public void getEpisode() {
        getEpisodesUrl = "https://rickandmortyapi.com/api/episode/";
        Bundle bundle = new Bundle();

        // Generate url
        Random random = new Random();
        episodeId = random.nextInt(totalEpisodes - 1) + 1;

        getEpisodesUrl += Integer.toString(episodeId);
        Log.d("getEpisodesUrl", getEpisodesUrl);

        //API Call
        client.addHeader("Accept", "application/json");
        client.get(getEpisodesUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    // Episode number, name, air date, a “more information” button/text/icon and first 3 characters’ names OR/AND their images in this episode
                    bundle.putString("name", json.getString("name"));
                    bundle.putString("episode", json.getString("episode"));
                    bundle.putString("air_date", json.getString("air_date"));



                    JSONArray characters = json.getJSONArray("characters");
                    if (characters.length() > 1) {
                        for (int i = 0; i < characters.length(); i++) {
                            String characterUrl = characters.getString(i);
                            String[] splitChar = characterUrl.split("/");
                            int id = Integer.parseInt(splitChar[splitChar.length - 1]);
                            charactersArr.add(Integer.toString(id));
                        }
                        bundle.putStringArrayList("characters", charactersArr);
                    } else {
                        charactersArr.add("no characters");
                        bundle.putStringArrayList("characters", charactersArr);
                    }


                    // Load Fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragContainer, EpisodeFragment.class, bundle)
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