package com.example.kiddoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class customviewchild extends BaseAdapter {
    String[] name,age,gender,photo,classname,schoolname,phoneno,emailid,place,chid,status;
    private Context context;
    Button b1;

//    public customviewchild(Context applicationContext, String[] name, String[] date,String [] rating) {



//
    public customviewchild(Context applicationContext,String[] name, String[] age, String[] gender, String[] photo,String[] class_name, String[] schoolname, String[] chid, String[] status) {
        this.context = applicationContext;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.photo = photo;
        this.classname = class_name;
        this.schoolname = schoolname;
        this.chid = chid;
        this.status=status;
//        this.emailid = email;
//        this.place = place;


    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_customviewchild,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.t1);
        TextView tv2=(TextView)gridView.findViewById(R.id.t2);
        TextView tv3=(TextView)gridView.findViewById(R.id.t3);

        TextView tv5=(TextView)gridView.findViewById(R.id.t4);
        TextView tv6=(TextView)gridView.findViewById(R.id.t5);
//        TextView tv7=(TextView)gridView.findViewById(R.id.t6);
//        TextView tv8=(TextView)gridView.findViewById(R.id.t7);
//        TextView tv9=(TextView)gridView.findViewById(R.id.t8);
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView4);
        Button bt=(Button) gridView.findViewById(R.id.button6);
        bt.setTag(chid[i]);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//                final String maclis=sh.getString("mac_list","");
//                String uid=sh.getString("uid","");
//                String hu = sh.getString("ip", "");
//                String url = "http://" + hu + ":5000/deleteChild";

                sh= PreferenceManager.getDefaultSharedPreferences(context);
                String  ip=sh.getString("ip","");
                String  url=sh.getString("url","")+"/deleteChild";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


                                        Intent ii=new Intent(context,ViewChild.class);
                                        ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(ii);



                                    }

                                    // }
                                    else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                }    catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("chid",view.getTag().toString());


                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS=100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }
        });


        Button bt1=(Button) gridView.findViewById(R.id.button7);
        bt1.setTag(chid[i]);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("chid",view.getTag().toString());
                ed.commit();


                Intent ii=new Intent(context,AddInterests.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
            }
        });

        Button bt2=(Button) gridView.findViewById(R.id.button8);
        bt2.setTag(chid[i]);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("chid",view.getTag().toString());
                ed.commit();


                Intent ii=new Intent(context,ViewInterests.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
            }
        });



//        tv1.setTextColor(Color.te);//color setting
//        tv2.setTextColor(Color.BLACK);
//        tv3.setTextColor(Color.BLACK);
//        tv5.setTextColor(Color.BLACK);
//        tv6.setTextColor(Color.BLACK);

        tv1.setText(name[i]);
        tv2.setText(age[i]);
        tv3.setText(gender[i]);

        tv5.setText(classname[i]);
        tv6.setText(schoolname[i]);

        Button bt3=(Button) gridView.findViewById(R.id.button9);
if(status[i].equalsIgnoreCase("running"))
{
    bt3.setText("Disable");
}
else{
    bt3.setText("Enable");
}



        bt3.setTag(i);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             final    int ik=Integer.parseInt(view.getTag().toString());
                if(status[ik].equalsIgnoreCase("running")) {
                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                    sh= PreferenceManager.getDefaultSharedPreferences(context);
                    String  ip=sh.getString("ip","");
                    String  url=sh.getString("url","")+"/updateChild";

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


                                            Intent ii=new Intent(context,ViewChild.class);
                                            ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(ii);



                                        }

                                        // }
                                        else {
                                            Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    }    catch (Exception e) {
                                        Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                            Map<String, String> params = new HashMap<String, String>();


                            params.put("chid",chid[ik]);
                            params.put("type","stop");


                            return params;
                        }
                    };

                    int MY_SOCKET_TIMEOUT_MS=100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);


                }
                else{
                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);


                    sh= PreferenceManager.getDefaultSharedPreferences(context);
                  String  ip=sh.getString("ip","");
                  String  url=sh.getString("url","")+"/updateChild";

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


                                            Intent ii=new Intent(context,ViewChild.class);
                                            ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(ii);



                                        }

                                        // }
                                        else {
                                            Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    }    catch (Exception e) {
                                        Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                            Map<String, String> params = new HashMap<String, String>();


                            params.put("chid",chid[ik]);
                            params.put("type","running");

                            return params;
                        }
                    };

                    int MY_SOCKET_TIMEOUT_MS=100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);





                }
            }
        });

//            ImageView im=(ImageView) gridView.findViewById(R.id.imageView101);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");
        String url="http://" + ip + ":5000"+photo[i];

        Picasso.with(context).load(url). into(im);//circle

        return gridView;


    }

}
