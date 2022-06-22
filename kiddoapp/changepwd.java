package com.example.kiddoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class changepwd extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText uname;
    EditText cur_pwd;
    EditText new_pwd;
    EditText confirm_pwd;
    Button btn;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        uname=(EditText)findViewById(R.id.un1);
        cur_pwd = (EditText) findViewById(R.id.cur_pwd);
        new_pwd = (EditText) findViewById(R.id.new_pwd);
        confirm_pwd = (EditText) findViewById(R.id.confirm_pwd);
        confirm_pwd.addTextChangedListener(this);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        int flg=0;
        final String username=uname.getText().toString();
        final String current_password = cur_pwd.getText().toString();
        final String new_password = new_pwd.getText().toString();
        final String confirm_password = confirm_pwd.getText().toString();
        if (current_password.length()==0){
            cur_pwd.setError("Field cannot be empty");
            flg++;
        }
         if (uname.length()==0){
            cur_pwd.setError("Field cannot be empty");
             flg++;
        }
         if (new_password.length()==0) {
             flg++;
            new_pwd.setError("Field cannot be empty");
        }
         if (confirm_password.length()==0) {
             flg++;
            confirm_pwd.setError("Field cannot be empty");
        }
        if(flg==0) {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");
            url=sh.getString("url","")+"/changepwd";


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            // response
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), login.class);
                                    startActivity(i);


                                }


                                // }
                                else {
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
                @Override
                protected Map<String, String> getParams() {
                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Map<String, String> params = new HashMap<String, String>();

                    String id = sh.getString("lid", "");
                    params.put("username",username);
                    params.put("current_password", current_password);
                    params.put("new_password",new_password);
                    params.put("confirm_password", confirm_password);
                    params.put("id",sh.getString("lid",""));




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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if(!confirm_pwd.getText().toString().equalsIgnoreCase(new_pwd.getText().toString()))
        {
            confirm_pwd.setError("Mismatch");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}




