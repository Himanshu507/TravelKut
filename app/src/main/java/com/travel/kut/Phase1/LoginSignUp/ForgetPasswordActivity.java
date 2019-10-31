package com.travel.kut.Phase1.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.travel.kut.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forget Password");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_);

    }
}
