package com.findriver.docubaseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.findriver.docubaseapp.Hooks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddAffichage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddAffichage extends Fragment {

    private EditText et_titre, et_description;
    private Button bt_add;
    private RequestQueue queue;
    private SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAddAffichage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAddAffichage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddAffichage newInstance(String param1, String param2) {
        FragmentAddAffichage fragment = new FragmentAddAffichage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_affichage, container, false);

        initView(view);
        initObject(view);
        initListener();

        return view;
    }

    private void initView(View view) {
        et_titre = (EditText) view.findViewById(R.id.et_titre);
        et_description = (EditText) view.findViewById(R.id.et_description);
        bt_add = (Button) view.findViewById(R.id.bt_add);
    }

    /* Initialisation des Objects */
    private void initObject(View view) {
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        sharedPreferences = view.getContext().getSharedPreferences("Session",Context.MODE_PRIVATE);
    }

    private void initListener() {
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_titre.getText().toString().trim();
                String description = et_description.getText().toString().trim();

                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(getContext(), "Remplissez tout les champs !", Toast.LENGTH_LONG).show();
                } else {
                    addNewDisplay(title, description);
                }
            }
        });
    }

    private void addNewDisplay(final String title, final String description) {
        String url = "http://51.210.107.146:5000/api/displays";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Ajout avec succéss", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Erreur lors de l'ajout", Toast.LENGTH_LONG).show();
            }
        }) {
            String data = "{\"title\": \"" + title + "\", \"description\": \"" + description + "\" , \"publisher\": \"" + sharedPreferences.getString("id", "") + "\"}";
            public String getBodyContentType() { return "application/json; charset=utf-8"; }
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(stringRequest);
    }
}