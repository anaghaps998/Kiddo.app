package com.example.kiddoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class ViewChild extends AppCompatActivity {

    ListView lv;
    SharedPreferences sh;
    String url,lid,ip;
    String[] name,age,gender,photo,class_name,schoolname,phoneno,emailid,place,chid,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_child);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv=(ListView)findViewById(R.id.listin);
        ip=sh.getString("url","");
        url=ip+"/viewChild";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                name= new String[js.length()];
                                age = new String[js.length()];
                                gender= new String[js.length()];
                                photo= new String[js.length()];
                                class_name = new String[js.length()];
                                schoolname= new String[js.length()];
                                phoneno= new String[js.length()];
                                emailid= new String[js.length()];
                                place= new String[js.length()];

                                chid = new String[js.length()];
                                status = new String[js.length()];

                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    //dbcolumn name in double quotes
                                    name[i] = u.getString("cname");
                                    age[i] = u.getString("age");
                                    gender[i] = u.getString("gender");
                                    photo[i] = u.getString("photo");
                                    class_name[i] = u.getString("class");
                                    schoolname[i] = u.getString("school");
                                    phoneno[i] = u.getString("school");
                                    emailid[i] = u.getString("username");
                                    place[i] = u.getString("username");
                                    chid[i] = u.getString("clid");

                                    status[i] = u.getString("status");
                                }
                                lv.setAdapter(new customviewchild(getApplicationContext(),name,age,gender,photo,class_name,schoolname,chid,status));//custom_view_service.xml and li is the listview object


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
                params.put("id", sh.getString("pid",""));//passing to python
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ij=new Intent(getApplicationContext(),Home.class);
        startActivity(ij);
    }

//    @Override

//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//    }
}
