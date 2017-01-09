package org.rotaract9210.d9210events;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.rotaract9210.d9210events.SharedClasses.SharedValues;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // create a File object for the parent directory
        File extStore = Environment.getExternalStorageDirectory();
        SharedValues.appFile = new File(extStore.getAbsolutePath() + "/d9210/");
        SharedValues.appFile.mkdirs();

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
    //Disposes the Activity when the onPause() method is called
    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
