package com.example.sinemaprojesi;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChooseSaloonFragment extends Fragment {
    View view;
    CardView saloon1, saloon2, saloon3;
    static int saloon_no = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_choose_saloon, container, false);

         saloon1 = view.findViewById(R.id.saloon1);
         saloon2 = view.findViewById(R.id.saloon2);
         saloon3 = view.findViewById(R.id.saloon3);

         saloon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saloon_no = 1;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSeatFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        saloon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saloon_no = 2;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSeatFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        saloon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saloon_no = 3;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSeatFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });



         return view;
    }
}
