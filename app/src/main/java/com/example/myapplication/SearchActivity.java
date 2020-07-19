package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.mainDrawer);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        mUserDatabase = FirebaseDatabase.getInstance().getReference("Book");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseBookSearch(searchText);

            }
        });

    }

    private void firebaseBookSearch(String searchText) {

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");


        FirebaseRecyclerAdapter<BookModel, BookViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BookModel, BookViewHolder>(

                BookModel.class,
                R.layout.card_row,
                BookViewHolder.class,
                firebaseSearchQuery


        ) {
            @Override
            protected void populateViewHolder(BookViewHolder bookViewHolder, BookModel bookModel, int position) {


                bookViewHolder.setTitle(bookModel.getTitle());
                bookViewHolder.setCategory(bookModel.getCategory());
                bookViewHolder.setAuthor(bookModel.getAuthor());
                bookViewHolder.setExcerpt(bookModel.getExcerpt());
                bookViewHolder.setImage(getApplicationContext(),bookModel.getImage());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    // View Holder Class

    public static class BookViewHolder extends RecyclerView.ViewHolder{
        View v;
        public BookViewHolder(View bookView){
            super(bookView);
            v = bookView;
        }
        public void setTitle(String title){
            TextView bookTitle = (TextView)v.findViewById(R.id.bookTitle);
            bookTitle.setText(title);
        }

        public void setExcerpt(String excerpt){
            TextView bookExcerpt = (TextView)v.findViewById(R.id.excerpt);
            bookExcerpt.setText(excerpt);
        }
        public void setAuthor(String author){
            TextView bookAuthor = (TextView)v.findViewById(R.id.author);
            bookAuthor.setText(author);
        }

        public void setCategory(String category){
            TextView bookCategory = (TextView)v.findViewById(R.id.category);
            bookCategory.setText(category);
        }

        public void setImage(Context ctx,String image){
            ImageView bookImage = (ImageView)v.findViewById(R.id.bookImage);
            Glide.with(ctx).load(image).into(bookImage);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout: {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }break;
            case R.id.home: {
                startActivity(new Intent(getApplicationContext(),HomePageActivity.class));

            }break;
            case R.id.books: {
                startActivity(new Intent(getApplicationContext(),BookCatalogue.class));

            }break;
            case R.id.search: {
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));

            }break;
            case R.id.addBook: {
                startActivity(new Intent(getApplicationContext(),AddBookActivity.class));

            }break;
        }
        return false;
    }
}
