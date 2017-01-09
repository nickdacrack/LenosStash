package org.rotaract9210.d9210events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

public class ProgramActivity extends AppCompatActivity{
    TextView tvDay,tvProgram;

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
        tvProgram.setText(extras.getStringExtra("program"));

    }
}
