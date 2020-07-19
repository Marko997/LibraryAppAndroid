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

public class RegisterActivity extends AppCompatActivity  {

    Button registerButton;
    EditText username, name,surename,email,password;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        username = findViewById(R.id.userName);
        name = findViewById(R.id.name);
        surename = findViewById(R.id.surename);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton=(Button)findViewById(R.id.btnReg);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameQ = username.getText().toString().trim();
                String nameQ = name.getText().toString().trim();
                String surenameQ = surename.getText().toString().trim();
                String emailQ = email.getText().toString().trim();
                String passwordQ = password.getText().toString().trim();

                if(TextUtils.isEmpty(emailQ)){
                    email.setError("Email je obavezan i polje ne sme biti prazno!");
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

                fAuth.createUserWithEmailAndPassword(emailQ,passwordQ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Uspesna registracija!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,"Dogodila se greska!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }


}
