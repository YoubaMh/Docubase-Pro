package com.findriver.docubaseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.findriver.docubaseapp.Hooks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentAffichage extends Fragment {
    private RecyclerView recyclerView;
    private List<AffichageListe> list = new ArrayList<>();

    private RequestQueue queue;
    private JSONArray displays;

    private String role = null;

    public FragmentAffichage() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static FragmentAffichage newInstance(String param1, String param2) {
        FragmentAffichage fragment = new FragmentAffichage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_affichage, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.affichage_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        initView(view);
        initObject();

        fetchDisplays();

        Runnable r = new Runnable() {
            @Override
            public void run(){
                for(int i=0; i < displays.length(); i++) {
                    try {
                        System.err.println(displays.getJSONObject(i));
                        String title = displays.getJSONObject(i).getString("title");
                        String description = displays.getJSONObject(i).getString("description");
                        String publisher = displays.getJSONObject(i).getString("publisher_id");
                        String publisherRole = getPublisherRole(publisher);
                        list.add(new AffichageListe(title, publisherRole, description));
                        Log.d("title", "affichage"+ i);
                        Log.d("title", title);
                        Log.d("title", description);
                        list.add(new AffichageListe(title+"", "Etudiant", description+""));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerView.setAdapter(new AffichageAdapter(list));
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 500);


        return view;
    }

    /* Initialisation des views */
    private void initView(View view) {

    }
    /* Initialisation des Objects */
    private void initObject() {
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
    }

    private void fetchDisplays() {
        String url = "http://51.210.107.146:5000/api/displays";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonResponseArray = jsonResponse.getJSONArray("displays");
                    displays = jsonResponseArray;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

    private String getPublisherRole(String id) {
        String url = "http://51.210.107.146:5000/api/users/"+id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject json = jsonResponse.getJSONObject("user");
                    role = json.getString("firstname") + " " + json.getString("lastname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                role = "error";
            }
        });

        queue.add(stringRequest);
        return role;
    }
}