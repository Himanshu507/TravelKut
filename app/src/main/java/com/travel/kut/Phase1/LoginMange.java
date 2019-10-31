package com.travel.kut.Phase1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.travel.kut.Phase1.Adapter.FragmentAdapter;
import com.travel.kut.R;

public class LoginMange extends AppCompatActivity {


    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;
    TabLayout tabLayout;
    TabItem signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mange);

        init_views();
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void init_views() {

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        signIn = findViewById(R.id.tabitem1);
        signUp = findViewById(R.id.tabitem2);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

    }
}
