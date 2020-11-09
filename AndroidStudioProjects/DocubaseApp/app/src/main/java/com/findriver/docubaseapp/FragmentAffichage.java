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
                        AffichageListe affichage = new AffichageListe(title, "Etudiant", description);
                        list.add(affichage);
                        affichage = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 1000);


//        AffichageListe affichage1 = new AffichageListe("Superman dalilou" ,"Etudiant", "Appel a tout les etudiants MASTER 1 GL, le dernier délai de l’exercice AIE est pour le 30 AOUT 2020, vous devez envoyer le document PDF à l’email de l’université.");
//        AffichageListe affichage2 = new AffichageListe("Jimbo nitrat" ,"Administrateur", "Appel a tout les etudiants MASTER 1 GL, le dernier délai de l’exercice AIE est pour le 30 AOUT 2020, vous devez envoyer le document PDF à l’email de l’université.");
//
//        list.add(affichage1);
//        list.add(affichage2);

        recyclerView.setAdapter(new AffichageAdapter(list));

        return view;
    }

    /* Initialisation des views */
    private void initView(View view) {
//        welcomeWithName = (TextView) view.findViewById(R.id.WelcomeWithName);
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

    private void setDisplays(JSONArray displays) {
        this.displays = displays;
    }
}