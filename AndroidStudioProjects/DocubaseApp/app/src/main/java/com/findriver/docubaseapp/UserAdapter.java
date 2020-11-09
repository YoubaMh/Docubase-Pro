package com.findriver.docubaseapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class UserAdapter extends RecyclerView.Adapter<UserAdapter.Users>  {
    private List<UserListe> list;


    public UserAdapter(List<UserListe> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public Users onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);

        return new UserAdapter.Users(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.Users holder, int position) {
        holder.nom.setText(list.get(position).getNom());
        holder.role.setText(list.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Users extends RecyclerView.ViewHolder{
        private TextView nom, role;
        private CardView card_user;

        public Users(@NonNull View itemView) {
            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.nom);
            role = (TextView) itemView.findViewById(R.id.role);
            card_user = (CardView) itemView.findViewById(R.id.card_user);
        }
    }

}



