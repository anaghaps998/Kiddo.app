package com.example.kiddoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

public class Launch_me extends AppCompatActivity  {
TextView ttc;
Handler hnd;
Runnable rr=new Runnable() {
    @Override
    public void run() {
        Intent ij=new Intent(getApplicationContext(),ip_page.class);
        startActivity(ij);
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_me);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        hnd=new Handler();
        hnd.postDelayed(rr,3000);
    }
//
//    @Override
//    public void onClick(View view) {
//        Intent ij=new Intent(getApplicationContext(),ip_page.class);
//        startActivity(ij);
//    }
}