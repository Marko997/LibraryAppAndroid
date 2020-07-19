package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button loginButton, registerButton;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        email = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        fAuth = FirebaseAuth.getInstance();

        loginButton= findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailQ = email.getText().toString().trim();
                String passwordQ = password.getText().toString().trim();


                if(TextUtils.isEmpty(emailQ)){
                    email.setError("Korisnicko ime je obavezno i polje ne sme biti prazno!");
                    return;
                }

                if(TextUtils.isEmpty(passwordQ)){
                    password.setError("Sifra je obavezna i polje ne sme biti prazno!");
                    return;
                }

                if(passwordQ.length()<8){
                    password.setError("Sifra mora biti duza od 8 karaktera!");
                    return;
                }

                fAuth.signInWithEmailAndPassword(emailQ,passwordQ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Uspesno logovanje!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this,"Dogodila se greska!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        registerButton = (Button) findViewById(R.id.buttonReg);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(startIntent);

            }
        });
    }




}
