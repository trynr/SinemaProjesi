package com.example.sinemaprojesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class AdminOrUserActivity extends AppCompatActivity {

    CardView admin_mtcv, user_mtcv;
    public static int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_or_user);

        admin_mtcv = findViewById(R.id.admin_mtcv);
        user_mtcv = findViewById(R.id.user_mtcv);

        admin_mtcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 1; //Yönetici Girişi

                Intent intent = new Intent(AdminOrUserActivity.this, FirstLoginActivity.class);
                startActivity(intent);
            }
        });

        user_mtcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 0; //Müşteri Girişi

                Intent intent = new Intent(AdminOrUserActivity.this, FirstLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
