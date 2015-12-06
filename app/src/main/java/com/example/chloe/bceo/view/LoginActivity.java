package com.example.chloe.bceo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.chloe.bceo.DBLayout.DatabaseConnector;
import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private Button signup;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        signup = (Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void SignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            requestLogin(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public void requestLogin(String email, String pw) {
        // to handle asynchronously in main activity
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // url for inserting into the db, remote server
        String user_email = mEmailView.getText().toString();
        String user_password = mPasswordView.getText().toString();

        // ubuntu remote server with signup GET request
        StringBuilder urlStr =
                new StringBuilder("http://52.34.169.54:3000/login?");
        urlStr.append("email=" + user_email);
        urlStr.append("&pw=" + user_password);

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


        if( response.toString().equals("not exist")) {
            Toast.makeText(this.getApplicationContext(),
                    "Email doesn't exist", Toast.LENGTH_SHORT).show();

        } else if (response.toString().equals("wrong password")){
            Toast.makeText(this.getApplicationContext(),
                    "Wrong password", Toast.LENGTH_SHORT).show();
        } else if (response.toString().substring(0,6).equals("{\"id\":")) {
            try  {
                JSONObject job = new JSONObject(response.toString());
                int login_id = job.getInt("id");
                String login_email = job.getString("email");
                String login_password = job.getString("password");
                String login_phone = job.getString("phone");
                int login_groupId = job.getInt("group_id");

                User u = new User(
                        login_id, login_email, login_password, login_groupId, login_phone);

                // Success loging in, so go to Group page
                Intent intent = new Intent(this, GroupsActivity.class);
                //passing successfully logged in user
                intent.putExtra("user", u);
                this.startActivity(intent);

            } catch (JSONException e) {
                Log.w("ERROR:", "response not in JSON form!");
            }

        }
        else {
            Toast.makeText(this.getApplicationContext(),
                    "sign up failed, try again", Toast.LENGTH_SHORT).show();
        }

    }
}

