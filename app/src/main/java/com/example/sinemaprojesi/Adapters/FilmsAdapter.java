package com.example.sinemaprojesi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sinemaprojesi.AddNewFilmActivity;
import com.example.sinemaprojesi.Models.Film;
import com.example.sinemaprojesi.R;
import com.example.sinemaprojesi.TicketActivity;
import com.squareup.picasso.Picasso;
import static com.example.sinemaprojesi.FirstLoginActivity.state;

import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.define> {

    Context context;
    List<Film> list;
    Activity activity;
    public static String selected_film_id;

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
        Picasso.with(activity).load(list.get(position).getFilmImage_id()).into(holder.img);
        if(state == 0) holder.edit_button.setVisibility(View.INVISIBLE);
        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddNewFilmActivity.class);
                intent.putExtra("film_id",list.get(position).getFilm_id());
                intent.putExtra("name", list.get(position).getFilm_name());
                intent.putExtra("director", list.get(position).getDirector_name());
                intent.putExtra("imageid", list.get(position).getFilmImage_id());
                intent.putExtra("edit_or_add", "1");

                activity.startActivity(intent);
            }
        });
        //holder.img.setImageResource(list.get(position).getFilmImage_id()); //TODO resim için bakılacak. Sunucudan aldığıma göre String vermeliyim.
        holder.film_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_film_id = list.get(position).getFilm_id();

                Intent intent = new Intent(activity, TicketActivity.class);
                intent.putExtra("film_id",list.get(position).getFilm_id());
                intent.putExtra("name", list.get(position).getFilm_name());
                intent.putExtra("director", list.get(position).getDirector_name());
                intent.putExtra("imageid", list.get(position).getFilmImage_id());
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
        Button edit_button;

        public define(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.film_image);
            name_tv = itemView.findViewById(R.id.film_name);
            director_tv = (TextView) itemView.findViewById(R.id.film_director_tv);
            film_layout = itemView.findViewById(R.id.film_layout);
            edit_button = itemView.findViewById(R.id.edit_button);
        }
    }

}
