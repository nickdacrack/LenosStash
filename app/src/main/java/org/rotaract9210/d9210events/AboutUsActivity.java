package org.rotaract9210.d9210events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {

    ListView list;
    ArrayList<String> aboutPeople = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        populateList();
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,aboutPeople);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getApplicationContext(),AboutDistrictActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(),AboutDGActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(),AboutDRRActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(),MenuActivity.class).putExtra("menu","clubs"));
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(),MenuActivity.class).putExtra("menu","committees"));
                        break;
                }
            }
        });
    }

    private void populateList(){
        aboutPeople.add("District 9210");
        aboutPeople.add("DG");
        aboutPeople.add("DRR");
        //aboutPeople.add("Committees");
        aboutPeople.add("Clubs");
    }
}
