package com.findriver.docubaseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class AffichageAdapter extends RecyclerView.Adapter<AffichageAdapter.Affichages> {
    private List<AffichageListe> list;

    public AffichageAdapter(List<AffichageListe> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AffichageAdapter.Affichages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.affichage_card,parent,false);

        return new AffichageAdapter.Affichages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AffichageAdapter.Affichages holder, int position) {
        holder.titre.setText(list.get(position).getTitre());
        holder.nom.setText(list.get(position).getNom());
        holder.description.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Affichages extends RecyclerView.ViewHolder{
        private TextView titre, nom, description;
        private CardView card_affichage;

        public Affichages(@NonNull View itemView) {
            super(itemView);
            titre = (TextView) itemView.findViewById(R.id.titre_affichage);
            nom = (TextView) itemView.findViewById(R.id.nom_prof);
            description = (TextView) itemView.findViewById(R.id.content_affichage);
            card_affichage = (CardView) itemView.findViewById(R.id.affichage_card);
        }
    }
}
