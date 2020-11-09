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


public class FragmentAffichage extends Fragment {
    private RecyclerView recyclerView;
    private List<AffichageListe> list = new ArrayList<>();

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

        AffichageListe affichage1 = new AffichageListe("Superman dalilou" ,"Etudiant", "Appel a tout les etudiants MASTER 1 GL, le dernier délai de l’exercice AIE est pour le 30 AOUT 2020, vous devez envoyer le document PDF à l’email de l’université.");
        AffichageListe affichage2 = new AffichageListe("Jimbo nitrat" ,"Administrateur", "Appel a tout les etudiants MASTER 1 GL, le dernier délai de l’exercice AIE est pour le 30 AOUT 2020, vous devez envoyer le document PDF à l’email de l’université.");

        list.add(affichage1);
        list.add(affichage2);

        recyclerView.setAdapter(new AffichageAdapter(list));

        return view;
    }
}