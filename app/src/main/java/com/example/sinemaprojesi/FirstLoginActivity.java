package com.example.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import static com.example.sinemaprojesi.AdminOrUserActivity.state;

public class FirstLoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    EditText email_et, password_et;
    TextView state_tv;
    CardView login_cv, register_cv, google_login_cv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        state_tv = findViewById(R.id.state_tv);
        login_cv = findViewById(R.id.login_cv);
        register_cv = findViewById(R.id.register_cv);
        google_login_cv = findViewById(R.id.google_login_cv);
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();

                if(mFirebaseUser != null){
                    Intent intent = new Intent(FirstLoginActivity.this, MainActivity.class);

                    startActivity(intent);
                }

            }
        };

        register_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLoginActivity.this, SignUpActivity.class);

                startActivity(intent);
            }
        });

        login_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(FirstLoginActivity.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();
                } else{

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(FirstLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(FirstLoginActivity.this, "Başarıyla giriş yaptınız!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(FirstLoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(FirstLoginActivity.this, "Giriş başarısız!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });


        if(state == 1){ //Yönetici ise
            state_tv.setText("Yönetici Girişi");
            register_cv.setVisibility(View.INVISIBLE);
            google_login_cv.setVisibility(View.INVISIBLE);

        } else{ //Müşteri ise
            state_tv.setText("Müşteri Girişi");

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthStateListener);
    }
}
