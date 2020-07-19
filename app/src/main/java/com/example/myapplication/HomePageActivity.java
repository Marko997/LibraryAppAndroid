package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

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




        Button buttonPregled = (Button) findViewById(R.id.buttonPregled);

        buttonPregled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),BookCatalogue.class);
                startActivity(startIntent);

            }
        });
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
