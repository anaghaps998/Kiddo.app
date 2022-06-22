package com.example.kiddoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_tags extends BaseAdapter {
    String[] tag,description;
    Context context;

public Custom_view_tags(Context applicationContext, String[] tag, String[] description)
{
    this.context = applicationContext;
    this.tag = tag;
    this.description = description;
}
    @Override
    public int getCount() {
        return tag.length;
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
            gridView=inflator.inflate(R.layout.activity_customviewtags,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }

        TextView tv2=(TextView)gridView.findViewById(R.id.textView22);
        ImageView im=(ImageView)gridView.findViewById(R.id.imageView7);



        tv2.setTextColor(Color.BLACK);
//        tv3.setTextColor(Color.BLACK);



        tv2.setText(tag[i]);
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

        return gridView;
    }
}
