package com.example.kiddoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class AddInterests extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    EditText ed;
    ListView lv;
    Button b, b9;
    SharedPreferences sh;
    String ip, url;
    int i;
    String[] nm = {"Location", "School name", "Sports", "Movie", "Cartoons", "Vlogs", "Subjects", "Science", "Languages", "Ambition", "News Topics", "Genres of books", "Drawings", "Arts", "Music", "Places you like to visit", "Tv Shows", "Hobbies", "Fav Song", "Dance Videos", "Artists", "Fashion", "Magazines","Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_interests);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tv = (TextView) findViewById(R.id.textView7);
        ed = (EditText) findViewById(R.id.editTextTextPersonName);


        tv.setText(nm[0]);


        SharedPreferences.Editor ede = sh.edit();
        ede.putInt("qid", 0);
        ede.putInt("tot",nm.length);
        ede.commit();

        b = (Button) findViewById(R.id.button5);
        b.setOnClickListener(this);
        b9 = (Button) findViewById(R.id.button9);
        b9.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == b9) {
            int k = sh.getInt("qid", 0);

            tv.setText(nm[k+1]);

            SharedPreferences.Editor ede = sh.edit();
            ede.putInt("qid", k+1);

            ede.commit();

        } else {

            int flg=0;
            final String st=tv.getText().toString();
            final String text=ed.getText().toString();
            if(text.length()<0){
                flg++;
                ed.setError("Enter a value");
            }

            if(flg==0) {

                ip = sh.getString("ip", "");
                url = sh.getString("url", "") + "/AddInterests";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                Toast.makeText(AddInterests.this, "Interest added", Toast.LENGTH_SHORT).show();

                                        int k = sh.getInt("qid", 0);
                                        if (k == sh.getInt("tot", 0) - 1) {
                                            Intent ij = new Intent(getApplicationContext(), ViewChild.class);
                                            startActivity(ij);
                                        } else {
                                            tv.setText(nm[k + 1]);
                                            SharedPreferences.Editor ede = sh.edit();
                                            ede.putInt("qid", k + 1);

                                            ede.commit();
                                            ed.setText("");
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
                                // err
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("interest", tv.getText().toString());
                        params.put("text", ed.getText().toString());
                        params.put("id", sh.getString("chid", ""));


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
    }
}