package com.example.sinemaprojesi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinemaprojesi.Models.Film;
import com.example.sinemaprojesi.R;
import com.example.sinemaprojesi.TicketActivity;

import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.define> {

    Context context;
    List<Film> list;
    Activity activity;

    public FilmsAdapter(List<Film> list, Context context, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }


    @Override
    public define onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filmsadapter_layout,parent,false);


        return new define(view);
    }

    @Override
    public void onBindViewHolder(define holder, final int position) {
        holder.name_tv.setText("Filmin Adı: " + list.get(position).getFilm_name());
        holder.director_tv.setText(list.get(position).getDirector_name());
        holder.img.setImageResource(list.get(position).getFilmImage_id());
        holder.film_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TicketActivity.class);

                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class define extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView director_tv, name_tv;
        LinearLayout film_layout;

        public define(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.film_image);
            name_tv = itemView.findViewById(R.id.film_name);
            director_tv = (TextView) itemView.findViewById(R.id.film_director_tv);
            film_layout = itemView.findViewById(R.id.film_layout);
        }
    }

}
