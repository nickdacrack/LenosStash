package org.rotaract9210.d9210events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.rotaract9210.d9210events.SharedClasses.DBHelper;
import org.rotaract9210.d9210events.SharedClasses.EventArrayAdapter;
import org.rotaract9210.d9210events.SharedClasses.EventMessage;
import org.rotaract9210.d9210events.SharedClasses.Speakers;
import org.rotaract9210.d9210events.SharedClasses.SpeakersArrayAdapter;
import org.rotaract9210.d9210events.SharedClasses.Club;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Leo on 9/2/2016.
 */
public class MenuActivity extends AppCompatActivity {

    SpeakersArrayAdapter adapter;
    ListView listView;
    Toolbar toolbar;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("Menu");
        toolbar.setSubtitleTextColor(000000);

        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        listView = (ListView)findViewById(R.id.list);
        listView.setDividerHeight(0);

        LinearLayout llayout = (LinearLayout)findViewById(R.id.llayout);

        switch (getIntent().getStringExtra("menu")){
            case "speakers":
                toolbar.setSubtitle("Speakers");
                populateSpeakers();
                break;
            case "program":
                toolbar.setSubtitle("Program");
                frameLayout.setVisibility(View.VISIBLE);
                populateProgram();
                break;
            case "social":
                toolbar.setSubtitle("Social");
                populateSocial();
                break;
            case "projects":
                toolbar.setSubtitle("Projects");
                populateProjects();
                break;
            case "committees":
                toolbar.setSubtitle("Committees");
                populateCommittees();
                break;
            case "clubs":
                toolbar.setSubtitle("Clubs");
                populateClubs();
                break;
            case "sponsors":
                toolbar.setSubtitle("Sponsors");
                llayout.setBackgroundColor(50085152);
                populateSponsors();
                break;
            case "about":
                toolbar.setSubtitle("About");
                populateAbout();
                break;
            default:
        }

    }

    public void populateProgram(){

        DBHelper helper = new DBHelper(MenuActivity.this);
        String[] program;

        listView.setDivider(ContextCompat.getDrawable(this, R.drawable.divider_grey));
        program = getResources().getStringArray(R.array.program);
        final ArrayList<EventMessage> programList = new ArrayList<>();
        Iterator iterator = helper.getDaysProgram(getIntent().getStringExtra("event")).iterator();
        while (iterator.hasNext()) {
            programList.add((EventMessage) iterator.next());
        }
        /*int i=0;
        for (String day : program){
            programList.add(new EventMessage("Program",day,"Day "+ ++i));
        }*/

        //programList = helper.getDaysProgram(getIntent().getStringExtra("event"))
        EventArrayAdapter programAdapter = new EventArrayAdapter(getApplicationContext(),programList);
        listView.setAdapter(programAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),ProgramActivity.class)
                        .putExtra("day",programList.get(position).getDay())
                        .putExtra("program",programList.get(position).getBody())
                        .putExtra("event",getIntent().getStringExtra("event")));
            }
        });
    }
    public void populateAbout(){
        adapter = new SpeakersArrayAdapter(this);
        adapter.add(new Speakers("Rotary International President's Message", "John F. Germ", getResources().getString(R.string.john_germ), "", "",R.mipmap.john_germ));
        adapter.add(new Speakers("Convenor's Message", "Allistar Volo", getResources().getString(R.string.a_Volo), "", "",R.mipmap.allistar_volo));
        adapter.add(new Speakers("District Governor's Message", "Lee-Ann Shearing", getResources().getString(R.string.dg_message), "", "",R.mipmap.lee_ann_shearing));
        adapter.add(new Speakers("District Rotaract Representative's Message", "Martin Mavesera", getResources().getString(R.string.drr_message), "", "",R.mipmap.drr_pic));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (position == 0){
                    // create a File object for the parent directory
                    File extStore = Environment.getExternalStorageDirectory();
                    File IPDirectory = new File(extStore.getAbsolutePath() + "/d9210/ras/about/");
                    IPDirectory.mkdirs();
                    File f = new File(IPDirectory,"D9210_RACAfricaSummit_Presidential_Letter.pdf");
                    try {

                        InputStream is = getAssets().open("D9210_RACAfricaSummit_Presidential_Letter.pdf");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();


                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(buffer);
                        fos.close();
                    } catch (Exception e) { throw new RuntimeException(e); }

                    File file = new File(IPDirectory,"D9210_RACAfricaSummit_Presidential_Letter.pdf");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } else{*/
                    Speakers theSpeaker = adapter.getItem(position);
                    Intent speakerIntent = new Intent(getApplicationContext(), AboutActivity.class);
                    speakerIntent.putExtra("name", theSpeaker.getProfession())
                            .putExtra("profession", theSpeaker.getName())
                            .putExtra("bio_brief", theSpeaker.getBioBrief())
                            .putExtra("pic", theSpeaker.getPic());

                    startActivity(speakerIntent);
            //    }
            }
        });
    }
    public void populateClubs(){
        String rt = "Rotaract Club of ";
        ArrayList<Club> clublist = new ArrayList<>();

        //Malawi
        clublist.add(new Club(rt+"Blantyre","Malawi","Blantyre","valentinemaguru@gmail.com"));
        clublist.add(new Club(rt+"Lilongwe","Malawi","Lilongwe","isabel@ghcorps.org"));
        clublist.add(new Club(rt+"Limbe","Malawi","Blantyre","jamesangombe@gmail.com"));
        clublist.add(new Club(rt+"NRC(Natural Resources College)","Malawi","Lilongwe","lonjeclever@gmail.com"));

        //Northern Mozambique
        clublist.add(new Club(rt+"Beira","Northern Mozambique","Beira","chaual.araufo@gmail.com"));

        //Zambia

        clublist.add(new Club(rt+"Catholic University","Zambia","Copper Belt","tmalwita@gmail.com"));
        clublist.add(new Club(rt+"Lusaka","Zambia","Lusaka","emeraudesidibe@gmail.com"));
        clublist.add(new Club(rt+"Ndola","Zambia","Copper Belt","rotaraclubofndola@yahoo.co.uk"));
        clublist.add(new Club(rt+"Nkana","Zambia","Copper Belt","nandwemumba@gmail.com"));
        clublist.add(new Club(rt+"Tiyende Pamodzi","Zambia","Lusaka","margaret.mumba@yahoo.com"));
        clublist.add(new Club(rt+"UNZA (University of Zambia)","Zambia","Lusaka","sincmansaya@gmail.com"));

        //Zimbabwe

        clublist.add(new Club(rt+"Avondale","Zimbabwe","Harare","nyamadzawocollins@gmail.com"));
        clublist.add(new Club(rt+"Belmont","Zimbabwe","Bulawayo","michellenyathi@yahoo.com"));
        clublist.add(new Club(rt+"Bindura University","Zimbabwe","Bindura","gaylordrgundani@gmail.com"));
        clublist.add(new Club(rt+"Borrowdale Brooke","Zimbabwe","Harare","brian.mataruka@gmail.com"));
        clublist.add(new Club(rt+"Bulawayo","Zimbabwe","Bulawayo","sehlulendlovu@yahoo.com"));
        clublist.add(new Club(rt+"Catholic University","Zimbabwe","Harare","zimotors1@gmail.com"));
        clublist.add(new Club(rt+"Chinhoyi University","Zimbabwe","Chinhoyi","nomarahh@gmail.com"));
        clublist.add(new Club(rt+"Great Zimbabwe University","Zimbabwe","Masvingo","rodneygoldman8@gmail.com"));
        clublist.add(new Club(rt+"Gweru","Zimbabwe","Gweru","altonp@gmail.com"));
        clublist.add(new Club(rt+"Harare Central","Zimbabwe","Harare","michaelmartinc@gmail.com"));
        clublist.add(new Club(rt+"Harare Institute of Technology","Zimbabwe","Harare","gracenyasha94@gmail.com"));
        clublist.add(new Club(rt+"Harare Polytechnic","Zimbabwe","Harare","lerole91@gmail.com"));
        clublist.add(new Club(rt+"Harare West","Zimbabwe","Harare","mangwanast@gmail.com"));
        clublist.add(new Club(rt+"Highlands","Zimbabwe","Harare","ts.mutimutema@yahoo.com"));
        clublist.add(new Club(rt+"Hunyani","Zimbabwe","Harare","takudzwa.matinenga@gmail.com"));
        clublist.add(new Club(rt+"Matopos","Zimbabwe","Bulawayo","f.f.makwarimba@gmail.com"));
        clublist.add(new Club(rt+"Midlands State University","Zimbabwe","Gweru","samaz@live.co.uk"));
        clublist.add(new Club(rt+"Nust","Zimbabwe","Bulawayo","tsomondomufudzi@gmail.com"));
        clublist.add(new Club(rt+"School Of Mines","Zimbabwe","Bulawayo","halakemm.mase@gmail.com"));
        clublist.add(new Club(rt+"University of Zimbabwe College of Health Science","Zimbabwe","Harare","kudahchikuwadzo@gmail.com"));
        clublist.add(new Club(rt+"University of Zimbabwe","Zimbabwe","Harare","chipara.takunda@gmail.com"));

        myAdapter adapter1 = new myAdapter(getApplicationContext(),clublist);
        listView.setAdapter(adapter1);
        listView.setDividerHeight(0);
    }

    public void populateSponsors(){
        adapter = new SpeakersArrayAdapter(this){

            //ArrayList<Speakers> speakersList = new ArrayList<>();
            ImageView ivSpeakerPic;
            TextView tvName;

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
                    ivSpeakerPic.setBackgroundColor(getResources().getColor(R.color.faint_blue));
                }
                tvName.setText("" + speakerobj.getName());

                return v;
            }
        };
        adapter.add(new Speakers("Stephen Margolis Resort", "", "", "", "",R.mipmap.smr_logo));
        adapter.add(new Speakers("Rotary ", "", "", "", "",R.mipmap.rotary_logo));
        adapter.add(new Speakers("District 9210", "", "", "", "",R.mipmap.d9210_logo));
        adapter.add(new Speakers("Rotaract", "", "", "", "",R.mipmap.rotaract_logo));

        listView.setAdapter(adapter);
    }
    public void populateCommittees(){
        adapter = new SpeakersArrayAdapter(this);
        adapter.add(new Speakers("District Treasurer", "Milton Murongazvombo", "", "", ""));
        adapter.add(new Speakers("District Secretary", "Hilary Jowah", "", "", ""));
        adapter.add(new Speakers("District Public Relations", "Someone", "", "", ""));

        listView.setAdapter(adapter);
    }
    public void populateSpeakers(){
        adapter = new SpeakersArrayAdapter(this);
        adapter.add(new Speakers("Adrian Hayes"
                , getString(R.string.hayesProf)
                , getString(R.string.hayesBio)
                , ""
                , ""
                ,R.mipmap.adrian_hayes ));
        adapter.add(new Speakers("Barbra Mzembi"
                , getString(R.string.muzembiProf)
                , getString(R.string.muzembiBio)
                , ""
                , ""
                ,R.mipmap.barbra_zembi ));
        adapter.add(new Speakers("Chamu Chiwanza"
                , ""
                , ""
                , ""
                , ""
                , R.mipmap.dr_chamu_chiwanza));
        adapter.add(new Speakers("Gupta Kumar"
                , getString(R.string.guptaProf)
                , getString(R.string.guptaBio)
                , ""
                , ""
                ,R.mipmap.gupta_kumar ));
        adapter.add(new Speakers("Jonah Mungoshi"
                , getResources().getString(R.string.MungoshiProf)
                , getResources().getString(R.string.MungoshiBio)
                , ""
                , ""
                , R.mipmap.mungoshi));

        adapter.add(new Speakers("Gabriel Chipara"
                , getResources().getString(R.string.ChiparaProf)
                , getResources().getString(R.string.ChiparaBio)
                , ""
                , ""
                , R.mipmap.gabriel_chipara));

        adapter.add(new Speakers("Linda Lisa Longwe"
                , getString(R.string.LongweProf)
                , getString(R.string.LongweBio)
                , ""
                , ""
                , R.mipmap.linda_longwe));
        adapter.add(new Speakers(getString(R.string.Mavesera)
                , getString(R.string.LongweProf)
                , getString(R.string.MaveseraBio)
                , ""
                , ""
                , R.mipmap.mavesera));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Speakers theSpeaker = adapter.getItem(position);
                Intent speakerIntent = new Intent(getApplicationContext(), SpeakerActivity.class);
                speakerIntent.putExtra("name", theSpeaker.getName())
                        .putExtra("profession", theSpeaker.getProfession())
                        .putExtra("bio_brief", theSpeaker.getBioBrief())
                        .putExtra("topic", theSpeaker.getTopic())
                        .putExtra("pic", theSpeaker.getPic());
                startActivity(speakerIntent);
            }
        });
    }

    public void populateSocial(){
        //create a design with a picture and description of the social event
    }

    public void populateProjects(){
        //create a design of a list item for the
        adapter = new SpeakersArrayAdapter(this){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                ArrayList<Speakers> speakersList = new ArrayList<>();
                ImageView ivSpeakerPic;
                TextView tvName;

                if (v==null){
                    LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = inflater.inflate(R.layout.layout_speaker_list, parent,false);
                }

                Speakers speakerobj = getItem(position);
                tvName = (TextView)v.findViewById(R.id.tvSpeaker_List_Name);

                ivSpeakerPic = (ImageView)v.findViewById(R.id.ivSpeakers_List_Picture);
                ivSpeakerPic.setVisibility(View.GONE);
                if (speakerobj.getPic() != 0){
                    ivSpeakerPic.setImageResource(speakerobj.getPic());
                    ivSpeakerPic.setBackgroundColor(00000000);
                }
                tvName.setText("" + speakerobj.getName());

                return v;
            }
        };
        adapter.add(new Speakers("Project 50/50"
                , ""
                , ""
                , ""
                , "" ));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = "123";
                switch (position){
                    case 0:
                        fileName = "Project 5050.pdf";
                }

                // create a File object for the parent directory
                File extStore = Environment.getExternalStorageDirectory();
                File IPDirectory = new File(extStore.getAbsolutePath() + "/d9210/ras/project/");
                IPDirectory.mkdirs();
                File f = new File(IPDirectory,fileName);
                try {

                    InputStream is = getAssets().open(fileName);
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

                File file = new File(IPDirectory,fileName);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    class myAdapter extends ArrayAdapter<Club>{
        TextView tvName,tvCountry,tvCity,tvContact;

        public myAdapter(Context context, ArrayList<Club> objects) {
            super(context, R.layout.layout_clubs, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v==null){
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v=inflater.inflate(R.layout.layout_clubs,parent,false);
            }

            Club clubObj = getItem(position);
            tvName = (TextView)v.findViewById(R.id.tvClub_Name);
            tvCity = (TextView)v.findViewById(R.id.tvClub_City);
            tvCountry = (TextView)v.findViewById(R.id.tvClub_Country);
            tvContact = (TextView)v.findViewById(R.id.tvClub_Contact);

            tvName.setText(clubObj.getName());
            tvCity.setText(clubObj.getCity());
            tvContact.setText(clubObj.getContact());
            tvCountry.setText(clubObj.getCountry());

            return v;
        }
    }
}
