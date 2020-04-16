package com.chetan.allinone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    String x = "open", y = "close";
    TextView tv;
    ImageView imageView;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        parentLayout = findViewById(android.R.id.content);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new MainFragment()).commit();

        }
        View headerView = navigationView.getHeaderView(0);
        tv = headerView.findViewById(R.id.menuText);
        imageView = headerView.findViewById(R.id.menuImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new MainFragment()).commit();
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new MainFragment()).commit();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.jokesMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new JokesFragment()).commit();
                break;
            case R.id.chanakyaMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ChanakyaFragment()).commit();
                break;
            case R.id.factsMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FactsFragment()).commit();
                break;
            case R.id.shayariMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ShayariFragment()).commit();
                break;

            case R.id.wallpaperMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new WallpaperFragment()).commit();
                break;
            case R.id.quotesMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new QuotesFrgament()).commit();
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.chetan.allinone");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
        {


        } else {
            Snackbar snackbar = Snackbar.make(parentLayout, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
