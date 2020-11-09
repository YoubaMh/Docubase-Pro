package com.findriver.docubaseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class FragmentDocument extends Fragment {
    private RecyclerView recyclerView;
    private List<DocumentListe> list = new ArrayList<>();

    private RequestQueue queue;
    private JSONArray documents;

    public FragmentDocument() {
        // Required empty public constructor
    }

    public static FragmentDocument newInstance(String param1, String param2) {
        FragmentDocument fragment = new FragmentDocument();

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

        View view = inflater.inflate(R.layout.fragment_document, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.document_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        initView(view);
        initObject();

        fetchDocuments();

        Runnable r = new Runnable() {
            @Override
            public void run(){
                for(int i=0; i < documents.length(); i++) {
                    try {
                        System.err.println(documents.getJSONObject(i));
                        String title = documents.getJSONObject(i).getString("title");
                        list.add(new DocumentListe(title));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setAdapter(new DocumentAdapter(list));
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 500);

        recyclerView.setAdapter(new DocumentAdapter(list));

        return view;
    }

    /* Initialisation des views */
    private void initView(View view) {

    }
    /* Initialisation des Objects */
    private void initObject() {
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
    }

    private void fetchDocuments() {
        String url = "http://51.210.107.146:5000/api/documents";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonResponseArray = jsonResponse.getJSONArray("documents");
                    documents = jsonResponseArray;
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
}