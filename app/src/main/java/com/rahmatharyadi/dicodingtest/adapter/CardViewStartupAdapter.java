package com.rahmatharyadi.dicodingtest.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rahmatharyadi.dicodingtest.PageDetailsActivity;
import com.rahmatharyadi.dicodingtest.R;
import com.rahmatharyadi.dicodingtest.model.Startup;

import java.util.ArrayList;

public class CardViewStartupAdapter extends RecyclerView.Adapter<CardViewStartupAdapter.CardViewViewHolder> {

    private ArrayList<Startup> listStartups;

    public CardViewStartupAdapter(ArrayList<Startup> list) {
        this.listStartups = list;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_startup, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        Startup startup = listStartups.get(position);

        holder.startupName.setText(startup.getName());

        Glide.with(holder.itemView.getContext())
                .load(listStartups.get(position).getPicture())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.startupIcon);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Kamu memilih " +
                        listStartups.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(holder.itemView.getContext(), PageDetailsActivity.class);
                intent.putExtra("name", listStartups.get(position).getName());
                intent.putExtra("description", listStartups.get(position).getDescription());
                intent.putExtra("picture", listStartups.get(position).getPicture());
                intent.putExtra("web", listStartups.get(position).getWeb());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStartups.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView startupIcon;
        TextView startupName;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            startupIcon = itemView.findViewById(R.id.startupIcon);
            startupName = itemView.findViewById(R.id.startupName);
        }
    }
}
