package org.rotaract9210.d9210events;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.rotaract9210.d9210events.SharedClasses.GridViewAdapter;
import org.rotaract9210.d9210events.SharedClasses.ImageItem;
import org.rotaract9210.d9210events.SharedClasses.SharedValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GalleryPicsFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter adapter;
    ArrayList<ImageItem> listOfPics;
    File[] listFile;
    String eventName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gallery_pics, container, false);
        eventName = SharedValues.eventNAme;

        listOfPics = getData();
        gridView = (GridView)rootView.findViewById(R.id.gridView);
        adapter = new GridViewAdapter(rootView.getContext(),getData());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle extras = new Bundle();
                extras.putString("image",listFile[position].getPath());
                extras.putString("picFrom", "gallery");
                startActivity(new Intent(getContext(), ImageViewActivity.class).putExtras(extras));
            }
        });
        return rootView;
    }

    private ArrayList<ImageItem> getData(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();

            ArrayList<String> f = new ArrayList<String>();// list of file paths

            File file= new File(SharedValues.appFile+"/"+eventName+"/gallery/");
            file.mkdirs();

            if (file.isDirectory())
            {
                listFile = file.listFiles();
                for (int i = 0; i < listFile.length; i++)
                {
                    //imageItems.add(new ImageItem(BitmapFactory.decodeFile(listFile[i].toString()),"Image#"+i));
                    imageItems.add(new ImageItem(convertBitmap(listFile[i].getPath()),"image#"+i));
                }
            }
        //}
        return imageItems;
    }

    public static Bitmap convertBitmap(String path)   {
        Bitmap bitmap=null;
        BitmapFactory.Options ourOptions=new BitmapFactory.Options();
        ourOptions.inDither=false;
        ourOptions.inPurgeable=true;
        ourOptions.inInputShareable=true;
        ourOptions.inTempStorage=new byte[32 * 1024];
        File file=new File(path);
        FileInputStream fs=null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if(fs!=null)
            {
                bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, ourOptions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fs!=null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
