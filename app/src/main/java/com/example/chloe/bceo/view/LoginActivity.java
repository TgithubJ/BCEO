package com.example.chloe.bceo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button signup;
    private Button mEmailSignInButton;

    private HTTPGet httpUtil;

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
        StringBuilder tail =
                new StringBuilder("login?");
        tail.append("email=" + user_email);
        tail.append("&pw=" + user_password);

        String urlStr = httpUtil.buildURL(tail.toString());
        String response = httpUtil.getResponse(urlStr);

        if( response.equals("not exist")) {
            Toast.makeText(this.getApplicationContext(),
                    "Email doesn't exist", Toast.LENGTH_SHORT).show();

        } else if (response.equals("wrong password")){
            Toast.makeText(this.getApplicationContext(),
                    "Wrong password", Toast.LENGTH_SHORT).show();
        } else if (response.substring(0,6).equals("{\"id\":")) {
            try  {
                JSONObject job = new JSONObject(response);
                int login_id = job.getInt("id");
                String login_name = job.getString("name");
                String login_email = job.getString("email");
                String login_password = job.getString("password");
                String login_phone = job.getString("phone");
                int login_groupId = job.getInt("group_id");

                User u = new User(
                        login_id, login_name,login_email, login_password, login_groupId,
                                                                          login_phone);

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

