package org.rotaract9210.d9210events.SharedClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.rotaract9210.d9210events.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 9/8/2016.
 */
public class GridViewAdapter extends ArrayAdapter<ImageItem> {
    private Context context;
    private int resource;
    private ArrayList<ImageItem> data = new ArrayList();

    public GridViewAdapter(Context context, ArrayList<ImageItem> data) {
        super(context, R.layout.layout_grid_item, data);
        this.context = context;
        this.resource = R.layout.layout_grid_item;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.layout_grid_item, parent,false);
        }

        ImageView ivImage = (ImageView)v.findViewById(R.id.ivGallery_Pic);
        //extView tvName = (TextView)v.findViewById(R.id.tvGallery_Name);

        ImageItem pic = data.get(position);
        ivImage.setImageBitmap(pic.getImage());
        return v;
    }
}
