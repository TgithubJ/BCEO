package com.example.chloe.bceo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chloe.bceo.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class SignupActivity extends Activity {

    private Button signup;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = (Button) findViewById(R.id.sign_up_button);
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        phoneEditText = (EditText) findViewById(R.id.phonenumber);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signUp() {
        // to handle asynchronously in main activity
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // url for inserting into the db, remote server
        String user_email = emailEditText.getText().toString();
        String user_password = passwordEditText.getText().toString();
        String user_phone = phoneEditText.getText().toString();

        // ubuntu remote server with signup GET request
        StringBuilder urlStr =
                new StringBuilder("http://52.34.169.54:3000/signup?");
        urlStr.append("email=" + user_email);
        urlStr.append("&pw=" + user_password);
        urlStr.append("&phone=" + user_phone);

        URL url;
        HttpURLConnection urlConnection = null;

        // server response str
        StringBuilder response = new StringBuilder();
        try {
            url = new URL(urlStr.toString());
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);


            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                response.append(current);
            }
            Log.w("INFO:", response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }


        if( response.toString().equals("s")) {
            Intent intent = new Intent(this, GroupsActivity.class);
            this.startActivity(intent);
        } else if (response.toString().equals("f")){
            Toast.makeText(this.getApplicationContext(),
                    "user with same email already exist", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getApplicationContext(),
                    "sign up failed, try again", Toast.LENGTH_SHORT).show();
        }


    }
}
