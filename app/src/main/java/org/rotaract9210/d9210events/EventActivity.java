package org.rotaract9210.d9210events;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.rotaract9210.d9210events.SharedClasses.About;
import org.rotaract9210.d9210events.SharedClasses.SharedValues;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class EventActivity extends AppCompatActivity {

    private Button btnSpeakers;
    private Button btnRegister;
    private Button btnProgram;
    private ImageButton ibTwitter;
    private Button btnSponsors;
    private Button btnAbout;
    private Button btnGallary;

    private TextView tvName,tvVenue,tvDate;
    private ImageView ivEventPic;
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;

    int[] resources = {
            R.mipmap.magolis_lodge_1,
            R.mipmap.magolis_lodge_2,
            R.mipmap.magolis_lodge_3,
    };

    String tweet;
    String eventName;
    String verify;

    RequestParams loginParameters = new RequestParams();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eventName = getIntent().getStringExtra("event");

        btnSpeakers = (Button)findViewById(R.id.btnEvent_Speakers);
        tvName = (TextView)findViewById(R.id.tvEvent_Name);
        tvVenue = (TextView)findViewById(R.id.tvEvent_Venue);
        tvDate = (TextView)findViewById(R.id.tvEvent_Date);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);



        // Get the ViewFlipper
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        // Add all the images to the ViewFlipper
        nSetUpPage();

        //Identifies the moment the user interacts with the ViewSwitcher
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(this, customGestureDetector);

        // Set in/out flipping animations
        mViewFlipper.setInAnimation(this, R.anim.right_in);
        mViewFlipper.setOutAnimation(this, R.anim.left_out);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000); // flip every 4 seconds (4000ms)

        switch (getIntent().getStringExtra("event")){
            case "ras":

                tvName.setText("RAS 2016");
                tvVenue.setText("STEPHEN MARGOLIS RESORT");
                tvDate.setText("29 September - 2 October");
                btnSpeakers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),MenuActivity.class)
                                .putExtra("menu","speakers"));
                    }
                });
                break;
            case "discon":
                tvName.setText("DISCON");
                tvVenue.setText("VICTORIA FALLS");
                tvDate.setText("");
                //ivEventPic.setImageBitmap();
                break;
            case "ryla":
                tvName.setText("RYLA");
                tvVenue.setText("");
                tvDate.setText("");
                //ivEventPic.setImageBitmap();
                break;
            case "inductions":
                tvName.setText("INDUCTIONS");
                tvVenue.setText("ALL OVER THE DISTRICT");
                tvDate.setText("");
                //ivEventPic.setImageBitmap();
                break;
            case "social":
                tvName.setText("SOCIAL EVENTS");
                tvVenue.setText("LOL");
                tvDate.setText("");
                //ivEventPic.setImageBitmap();
                break;
            default:
        }

        btnRegister = (Button)findViewById(R.id.btnEvent_Register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(),RegisterEventActivity.class);
                registerIntent.putExtra("registration","http://ras.rotaract9210.org/index.php/conference-registration-options/");
                startActivity(registerIntent);
            }
        });
        btnProgram = (Button)findViewById(R.id.btnEvent_Program);
        btnProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openProgramPDf();
                startActivity(new Intent(getApplicationContext(),MenuActivity.class)
                        .putExtra("menu","program")
                        .putExtra("event",eventName));
            }
        });
        ibTwitter = (ImageButton)findViewById(R.id.btnEvent_Twitter);
        ibTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TwitterActivity.class)
                        .putExtra("tweet",tweet));
            }
        });
        btnSponsors = (Button)findViewById(R.id.btnEvent_Sponsors);
        btnSponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MenuActivity.class)
                        .putExtra("menu", "sponsors"));
            }
        });
        btnAbout = (Button)findViewById(R.id.btnEvent_About);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                extras.putParcelable("picture",);
