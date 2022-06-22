package com.example.kiddoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class view_videos extends AppCompatActivity {


 TextView t1;
 ListView li;
    SharedPreferences sh;
    String ip, url, lid;
    String [] link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_videos2);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("tagname","");
        ed.putString("answer","");
        ed.commit();


//        t1=findViewById(R.id.textView10);
        li=findViewById(R.id.li2);
        ip = sh.getString("url", "");
        url = ip + "/viewVideos";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                        try {
//                            JSONObject jsonObj = new JSONObject(response);
//                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//
//
//                                String link = jsonObj.getString("link");
//
//                                SharedPreferences.Editor ed = sh.edit();
//                                ed.putString("link", link);
//                                ed.commit();
//                                t1.setText(link);
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                                startActivity(intent);
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                link = new String[js.length()];
//                                date= new String[js.length()];
//                                rating= new String[js.length()];





                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    link[i] = u.getString("tagtype");//dbcolumn name in double quotes
//                                    date[i] = u.getString("rating");
//                                    rating[i] = u.getString("date");



                                }
                           //     li.setAdapter(new customviewvideos(getApplicationContext(),link));

                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", sh.getString("pid", ""));//passing to python
                params.put("pid", sh.getString("pid", ""));//passing to python


                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);


    }
}
