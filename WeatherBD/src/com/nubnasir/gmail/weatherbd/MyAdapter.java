package com.nubnasir.gmail.weatherbd;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String>{
    ArrayList <String> words;
    TypedArray  images;
    private Activity context;

    
    public MyAdapter(Activity context, ArrayList<String> words,TypedArray  images) {
        super(context, R.layout.list_item_xml, words);
        this.context=context;
        this.words=words;
        this.images=images;
  }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
           View view= convertView;
           LayoutInflater inflater= context.getLayoutInflater(); 
           view= inflater.inflate(R.layout.list_item_xml, parent,false);  
           TextView text = (TextView)view.findViewById(R.id.txtItem); 
           Typeface custom_font = Typeface.createFromAsset(context.getAssets(),"banglafont.ttf");
           text.setTypeface(custom_font);
           ImageView img = (ImageView)view.findViewById(R.id.imgItem); 
           text.setText(words.get(position));
           img.setImageResource(images.getResourceId(position, -1));
           return view;
    }

}
