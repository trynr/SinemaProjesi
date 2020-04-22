package com.example.sinemaprojesi;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.sinemaprojesi.Adapters.FilmsAdapter.selected_film_id;
import static com.example.sinemaprojesi.ChooseSaloonFragment.saloon_no;
import static com.example.sinemaprojesi.ChooseSessionFragment.session_no;

public class ChooseSeatFragment extends Fragment {

    View view;
    LinearLayout seat_container_layout;
    LinearLayout horizontal_layouts[];
    int seat_states[];
    int koltukSayisi;
    int row, column;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    static int db_seat_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_seat, container, false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        db_seat_info = 1;



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
                    final int finalI = i;
                    final int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new AlertDialog.Builder(getContext())
                                    .setTitle("Bilet alımı")
                                    .setMessage("Salon: "+saloon_no+" Sıra: "+(finalI +1)+" Sütun: "+(finalJ +1)+" biletini almak üzeresiniz.\n"+
                                            "Onaylıyor musunuz?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Bilet alımı database'e kaydediliyor.

                                            String ticket_id = "";
                                            for(int i = 0; i < 8; i++){
                                                ticket_id += String.valueOf((int)(Math.random()*10));
                                            }

                                            reference = database.getReference("Users/"+auth.getUid()+"/Tickets/"+selected_film_id+"/"+ticket_id);
                                            reference.setValue(session_no+"/"+saloon_no+"/"+(finalI +1)+"/"+(finalJ +1));
                                            reference = database.getReference("Films/"+selected_film_id+"/Tickets/"+ticket_id);
                                            reference.setValue(session_no+"/"+saloon_no+"/"+(finalI +1)+"/"+(finalJ +1));

                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.fragmentLayoutTicket,new OperationSuccessFragment())
                                                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                    .commit();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null)
                                    .show();



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
