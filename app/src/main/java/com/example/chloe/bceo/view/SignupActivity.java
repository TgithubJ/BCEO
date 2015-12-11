package com.example.chloe.bceo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;

public class SignupActivity extends Activity {

    private Button signup;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText phoneEditText;

    private HTTPGet httpUtil;

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

        if (!isEmailValid(user_email)) {
            Toast.makeText(this.getApplicationContext(),
                    "Invalid email format, try again", Toast.LENGTH_SHORT).show();
            return;
        } else if (!isPasswordValid(user_password)) {
            Toast.makeText(this.getApplicationContext(),
                    "password too short, at least 5 letters!!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!isPhoneValid(user_phone)) {
            Toast.makeText(this.getApplicationContext(),
                    "phone number cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ubuntu remote server with signup GET request
        StringBuilder tail =
                new StringBuilder("/signup?");
        tail.append("email=" + user_email);
        tail.append("&pw=" + user_password);
        tail.append("&phone=" + user_phone);

        String urlStr = httpUtil.buildURL(tail.toString());
        String response = httpUtil.getResponse(urlStr);

        if( response.equals("s")) {
            Intent intent = new Intent(this, GroupsActivity.class);
            User u = new User(0, user_email.split("@")[0] ,user_email, user_password, 0, user_phone);
            intent.putExtra("user", u);
            this.startActivity(intent);
        } else if (response.toString().equals("f")){
            Toast.makeText(this.getApplicationContext(),
                    "user with same email already exist, try again", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getApplicationContext(),
                    "sign up failed, try again", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isPhoneValid(String password) {
        return password.length() > 0;
    }

}
