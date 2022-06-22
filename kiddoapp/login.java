package com.example.kiddoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements View.OnClickListener {

    TextView t,tp;
    EditText user;
    EditText pwd;
    Button loginbtn;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=(EditText)findViewById(R.id.editTextTextEmailAddress);
        pwd=(EditText)findViewById(R.id.editTextTextPassword);
        loginbtn=(Button)findViewById(R.id.button2);
        t=(TextView)findViewById(R.id.textView33);
//        tp=(TextView)findViewById(R.id.textview32);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nt=new Intent(getApplicationContext(),parentReg.class);
                startActivity(nt);

            }
        });

//        tp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent n=new Intent(getApplicationContext(),changepwd.class);
//                startActivity(n);
//            }
//        });
        loginbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        final String username = user.getText().toString();
        final String password=pwd.getText().toString();
        if(username.length()==0)
        {
            user.setError("");
        }
        else if (password.length()==0)
        {
            pwd.setError("");
        }
        else {
            sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sh.getString("ip", "");
            url = sh.getString("url", "") + "/login";


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                Toast.makeText(Login.this, "welcome", Toast.LENGTH_SHORT).show();
                                    String typ = jsonObj.getString("type");
                                    String id = jsonObj.getString("lid");

                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("pid", id);
                                    ed.commit();
                                    if (typ.equalsIgnoreCase("parent")) {
                                        Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), Home.class);
                                        startActivity(i);
                                    } else if (typ.equalsIgnoreCase("child")) {
//                                    String pd = jsonObj.getString("pid");
                                        SharedPreferences.Editor e = sh.edit();
//                                    e.putString("pid", pd);
                                        e.putString("lid", jsonObj.getString("lid"));
                                        e.commit();

                                        Intent i = new Intent(getApplicationContext(), child_home.class);
                                        startActivity(i);
                                    }

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

                    params.put("u", username);//passing to python
                    params.put("p", password);


                    return params;
                }
            };


            int MY_SOCKET_TIMEOUT_MS = 200000;

            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);

        }

    }

}



