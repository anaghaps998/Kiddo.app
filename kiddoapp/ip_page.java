package com.example.kiddoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ip_page extends AppCompatActivity implements View.OnClickListener {
    TextView IP;
    EditText ed_ip;
    Button btn;
    SharedPreferences sh;
    String ip;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),Launch_me.class);
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_page);
        ed_ip = (EditText) findViewById(R.id.en);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        ed_ip.setText("192.168.138.131");
    }
    @Override
    public void onClick(View view) {
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final String ip_connect = ed_ip.getText().toString();
        String url1 = "http://" + ip_connect + ":5000";
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("ip",ip);
        ed.putString("url",url1);
        ed.commit();
        Intent i=new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }
}