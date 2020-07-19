

package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    EditText inputNaslov;
    EditText inputKategorija;
    EditText inputImePrezimeAutora;
    EditText inputExcerpt;
    EditText inputImage;
    Button btnSacuvaj;
    Long id;

    DatabaseReference reff;

    BookModel book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        inputNaslov = (EditText)findViewById(R.id.inputNaslov);
        inputImePrezimeAutora = (EditText)findViewById(R.id.inputImePrezimeAutora);
        inputKategorija = (EditText)findViewById(R.id.inputKategorija);
        inputExcerpt = (EditText)findViewById(R.id.inputExcerpt);
        inputImage = (EditText)findViewById(R.id.inputImage);
        btnSacuvaj = (Button)findViewById(R.id.btnSacuvaj);


        book = new BookModel();







        reff = FirebaseDatabase.getInstance().getReference().child("Book");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    id = (snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputNaslov.getText().toString().equals("") && !inputKategorija.getText().toString().equals("") && !inputExcerpt.getText().toString().equals("") && !inputImage.getText().toString().equals("")&& !inputImePrezimeAutora.getText().toString().equals("")){
                book.setTitle(inputNaslov.getText().toString().trim());
                book.setCategory(inputKategorija.getText().toString().trim());
                book.setAuthor(inputImePrezimeAutora.getText().toString().trim());
                book.setExcerpt(inputExcerpt.getText().toString().trim());
                book.setImage(inputImage.getText().toString().trim());

                reff.child(String.valueOf(id+1)+" - "+book.getTitle()).setValue(book);
                Toast.makeText(AddBookActivity.this,"Knjiga uspesno dodata!",Toast.LENGTH_LONG).show();


            }

        };




    });



}
}


