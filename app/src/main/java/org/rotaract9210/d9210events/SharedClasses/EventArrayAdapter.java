package org.rotaract9210.d9210events.SharedClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.rotaract9210.d9210events.R;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Leo on 8/16/2016.
 */
public class EventArrayAdapter extends ArrayAdapter<EventMessage> {
    ArrayList<EventMessage> eventMessageList = new ArrayList<>();
    private TextView tvSummary, tvDay, tvDate, tvMonth;

    public EventArrayAdapter(Context context, ArrayList<EventMessage> eventMessageList) {
        super(context, R.layout.layout_event, eventMessageList);
        this.eventMessageList = eventMessageList;
    }

    @Override
    public void add(EventMessage object) {
        super.add(object);
        eventMessageList.add(object);
    }

    @Override
    public int getCount() {
        return eventMessageList.size();
    }

    @Override
    public EventMessage getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.layout_event, parent,false);
        }

        EventMessage messageobj = getItem(position);

        tvSummary = (TextView) v.findViewById(R.id.tvEvents_Heading);
        tvDate = (TextView)v.findViewById(R.id.tvEvents_Date);
        tvDay = (TextView) v.findViewById(R.id.tvEvents_Day);
        tvMonth = (TextView) v.findViewById(R.id.tvEvents_Month);

        tvSummary.setText("" + messageobj.getTitle());
        //tvDate.setText("" + messageobj.getDay());
        String[] daysOfTheWeek = DateFormatSymbols.getInstance().getShortWeekdays();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = format.parse(messageobj.getBody());
            tvDay.setText("" + DateFormatSymbols.getInstance().getShortWeekdays()[d.getDay()]);
            tvDate.setText("" + d.getDate());
            tvMonth.setText("" + DateFormatSymbols.getInstance().getShortMonths()[d.getMonth()]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return v;
    }
}
