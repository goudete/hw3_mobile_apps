package com.example.homeworkthree;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class LocationFragment extends Fragment {

    public LocationFragment() {
        //set data here
        super(R.layout.fragment_location);
    }

    protected static AsyncHttpClient client = new AsyncHttpClient();
    private ArrayList<Location> locations;
    private RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Call API
        String getLocationsUrl = "https://rickandmortyapi.com/api/location";

        // Get total number of characters
        client.addHeader("Accept", "application/json");
        client.get(getLocationsUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));


                    try {
                        JSONArray results = json.getJSONArray("results");
                        for (int i = 0; i < results.length(); i ++) {
                            JSONObject locationObj = results.getJSONObject(i);
                            Location loc = new Location(locationObj.getString("name"),
                                    locationObj.getString("type"),
                                    locationObj.getString("dimension"));
                            locations.add(loc);
                        }

//                        TextView title = view.findViewById(R.id.recyclyer_view);
//                        title.setText("Locations");

                        LocationAdapter adapter = new LocationAdapter(locations);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error /location", new String(responseBody));

            }
        });

        // Look up recycler view
        recyclerView = view.findViewById(R.id.recyclyer_view);
        locations = new ArrayList<>();

    }
}