//                extras.putParcelable("eventName", "the event");
//                extras.putParcelable("eventDescription", "the best app");

                startActivity(new Intent(getApplicationContext(), MenuActivity.class)
                        .putExtra("menu","about"));
            }
        });
        btnGallary = (Button)findViewById(R.id.btnEvent_Gallery);
        btnGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GalleryActivity.class)
                        .putExtra("event",eventName));
            }
        });

    }

    public void openProgramPDf(){
        // create a File object for the parent directory
        File extStore = Environment.getExternalStorageDirectory();
        File IPDirectory = new File(extStore.getAbsolutePath() + "/d9210/"+eventName+"/event/");
        IPDirectory.mkdirs();
        File f = new File(IPDirectory,"Rotaract-Africa-Summit-2016-Programme.pdf");
        try {

            InputStream is = getAssets().open("Rotaract-Africa-Summit-2016-Programme.pdf");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();


            FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) { throw new RuntimeException(e); }


                /*Intent intent = new Intent(getApplicationContext(), ProgramActivity.class);
                intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, getCacheDir()+"/m1.pdf");
                startActivity(intent);*/

        File file = new File(IPDirectory,"Rotaract-Africa-Summit-2016-Programme.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                mViewFlipper.setInAnimation(getApplicationContext(), R.anim.left_in);
                mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.left_out);

                mViewFlipper.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                mViewFlipper.setInAnimation(getApplicationContext(), R.anim.right_in);
                mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.right_out);

                mViewFlipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

    }

    private void nSetUpPage(){

        /*ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //change back to != null
        if (networkInfo == null && networkInfo.isConnected()) {
            try {
                String pause = new AsyncTask<Void,Void,String>(){
                    @Override
                    protected void onPreExecute() {
                        progressDialog.setMessage("Connecting...");
                        progressDialog.show();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        progressDialog.hide();
                    }

                    @Override
                    protected String doInBackground(Void... params) {
                        for (int i = 0; i<3;i++){
                            getPic(String.format("%d",i));
                        }
                        return null;
                    }
                }.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            while (verify == null){}

        } else {
            //Toast.makeText(getApplicationContext(),"No internet access",Toast.LENGTH_LONG).show();
            if (!(new File(SharedValues.appFile+eventName+"slider").exists())){
                for (int i = 0; i < resources.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(resources[i]);
                    mViewFlipper.addView(imageView);
                }
            }else {
                for (int i = 0; i < resources.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(resources[i]);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(new File(SharedValues.appFile+eventName+i).getPath()));
                    mViewFlipper.addView(imageView);
                }
            }
        }*/

        if (!(new File(SharedValues.appFile + eventName + "slider").exists())) {
            for (int i = 0; i < resources.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(resources[i]);
                mViewFlipper.addView(imageView);
            }
        } else {
            for (int i = 0; i < resources.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(resources[i]);
                imageView.setImageBitmap(BitmapFactory.decodeFile(new File(SharedValues.appFile + eventName + i).getPath()));
                mViewFlipper.addView(imageView);
            }
        }
    }

    public void getPic(String pic){

        loginParameters.put("serverTask", "getVenuePics");
        loginParameters.put("eventName", Base64.encodeToString(eventName.getBytes(), 0));
        loginParameters.put("download", Base64.encodeToString(pic.getBytes(), 0));
        //progressDialog.setMessage("Connecting...");

        makeHttpCall(eventName,pic);

    }

    public void makeHttpCall(final String event, final String file){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.ras.rotaract91210.org/d9210_backend.php", loginParameters, new AsyncHttpResponseHandler() {

            // When the response returned by REST has Http
            // response code '200'
            @Override
            public void onSuccess(String response) {
                verify = Base64.decode(response.getBytes(), Base64.DEFAULT).toString();
                writeToStorage(event, file, response);
            }

            // When the response returned by REST has Http
            // response code other than '200' such as '404',
            // '500' or '403' etc
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Toast.makeText(EventActivity.this, content, Toast.LENGTH_LONG).show();
            }
        });
        //progressDialog.hide();
    }

    public void writeToStorage(String folder,String file,String contents)  {
        // create a File object for the parent directory
        //File extStore = Environment.getExternalStorageDirectory();
        //File IPDirectory = new File(extStore.getAbsolutePath() + "/d9210/"+folder+"/");
        File IPDirectory = new File(SharedValues.appFile+folder+"/");
        // have the object build the directory structure, if needed.
        IPDirectory.mkdirs();

        // create a File object for the output file
        File basicFile;

        // now attach the OutputStream to the file object, instead of a String representation
        FileWriter fwBasic;
        BufferedWriter bwBasic = null;

        try {
            basicFile = new File(IPDirectory,file+".png");
            fwBasic = new FileWriter(basicFile);
            bwBasic = new BufferedWriter(fwBasic);
            bwBasic.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bwBasic.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
