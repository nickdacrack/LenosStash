package org.rotaract9210.d9210events;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.rotaract9210.d9210events.SharedClasses.ImageItem;
import org.rotaract9210.d9210events.SharedClasses.SharedValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class GalleryActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 22;
    Uri cameraUri;

    private String Camerapath ;
    private PagerAdapter myPagerAdapter;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("hello everyone");
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        eventName = getIntent().getStringExtra("event");
        SharedValues.eventNAme = eventName;
        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);*/

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createImageFile()));
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't load photo"+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        initialisePaging();
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    if (resultCode == RESULT_OK) {
                        initialisePaging();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File extStore = Environment.getExternalStorageDirectory();
        File storageDir = new File(extStore.getAbsolutePath() + "/d9210/"+eventName+"/gallery");
        storageDir.mkdirs();
        //Toast.makeText(getApplicationContext(),storageDir.getPath(),Toast.LENGTH_LONG).show();
        /*File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );*/
        File image = new File(storageDir,imageFileName+".jpg");
        image.createNewFile();
        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void initialisePaging(){
        Bundle bundle = new Bundle();

        GalleryPicsFragment gpf = new GalleryPicsFragment();
        gpf.setArguments(bundle);
        GoogleDrivePicsFragment gdpf = new GoogleDrivePicsFragment();
        gdpf.setArguments(bundle);

        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, gpf.getClass().getName()));
        fragments.add(Fragment.instantiate(this, gdpf.getClass().getName()));
        myPagerAdapter = new AppsPagerAdapter(getSupportFragmentManager(),fragments);

        final ViewPager pager = (ViewPager) findViewById(R.id.appViewPager);
        /*pager.setClipToPadding(false);
        pager.setPageMargin(12);*/
        pager.setAdapter(myPagerAdapter);

    }

    class AppsPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        /*@Override
        public float getPageWidth(int position) {
            return 0.93f;
        }*/

        public AppsPagerAdapter (FragmentManager fm,List<Fragment> fragments){
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, 1, object);
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Local";
                case 1:
                    return "Cloud";
                default:
                    return super.getPageTitle(position);
            }
        }
    }
}
