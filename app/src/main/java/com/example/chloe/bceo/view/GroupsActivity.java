package com.example.chloe.bceo.view;

import android.app.Activity;
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

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;
import com.example.chloe.bceo.util.Image64Base;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GroupsActivity extends AppCompatActivity {
    private ImageButton groupImageButton;

    private Spinner future_group;
    private Button join;

    private TextView tmp;
    private Image64Base imageUtil;
    private HTTPGet httpUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        /* retrieve logged in user in JSON format
        {"id":9, "email":"woojoos1@andrew.cmu.edu",
        "phone":"4109712779", "group_id":2}
        */
        User user = (User) getIntent().getSerializableExtra("user");

        groupImageButton = (ImageButton) findViewById(R.id.groupImage);
        tmp = (TextView) findViewById(R.id.userView);
        future_group = (Spinner) findViewById(R.id.group_spinner);
        join = (Button) findViewById(R.id.join_group);

        Log.w("ERROR:", (Integer.toString(user.getGroupID())));

        if (user.getGroupID() == 0) {
            tmp.setText("No joined group yet!");

        } else {
            String urlStr = httpUtil.buildURL("group_logo?id=" + user.getGroupID());
            String response = httpUtil.getResponse(urlStr);

            groupImageButton.setImageBitmap(imageUtil.decodeBase64(response));
        }

        groupImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browse();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroup();
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
        this.startActivity(intent);
    }

    private void joinGroup() {
        Intent intent = new Intent(this, VerifyActivity.class);
        this.startActivity(intent);
    }
}
