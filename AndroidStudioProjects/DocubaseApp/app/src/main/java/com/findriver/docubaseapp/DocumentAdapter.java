package com.findriver.docubaseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.Documents> {
    private List<DocumentListe> list;


    public DocumentAdapter(List<DocumentListe> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Documents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_card,parent,false);

        return new DocumentAdapter.Documents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Documents holder, int position) {
        holder.titre.setText(list.get(position).getTitre());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Documents extends RecyclerView.ViewHolder{
        private TextView titre;
        private CardView card_document;

        public Documents(@NonNull View itemView) {
            super(itemView);
            titre = (TextView) itemView.findViewById(R.id.titre);
            card_document = (CardView) itemView.findViewById(R.id.card_document);
        }
    }
}
