package com.example.chloe.bceo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;

import org.json.JSONException;
import org.json.JSONObject;

public class VerifyActivity extends Activity {
    private Button send_verify_email;
    private Button confirm_verified_button;

    private User user;
    private String group_name;
    private TextView groupDomain;
    private String group_domain_response;
    private String group_id;
    private EditText userGroupEmail;
    private boolean sentEmailFlag;

    private HTTPGet httpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);


        user = (User) getIntent().getSerializableExtra("user");
        group_name = (String) getIntent().getSerializableExtra("group");
        String url = httpUtil.buildURL("group_id?name=" + group_name);
        group_id = httpUtil.getResponse(url);
        userGroupEmail = (EditText) findViewById(R.id.user_group_email);

        groupDomain = (TextView) findViewById(R.id.emailDomainTextView);

        group_domain_response = httpUtil.getResponse(
                httpUtil.buildURL("group_domain?id=" + group_id));
        groupDomain.setText("Ending with: " + group_domain_response);

        send_verify_email = (Button) findViewById(R.id.send_verification);
        confirm_verified_button = (Button) findViewById(R.id.return_to_group);

        sentEmailFlag = false;
        send_verify_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        confirm_verified_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadGroupPage();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verify, menu);
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

    public void sendEmail() {
        String to = userGroupEmail.getText().toString();
        if (to.equals("")) {
            Toast.makeText(VerifyActivity.this,
                    "Email address can't be empty",Toast.LENGTH_LONG).show();
            return;
        } else if(!isEmailValid(to)) {
            Toast.makeText(VerifyActivity.this,
                    "Invalid address, please check again",Toast.LENGTH_LONG).show();
            return;
        }

        String user_email_domain = to.split("@")[1];
        String subject = "Please verify";
        String body = new StringBuilder().append("http://52.34.169.54:3000/update_group?id="
                + user.getUserEmail() + "&group_id=" + group_id).toString();


        if (!group_domain_response.equals(user_email_domain)) {
            Toast.makeText(VerifyActivity.this,
                    "Your email domain doesn't match with group email domain",
                    Toast.LENGTH_LONG).show();
            return;
        }


        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, body);
        email.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(email, "Send mail with: "));
            sentEmailFlag = true;
            Log.w("INFO", "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(VerifyActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void reloadGroupPage() {
        if (sentEmailFlag = false) {
            Toast.makeText(VerifyActivity.this,
                "Send verification email first", Toast.LENGTH_SHORT).show();
            return;
        }


        /* update user data if email verification is confirmed */
        StringBuilder tail =
                new StringBuilder("login?");
        tail.append("email=" + user.getUserEmail());
        tail.append("&pw=" + user.getPassword());

        String urlStr = httpUtil.buildURL(tail.toString());
        String response = httpUtil.getResponse(urlStr);

        try  {
            JSONObject job = new JSONObject(response);
            int login_id = job.getInt("id");
            String login_name = job.getString("name");
            String login_email = job.getString("email");
            String login_password = job.getString("password");
            String login_phone = job.getString("phone");
            int login_groupId = job.getInt("group_id");

            user = new User(
                    login_id, login_name, login_email, login_password, login_groupId, login_phone);
        } catch (JSONException e) {
            Log.w("ERROR:", "response not in JSON form!");
        }

        if (user.getGroupID() != Integer.parseInt(group_id)) {
            notVerifiedEmail();
        } else {
            continueGroupPage();

        }
    }

    public void notVerifiedEmail() {

        // aler dialog for user to notify that email has sent
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        StringBuilder str = new StringBuilder("Email not verified yet");
        str.append("\n");
        str.append("Do you still wish to continue to group page?");
        alertDialogBuilder.setMessage(str);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                continueGroupPage();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void continueGroupPage() {
        Intent intent = new Intent(this, GroupsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //passing logged in user
        intent.putExtra("user", user);

        this.startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }
}
