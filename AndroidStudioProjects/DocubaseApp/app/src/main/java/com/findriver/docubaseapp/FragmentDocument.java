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

public class FragmentDocument extends Fragment {
    private RecyclerView recyclerView;
    private List<DocumentListe> list = new ArrayList<>();


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

        DocumentListe doc1 = new DocumentListe("M2 GL");
        DocumentListe doc2 = new DocumentListe("M1 TW");

        list.add(doc1);
        list.add(doc2);

        recyclerView.setAdapter(new DocumentAdapter(list));

        return view;
    }
}