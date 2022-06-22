package com.example.kiddoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class customviewinterests extends BaseAdapter {

    String[] tag,description,int_id;
    private Context context;

    public customviewinterests(Context applicationContext,String[] tag, String[] description, String[] int_id) {
        this.context = applicationContext;
        this.tag = tag;
        this.description = description;

        this.int_id = int_id;

    }


    @Override
    public int getCount() {
        return description.length ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_customviewinterests,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }

        TextView tv2=(TextView)gridView.findViewById(R.id.textView22);
        ImageView im=(ImageView)gridView.findViewById(R.id.imageView7);

        ImageView imi=(ImageView)gridView.findViewById(R.id.imageView8);
imi.setTag(int_id[i]);
imi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//                final String maclis=sh.getString("mac_list","");
//                String uid=sh.getString("uid","");
//                String hu = sh.getString("ip", "");
//                String url = "http://" + hu + ":5000/deleteChild";

        sh= PreferenceManager.getDefaultSharedPreferences(context);
        String  ip=sh.getString("ip","");
        String  url=sh.getString("url","")+"/deleteint";

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


                params.put("intid",view.getTag().toString());


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

        tv2.setTextColor(Color.BLACK);
//        tv3.setTextColor(Color.BLACK);



        tv2.setText(description[i]);
        if(tag[i].equalsIgnoreCase("Location"))
        {
            Picasso.with(context).load(R.drawable.location). into(im);
        }
        if(tag[i].equalsIgnoreCase("School name"))
        {
            Picasso.with(context).load(R.drawable.school). into(im);
        }

        if(tag[i].equalsIgnoreCase("Sports"))
        {
            Picasso.with(context).load(R.drawable.sports). into(im);
        }

        if(tag[i].equalsIgnoreCase("Movie"))
        {
            Picasso.with(context).load(R.drawable.movies). into(im);
        }

        if(tag[i].equalsIgnoreCase("Cartoons"))
        {
            Picasso.with(context).load(R.drawable.cartoon). into(im);
        }

        if(tag[i].equalsIgnoreCase("Vlogs"))
        {
            Picasso.with(context).load(R.drawable.vlogs). into(im);
        }

        if(tag[i].equalsIgnoreCase("Subjects"))
        {
            Picasso.with(context).load(R.drawable.subject). into(im);
        }


        if(tag[i].equalsIgnoreCase("Science"))
        {
            Picasso.with(context).load(R.drawable.science). into(im);
        }

        if(tag[i].equalsIgnoreCase("Languages"))
        {
            Picasso.with(context).load(R.drawable.language). into(im);
        }

        if(tag[i].equalsIgnoreCase("Ambition"))
        {
            Picasso.with(context).load(R.drawable.ambition). into(im);
        }

        if(tag[i].equalsIgnoreCase("Drawings"))
        {
            Picasso.with(context).load(R.drawable.drawing). into(im);
        }

        if(tag[i].equalsIgnoreCase("Arts"))
        {
            Picasso.with(context).load(R.drawable.arts). into(im);
        }

        if(tag[i].equalsIgnoreCase("Music"))
        {
            Picasso.with(context).load(R.drawable.music). into(im);
        }

        if(tag[i].equalsIgnoreCase("Places you like to visit"))
        {
            Picasso.with(context).load(R.drawable.places). into(im);
        }

        if(tag[i].equalsIgnoreCase("Genres of books"))
        {
            Picasso.with(context).load(R.drawable.books). into(im);
        }
        if(tag[i].equalsIgnoreCase("Fav Song"))
        {
            Picasso.with(context).load(R.drawable.song). into(im);
        }
        if(tag[i].equalsIgnoreCase("Tv Shows"))
        {
            Picasso.with(context).load(R.drawable.tv). into(im);
        }

        if(tag[i].equalsIgnoreCase("Dance Videos"))
        {
            Picasso.with(context).load(R.drawable.dance). into(im);
        }
        if(tag[i].equalsIgnoreCase("Artists"))
        {
            Picasso.with(context).load(R.drawable.artist). into(im);
        }if(tag[i].equalsIgnoreCase("Fashion"))
        {
            Picasso.with(context).load(R.drawable.fashion). into(im);
        }if(tag[i].equalsIgnoreCase("Magazines"))
        {
            Picasso.with(context).load(R.drawable.maagazine). into(im);
        }

        if(tag[i].equalsIgnoreCase("Hobbies"))
        {
            Picasso.with(context).load(R.drawable.hobbies). into(im);
        }


        if(tag[i].equalsIgnoreCase("News Topics"))
        {
            Picasso.with(context).load(R.drawable.news). into(im);
        }
        if(tag[i].equalsIgnoreCase("Others"))
        {
            Picasso.with(context).load(R.drawable.more). into(im);
        }



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//        String url="http://" + ip + ":5000"+photo[i];
//
//        Picasso.with(context).load(url). into(im);//circle






















        ImageView imoi=(ImageView)gridView.findViewById(R.id.imageView9);
        imoi.setTag(i);
        imoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j=Integer.parseInt(view.getTag().toString());
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("intid",int_id[j]);
                ed.putString("description",description[j]);
                ed.putString("tag",tag[j]);
                ed.commit();


                Intent ii=new Intent(context,EditInterests.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
            }
        });













        return gridView;
    }
}
