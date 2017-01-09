package org.rotaract9210.d9210events;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.rotaract9210.d9210events.SharedClasses.Utility;

public class ContactUsActivity extends AppCompatActivity {

    EditText etName, etMessage, etEmail;

    Button btnSend;
    ProgressDialog progressDialog;
    RequestParams sendParameters = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sending...");

        etName = (EditText)findViewById(R.id.etContact_Name);
        etEmail = (EditText)findViewById(R.id.etContact_Email);
        etMessage = (EditText)findViewById(R.id.etContact_Message);

        btnSend = (Button)findViewById(R.id.btnContact_Send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String message = etMessage.getText().toString();
                if (name.equals("")||message.equals("")||email.equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill in all parameters",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Utility.validate(email)){
                    sendParameters.put("name",name);
                    sendParameters.put("email",email);
                    sendParameters.put("message",message);
                    sendParameters.put("serverTask","contactUs");

                    nSend();
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    public void nSend(){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPreExecute() {
                progressDialog.show();

            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.hide();
            }

            @Override
            protected String doInBackground(Void... params) {
                return makeHttpCall();
            }
        }.execute();
    }

    String result = null;
    public String makeHttpCall(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.ras.rotaract91210.org/d9210_backend.php", sendParameters, new AsyncHttpResponseHandler() {

            // When the response returned by REST has Http
            // response code '200'
            @Override
            public void onSuccess(String response) {
                result = response;
                Toast.makeText(getApplicationContext(),"Message send",Toast.LENGTH_SHORT).show();
            }

            // When the response returned by REST has Http
            // response code other than '200' such as '404',
            // '500' or '403' etc
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Toast.makeText(getApplicationContext(), "An error has occured: " + content, Toast.LENGTH_LONG).show();
            }
        });
        return result;
    }
}
