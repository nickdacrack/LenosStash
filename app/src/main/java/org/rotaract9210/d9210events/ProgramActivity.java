package org.rotaract9210.d9210events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import org.rotaract9210.d9210events.SharedClasses.DBHelper;
import org.rotaract9210.d9210events.SharedClasses.ProgramItem;

import java.util.ArrayList;
import java.util.List;

public class ProgramActivity extends AppCompatActivity{
    TextView tvDay,tvProgram;
    ListView listView;
    ProgramListArrayAdapter adapter;
    ArrayList<ProgramItem> programItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent extras = getIntent();
        tvDay=(TextView)findViewById(R.id.tvProgram_Day);
        tvProgram =(TextView)findViewById(R.id.tvProgram_Program);

        tvDay.setText(extras.getStringExtra("day"));

        /** Populates the programlist with the days program */
        populateProgramList(extras.getStringExtra("event"));

        listView = (ListView)findViewById(R.id.list);
        adapter = new ProgramListArrayAdapter(ProgramActivity.this,programItems);
        listView.setFocusable(false);
        listView.setAdapter(adapter);


    }

    private void populateProgramList(String eventName){
        DBHelper helper = new DBHelper(ProgramActivity.this);
        programItems = helper.getEventProgram(eventName);

    }
    class ProgramListArrayAdapter extends ArrayAdapter<ProgramItem>{

        TextView tvTime,tvSession,tvFacillitator;

        public ProgramListArrayAdapter(Context context,  ArrayList<ProgramItem> objects) {
            super(context, R.layout.layout_program_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v==null){
                LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.layout_program_item, parent,false);
            }

            tvTime = (TextView)v.findViewById(R.id.tvProgramTime);
            tvSession = (TextView)v.findViewById(R.id.tvProgramDescription);
            tvFacillitator = (TextView)v.findViewById(R.id.tvProgramFacilitator);

            ProgramItem item = getItem(position);
            tvTime.setText(item.time);
            tvSession.setText(item.session);
            tvFacillitator.setText(item.facilitator);

            return  v;
        }
    }
}
