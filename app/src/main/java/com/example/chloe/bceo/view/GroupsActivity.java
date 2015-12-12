package com.example.chloe.bceo.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;
import com.example.chloe.bceo.util.Image64Base;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupsActivity extends AppCompatActivity {
    private ImageButton groupImageButton_1;
    private ImageButton leaveGroupButton;

    private Spinner all_groups;
    private Button join;
    private ImageButton refreshButton;

    private TextView no_group_text;
    private Image64Base imageUtil;
    private HTTPGet httpUtil;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        /* retrieve logged in user in JSON format
        {"id":9, "email":"woojoos1@andrew.cmu.edu",
        "phone":"4109712779", "group_id":2} */
        user = (User) getIntent().getSerializableExtra("user");

        groupImageButton_1 = (ImageButton) findViewById(R.id.groupImageButton1);
        leaveGroupButton = (ImageButton) findViewById(R.id.leaveButton);

        no_group_text = (TextView) findViewById(R.id.noGroupTextView);
        all_groups = (Spinner) findViewById(R.id.group_spinner);
        join = (Button) findViewById(R.id.join_group);
        refreshButton = (ImageButton) findViewById(R.id.refreshButton);

        Log.w("ERROR:", (Integer.toString(user.getGroupID())));

        // group_id = 0 is the default when signing up, so 0 = no group
        if (user.getGroupID() == 0) {
            no_group_text.setText("No joined group yet!");
        } else {
            String urlStr = httpUtil.buildURL("group_logo?id=" + user.getGroupID());
            String response = httpUtil.getResponse(urlStr);

            groupImageButton_1.setImageBitmap(imageUtil.decodeBase64(response));
            leaveGroupButton.setImageResource(R.drawable.leave);
        }

        groupImageButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browse();
            }
        });

        leaveGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leave();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroup();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void browse() {
        Intent intent = new Intent(this, BrowseActivity.class);
        //passing logged in user
        intent.putExtra("user", user);
        intent.putExtra("visibility", true);
        this.startActivity(intent);
    }

    private void leave() {
        // aler dialog for user to confirm leaving the group
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        StringBuilder str = new StringBuilder("Are you sure you want to leave? \n");
        alertDialogBuilder.setMessage(str);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                StringBuilder tail =
                        new StringBuilder(
                                "leave_group?user_email=" + user.getUserEmail());
                String urlStr = httpUtil.buildURL(tail.toString());
                String response = httpUtil.getResponse(urlStr);

                if (!response.equals("left")) {
                    Toast.makeText(GroupsActivity.this,
                            "Oops! unable to leave group, try again!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                refresh();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void joinGroup() {
        String group_name = all_groups.getSelectedItem().toString();
        if (group_name.equals("Search group..")) {
            Toast.makeText(GroupsActivity.this,
                    "Select the group to join first!", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, VerifyActivity.class);
        //passing logged in user
        intent.putExtra("user", user);
        intent.putExtra("group", group_name);
        this.startActivity(intent);
    }

    private void refresh() {
        // update group id if email is verified
        StringBuilder tail =
                new StringBuilder("login?");
        tail.append("email=" + user.getUserEmail());
        tail.append("&pw=" + user.getPassword());

        String urlStr = httpUtil.buildURL(tail.toString());
        String response = httpUtil.getResponse(urlStr);

        try  {
            JSONObject job = new JSONObject(response);
            int login_groupId = job.getInt("group_id");

            user.setGroupID(login_groupId);
        } catch (JSONException e) {
            Log.w("ERROR:", "response not in JSON form!");
        }

        Intent intent = new Intent(this, GroupsActivity.class);
        //passing updated user
        intent.putExtra("user", user);
        this.startActivity(intent);
    }

}
