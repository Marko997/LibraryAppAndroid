package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class BookCatalogue extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView bookList;
    private DatabaseReference reff;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_catalogue);

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

        reff = FirebaseDatabase.getInstance().getReference().child("Book");
        reff.keepSynced(true);

        bookList=(RecyclerView)findViewById(R.id.recycleView);
        bookList.setHasFixedSize(true);
        bookList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Query firebaseQuery = reff.orderByChild("title");
        FirebaseRecyclerAdapter<BookModel,BookViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<BookModel, BookViewHolder>
                (BookModel.class,R.layout.card_row,BookViewHolder.class,firebaseQuery)
        {
            @Override
            protected void populateViewHolder(BookViewHolder bookViewHolder, BookModel bookModel, int i) {
                bookViewHolder.setTitle(bookModel.getTitle());
                bookViewHolder.setCategory(bookModel.getCategory());
                bookViewHolder.setAuthor(bookModel.getAuthor());
                bookViewHolder.setExcerpt(bookModel.getExcerpt());
                bookViewHolder.setImage(getApplicationContext(),bookModel.getImage());
            }
        };

        bookList.setAdapter(firebaseRecyclerAdapter);
    }

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
