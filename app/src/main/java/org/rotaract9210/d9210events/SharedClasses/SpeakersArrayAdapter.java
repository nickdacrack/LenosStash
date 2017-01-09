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

/**
 * Created by Leo on 9/1/2016.
 */
public class SpeakersArrayAdapter extends ArrayAdapter<Speakers> {

    ArrayList<Speakers> speakersList = new ArrayList<>();
    ImageView ivSpeakerPic;
    TextView tvName;

    public SpeakersArrayAdapter(Context context) {
        super(context, R.layout.layout_speaker_list);
    }

    @Override
    public void add(Speakers object) {
        speakersList.add(object);
    }

    @Override
    public Speakers getItem(int position) {
        return speakersList.get(position);
    }

    @Override
    public int getCount() {
        return speakersList.size();
    }

    @Override
    public void clear() {
        speakersList.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v==null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.layout_speaker_list, parent,false);
        }

        Speakers speakerobj = getItem(position);
        tvName = (TextView)v.findViewById(R.id.tvSpeaker_List_Name);

        ivSpeakerPic = (ImageView)v.findViewById(R.id.ivSpeakers_List_Picture);
        if (speakerobj.getPic() != 0){
            ivSpeakerPic.setImageResource(speakerobj.getPic());
            ivSpeakerPic.setBackgroundColor(00000000);
        }
        tvName.setText("" + speakerobj.getName());

        return v;
    }
}
