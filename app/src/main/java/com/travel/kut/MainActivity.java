package com.travel.kut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.kut.Phase1.MainFragments.HomeFragment;
import com.travel.kut.Phase1.MainFragments.NearMeFragment;
import com.travel.kut.Phase1.MainFragments.ProfileFragment;
import com.travel.kut.Phase1.MainFragments.TravelFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init_Views();
    }

    private void init_Views() {
        bottomNavigationView = findViewById(R.id.navigationbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,new HomeFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (menuItem.getItemId() == R.id.home){
                    menuItem.setIcon(getResources().getDrawable(R.drawable.ic_home_blue));
                    transaction.replace(R.id.container,new HomeFragment()).commit();
                    return true;

                }else if (menuItem.getItemId() == R.id.near){
                    menuItem.setIcon(getResources().getDrawable(R.drawable.ic_location_on_blue));
                    transaction.replace(R.id.container,new NearMeFragment()).commit();
                    return true;
                }else if (menuItem.getItemId() == R.id.travel){
                    menuItem.setIcon(getResources().getDrawable(R.drawable.ic_card_travel_blue));
                    transaction.replace(R.id.container,new TravelFragment()).commit();
                    return true;
                }else if(menuItem.getItemId() == R.id.profile){
                    menuItem.setIcon(getResources().getDrawable(R.drawable.ic_person_outline_blue));
                    transaction.replace(R.id.container,new ProfileFragment()).commit();
                    return true;
                }
                return false;
            }
        });

    }
}
