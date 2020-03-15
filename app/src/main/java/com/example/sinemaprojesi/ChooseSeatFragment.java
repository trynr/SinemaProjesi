package com.example.sinemaprojesi;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ChooseSeatFragment extends Fragment {

    View view;
    LinearLayout seat_container_layout;
    LinearLayout horizontal_layouts[];
    int seat_states[];
    int koltukSayisi;
    int row, column;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_seat, container, false);

        row = 20;   column = 10;
        koltukSayisi = row * column;

        seat_states = new int[koltukSayisi];
        seat_container_layout = view.findViewById(R.id.seat_container_layout);
        horizontal_layouts = new LinearLayout[20];

        for(int i = 0; i < koltukSayisi; i++){
            int random_number = (int) (Math.random()*2);

            seat_states[i] = random_number;
        }


        for(int i = 0; i < row; i++){
            horizontal_layouts[i] = new LinearLayout(getContext());

            for(int j = 0; j < column; j++){
                ImageView imageView = new ImageView(getContext());

                if(seat_states[i*column + j] == 0){
                    imageView.setBackgroundResource(R.drawable.seat_empty2);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentLayoutTicket,new OperationSuccessFragment())
                                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        }
                    });
                } else{
                    imageView.setBackgroundResource(R.drawable.seat_reserved2);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Başka bir koltuk seçiniz!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                horizontal_layouts[i].addView(imageView);

            }

            seat_container_layout.addView(horizontal_layouts[i]);
        }


        return view;
    }
}
