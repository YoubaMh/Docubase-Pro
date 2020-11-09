package com.findriver.docubaseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    private TextView welcomeWithName, cardNumberDocuments;
    private RequestQueue queue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        initObject();

        Intent intent = this.getActivity().getIntent();
        String firstname = intent.getStringExtra("firstname");
        welcomeWithName.setText("Bienvenue "+firstname);

        setNumberDocuments();

        return view;
    }

    /* Initialisation des views */
    private void initView(View view) {
        welcomeWithName = (TextView) view.findViewById(R.id.WelcomeWithName);
        cardNumberDocuments = (TextView) view.findViewById(R.id.card_number_documents);
    }
    /* Initialisation des Objects */
    private void initObject() {
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
    }

    private void setNumberDocuments() {
        String url = "http://192.168.1.121:5000/api/documents";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int count = 0;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String documents = jsonResponse.getString("documents");
                    for(int i = 0; i <= jsonResponse.length(); i++) {
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

}