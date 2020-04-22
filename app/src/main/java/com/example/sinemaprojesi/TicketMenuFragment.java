package com.example.sinemaprojesi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class TicketMenuFragment extends Fragment {
   View view;
   Button buy_ticket_button;
   ImageView film_image_menu;
   TextView film_name_tv_menu, film_director_tv_menu;
   String name, director, image_id, film_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket_menu, container, false);

        buy_ticket_button = view.findViewById(R.id.buy_ticket_button);
        film_name_tv_menu = view.findViewById(R.id.film_name_tv_menu);
        film_director_tv_menu = view.findViewById(R.id.film_director_tv_menu);
        film_image_menu = view.findViewById(R.id.film_image_menu);

        Bundle bundle = getActivity().getIntent().getExtras();

        name = bundle.getString("name");
        director = bundle.getString("director");
        image_id = bundle.getString("imageid");
        film_id = bundle.getString("film_id");

        film_name_tv_menu.setText(name);
        film_director_tv_menu.setText(director);
        Picasso.with(getActivity()).load(image_id).into(film_image_menu);

        buy_ticket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSessionFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });

        return view;
    }
}
