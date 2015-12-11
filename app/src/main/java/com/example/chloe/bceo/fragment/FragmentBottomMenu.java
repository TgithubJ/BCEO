package com.example.chloe.bceo.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.view.BrowseActivity;
import com.example.chloe.bceo.view.GroupsActivity;
import com.example.chloe.bceo.view.MypageActivity;
import com.example.chloe.bceo.view.OrderActivity;
import com.example.chloe.bceo.view.SellActivity;


/**
 * Created by HsiangLin on 11/12/15.
 */
public class FragmentBottomMenu extends Fragment {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FragmentBottomMenu.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);

        //Button Menu
        Button buttonBrowse = (Button) view.findViewById(R.id.button_browse);
        //Action taken when a button is pressed
        buttonBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), BrowseActivity.class);
                myIntent.putExtra("user", user);
                startActivity(myIntent);
            }
        });

        Button buttonOrder = (Button) view.findViewById(R.id.button_order);
        //Action taken when a button is pressed
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), OrderActivity.class);
                myIntent.putExtra("user", user);
                startActivity(myIntent);
            }
        });

        Button buttonSell = (Button) view.findViewById(R.id.button_sell);
        //Action taken when a button is pressed
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SellActivity.class);
                myIntent.putExtra("user", user);
                startActivity(myIntent);
            }
        });

        Button buttonGroup = (Button) view.findViewById(R.id.button_group);
        //Action taken when a button is pressed
        buttonGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), GroupsActivity.class);
                myIntent.putExtra("user", user);
                startActivity(myIntent);
            }
        });

        Button buttonMyPage = (Button) view.findViewById(R.id.button_mypage);
        //Action taken when a button is pressed
        buttonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MypageActivity.class);
                myIntent.putExtra("user", user);
                startActivity(myIntent);
            }
        });

        return view;
    }

}
