package org.rotaract9210.d9210events;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.rotaract9210.d9210events.SharedClasses.SharedValues;
import org.rotaract9210.d9210events.SharedClasses.Utility;

/**
 * Created by Leo on 8/18/2016.
 */
public class RegisterActivity extends AppCompatActivity {
    RequestParams requestParams = new RequestParams();
    ProgressDialog progressDialog;
    EditText etName, etPhoneNumber, etAddress, etEmail, etPassword, etRePassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //This is the activity toolbar to the one we designed
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        etName = (EditText)findViewById(R.id.etRegister_Name);
        etPhoneNumber = (EditText)findViewById(R.id.etRegister_Phone_Number);
        etAddress = (EditText)findViewById(R.id.etRegister_Address);
        etEmail = (EditText)findViewById(R.id.etRegister_Email);
        etPassword = (EditText)findViewById(R.id.etRegister_Password);
        etRePassword = (EditText)findViewById(R.id.etRegister_ReEnter_Password);
        btnRegister = (Button)findViewById(R.id.btnRegister_Register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,phoneNumber,address,email,password,reEnterPassword;
                name = etName.getText().toString().trim();
                phoneNumber = etPhoneNumber.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString();
                reEnterPassword = etRePassword.getText().toString();

                if(name.equals("")||phoneNumber.equals("")||address.equals("")||email.equals("")||password.equals("")||reEnterPassword.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please fill in all parameters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Utility.validate(email)){
                    Toast.makeText(RegisterActivity.this,"Please enter a valid Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(reEnterPassword)){
                    Toast.makeText(RegisterActivity.this,"Passwords do not match. Please please reEnter passwords",Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etRePassword.setText("");
                    return;
                }
                newRegister();
            }
        });

    }

    public void newRegister(){

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                return null;
            }

            @Override
            protected void onPreExecute() {

                requestParams.put("serverTask","registerUser");
                requestParams.put("name", Base64.encodeToString(etName.getText().toString().getBytes(), 0));
                requestParams.put("phoneNumber",Base64.encodeToString(etPhoneNumber.getText().toString().getBytes(),0));
                requestParams.put("address", Base64.encodeToString(etAddress.getText().toString().getBytes(),0));
                requestParams.put("email",Base64.encodeToString(etEmail.getText().toString().getBytes(),0));
                requestParams.put("password",Base64.encodeToString(etPassword.getText().toString().getBytes(),0));

                progressDialog.setMessage("Registering...");
                progressDialog.show();

            }

            @Override
            protected void onPostExecute(String s) {
                makeHttpCall();
            }
        }.execute(null,null,null);
    }

    public void makeHttpCall(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(SharedValues.serverAddress,requestParams, new AsyncHttpResponseHandler(){

            // When the response returned by REST has Http
            // response code '200'
            @Override
            public void onSuccess(String response) {
                progressDialog.hide();
                switch (response){
                    case "true":
                        Toast.makeText(RegisterActivity.this,"Registration was successful.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        break;
/*                    case "0":
                        Toast.makeText(RegisterActivity.this,"Registration was failed. Email already Registered.",Toast.LENGTH_SHORT).show();
                        break;*/
                    default:
                        Toast.makeText(RegisterActivity.this,"Something went wrong : "+ response,Toast.LENGTH_SHORT).show();
                }

            }

            // When the response returned by REST has Http
            // response code other than '200' such as '404',
            // '500' or '403' etc
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                progressDialog.hide();
                Toast.makeText(RegisterActivity.this,"Error in connection : "+statusCode,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
