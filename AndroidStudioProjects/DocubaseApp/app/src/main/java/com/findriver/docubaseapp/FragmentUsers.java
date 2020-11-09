package com.findriver.docubaseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FragmentUsers extends Fragment {
    private RecyclerView recyclerView;
    private List<UserListe> list = new ArrayList<>();

    public FragmentUsers() {
        // Required empty public constructor
    }

    public static FragmentUsers newInstance(String param1, String param2) {
        FragmentUsers fragment = new FragmentUsers();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.user_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        UserListe user1 = new UserListe("Superman dalilou" ,"Etudiant");
        UserListe user2 = new UserListe("Jimbo nitrat" ,"Administrateur");

        list.add(user1);
        list.add(user2);

        recyclerView.setAdapter(new UserAdapter(list));

        return view;
    }
}