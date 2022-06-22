package com.example.kiddoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubePlayerView;

public class customviewvideos extends BaseAdapter {
    String[] title,link,preview,desc,id;
    private Context context;


    public customviewvideos(Context applicationContext, String[] title, String[] preview, String[] link, String[] desc, String[] id) {
        this.context = applicationContext;
        this.link = link;
this.title=title;
this.preview=preview;
        this.desc=desc;
this.id=id;
    }

    @Override
    public int getCount() {
        return link.length;
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
            gridView=inflator.inflate(R.layout.activity_customviewvideos,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView6);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView8);
        ImageView vv=(ImageView)gridView.findViewById(R.id.videoView);




vv.setTag(i);
vv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SharedPreferences     sh= PreferenceManager.getDefaultSharedPreferences(context);

        int ii=Integer.parseInt(view.getTag().toString());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("id",id[ii]);
        ed.putString("title",title[ii]);
        ed.putString("description",desc[ii]);
        ed.commit();
        Intent ii1=new Intent(context,View_single.class);
        ii1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(ii1);


    }
});


              //  vv.setVideoPath(link[i]);
                //vv.setMediaController(new MediaController(context)); //sets MediaController in the video view

// MediaController containing controls for a MediaPlayer

             //   vv.requestFocus();//give focus to a specific view

//                vv.start();









        tv1.setTextColor(Color.BLACK);//color setting
        tv2.setTextColor(Color.BLACK);//color setting





        tv1.setText(title[i]);
        tv2.setText(desc[i]);



//            ImageView im=(ImageView) gridView.findViewById(R.id.imageView101);

//        String url="http://" + ip + ":5000"+photo[i];
//        Picasso.with(context).load(url). into(im);//circle

        return gridView;

    }

}
