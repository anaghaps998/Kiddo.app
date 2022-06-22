package com.example.kiddoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Map;

public class Child_ViewRecommendations extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    ListView lv;
ImageView im;
    String url,lid,ip;
String []title,preview,urll,desc,id;
SharedPreferences sh;
EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child__view_recommendations);
        lv=(ListView) findViewById(R.id.li6);
        ed=(EditText)findViewById(R.id.textView23);
        ed.addTextChangedListener(this);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        im=(ImageView) findViewById(R.id.imageView6);
        im.setOnClickListener(this);
        ip=sh.getString("url","");
        url=ip+"/viewVideos";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                     /   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                title= new String[js.length()];
                                preview = new String[js.length()];
                                urll = new String[js.length()];
                                desc = new String[js.length()];

                                id = new String[js.length()];



                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    //dbcolumn name in double quotes
                                    title[i] = u.getString("title");
                                    preview[i] = u.getString("thumbnails");
                                    urll[i] = u.getString("link");
                                    desc[i] = u.getString("description");
                                    id[i] = u.getString("id");
                                }
                                lv.setAdapter( new customviewvideos(getApplicationContext(),title,preview,urll,desc,id));//custom_view_service.xml and li is the listview object


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
                params.put("id", sh.getString("lid",""));//passing to python
                params.put("answer", sh.getString("answer",""));//pa
                params.put("tagname", sh.getString("tagname",""));//pa
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 500000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);


    }

    @Override
    public void onClick(View view) {
        if (view==im)
        {
Intent ij=new Intent(getApplicationContext(),View_tags.class);
startActivity(ij);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        ip=sh.getString("url","");
        url=ip+"/viewVideos_search";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                     /   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                title= new String[js.length()];
                                preview = new String[js.length()];
                                urll = new String[js.length()];
                                desc = new String[js.length()];

                                id = new String[js.length()];



                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    //dbcolumn name in double quotes
                                    title[i] = u.getString("title");
                                    preview[i] = u.getString("thumbnails");
                                    urll[i] = u.getString("link");
                                    desc[i] = u.getString("description");
                                    id[i] = u.getString("id");
                                }
                                lv.setAdapter( new customviewvideos(getApplicationContext(),title,preview,urll,desc,id));//custom_view_service.xml and li is the listview object


                            } else {
                            //    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
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
                params.put("search",ed.getText().toString());
                params.put("id", sh.getString("lid",""));//passing to python
                params.put("answer", sh.getString("answer",""));//pa
                params.put("tagname", sh.getString("tagname",""));//pa
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 500000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}