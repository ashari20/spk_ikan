package com.example.rika.spk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class admin extends AppCompatActivity implements View.OnClickListener {

    //defining views
    private EditText Textusername;
    private EditText Textpassword;
    private AppCompatButton buttonlogin;

    ActionBar actionBar;
    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.logo_ikan);

        //initializing views
        Textusername = (EditText) findViewById(R.id.Textusername);
        Textpassword = (EditText) findViewById(R.id.Textpassword);

        buttonlogin = (AppCompatButton) findViewById(R.id.buttonlogin);

        //adding click listener
        buttonlogin.setOnClickListener(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

@Override
    protected void onResume(){
        super.onResume();
        //in onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //fetching the boolean value from sharedpreference
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);

        //if we will get true
        if(loggedIn){
            Intent intent = new Intent(admin.this, menu_admin.class);
            startActivity(intent);
        }
    }
    private void login(){
        //getting values from edit text
        final String username = Textusername.getText().toString().trim();
        final String password = Textpassword.getText().toString().trim();

        //creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.LOGIN_URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        //if we are getting success from server
                        if(response.equalsIgnoreCase(config.LOGIN_SUCCESS)){
                            //creating a shared preference
                            SharedPreferences sharedPreferences = admin.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //creating editor to store value to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //adding values to editor
                            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(config.EMAIL_SHARED_PREF, username);

                            //saving values to editor
                            editor.commit();

                            //Starting profile activity

                            Intent intent = new Intent();
                            intent.putExtra("username", username);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else{
                            //if the server response is not success
                            //displaying an error message on toast
                            Toast.makeText(admin.this, "Username atau Password Salah", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        //you can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                //adding parameters to request
                params.put(config.KEY_EMAIL, username);
                params.put(config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v){
        //memanggil fungsi login
        login();
    }
}
