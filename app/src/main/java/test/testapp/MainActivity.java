package test.testapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String[] keys = {"name", "commit", "message"};
    private HashMap<String, String> hashMap;
    private ArrayList<HashMap> arrayList;
    private String server_response;
    private Custom_Adapter custom_adapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        hashMap = new HashMap<>();
        arrayList = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        //retreiving data from server
                                        retrieving_data();
                                    }
                                }
        );


    }

    //retreiving data from server
    private void retrieving_data() {
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("Tag", response);
                        server_response = response;
                        display_response(server_response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void display_response(String response) {
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                hashMap = new HashMap<>();
                String sha;
                String name;
                String message;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sha = jsonObject.getString("sha");

                JSONObject jsonObject_commit = jsonObject.getJSONObject("commit");
                JSONObject jsonObject_author = jsonObject_commit.getJSONObject("author");
                name = jsonObject_author.getString("name");

                message = jsonObject_commit.getString("message");

                hashMap.put(keys[0], name);
                hashMap.put(keys[1], sha);
                hashMap.put(keys[2], message);

                arrayList.add(hashMap);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        custom_adapter = new Custom_Adapter(this, arrayList);
        listView.setAdapter(custom_adapter);

    }

    @Override
    public void onRefresh() {
        //retreiving data from server
        retrieving_data();
    }
}
