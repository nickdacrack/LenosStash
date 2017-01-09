package org.rotaract9210.d9210events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeakerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvName,tvProfession,tvBioBrief,tvTopic,tvTopicLabel;
    private ImageView ivSpeakerPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        Intent intent = getIntent();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvName = (TextView) findViewById(R.id.tvSpeaker_Name);
        tvProfession = (TextView)findViewById(R.id.tvSpeaker_Profession);
        tvBioBrief = (TextView)findViewById(R.id.tvSpeaker_Brief_Bio);
        tvTopic = (TextView)findViewById(R.id.tvSpeaker_Topic);
        tvTopicLabel = (TextView)findViewById(R.id.tvSpeaker_Topic_Label);
        ivSpeakerPic = (ImageView)findViewById(R.id.ivSpeaker_Pic);

        tvName.setText(""+intent.getStringExtra("name"));
        tvProfession.setText(""+intent.getStringExtra("profession"));
        tvBioBrief.setText(""+intent.getStringExtra("bio_brief"));

        if (!intent.getStringExtra("topic").equals(""))
            tvTopic.setText("" + intent.getStringExtra("topic"));
        else {
            tvTopic.setVisibility(View.GONE);
            tvTopicLabel.setVisibility(View.GONE);
        }

        if (intent.getIntExtra("pic",0) != 0){
            ivSpeakerPic.setImageResource(intent.getIntExtra("pic",0));
            ivSpeakerPic.setBackgroundColor(00000000);
        }


    }
}
