package com.findriver.docubaseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.findriver.docubaseapp.Hooks.VolleySingleton;
import com.findriver.docubaseapp.Utils.InputValidation;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    private TextView welcomeWithName, cardNumberDocuments, titreAffichage, nomProf, contentAffichage;
    private RequestQueue queue;
    private String lastDisplayTitle = null;
    private String lastDisplayDescription = null;
    private String lastDisplayPublisher = null;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        initObject();

        Intent intent = this.getActivity().getIntent();
        String firstname = intent.getStringExtra("firstname");
        welcomeWithName.setText("Bienvenue "+firstname);




        Runnable r = new Runnable() {
            @Override
            public void run(){
                setNumberDocuments();
                setLastDisplay();
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 5000);
        if (lastDisplayPublisher !=  null){
            Log.d("affichage", lastDisplayPublisher);
        }
        
        return view;
    }

    /* Initialisation des views */
    private void initView(View view) {
        welcomeWithName = (TextView) view.findViewById(R.id.WelcomeWithName);
        cardNumberDocuments = (TextView) view.findViewById(R.id.card_number_documents);
        titreAffichage = (TextView) view.findViewById(R.id.titre_affichage);
        nomProf = (TextView) view.findViewById(R.id.nom_prof);
        contentAffichage = (TextView) view.findViewById(R.id.content_affichage);
    }
    /* Initialisation des Objects */
    private void initObject() {
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
    }

    private void setNumberDocuments() {
        String url = "http://51.210.107.146:5000/api/documents";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int count = 0;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    for(int i = 0; i < jsonResponse.getJSONArray("documents").length(); i++) {
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cardNumberDocuments.setText(String.valueOf(count));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cardNumberDocuments.setText("Error");
            }
        });

        queue.add(stringRequest);



    }

    private void setLastDisplay() {
        String url = "http://51.210.107.146:5000/api/displays";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonResponseArray = jsonResponse.getJSONArray("displays");
                    JSONObject lastDisplayJson = jsonResponseArray.getJSONObject(0);
                    lastDisplayTitle = lastDisplayJson.getString("title");
                    lastDisplayDescription = lastDisplayJson.getString("description");
                    lastDisplayPublisher = lastDisplayJson.getString("publisher_id");
                    Log.d("affichage", lastDisplayPublisher);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                titreAffichage.setText(lastDisplayTitle);
                contentAffichage.setText(lastDisplayDescription);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                titreAffichage.setText("Aucun affichage");
                contentAffichage.setText("Erreur");
                nomProf.setText("aucun prof");
            }
        });

        queue.add(stringRequest);
    }

}