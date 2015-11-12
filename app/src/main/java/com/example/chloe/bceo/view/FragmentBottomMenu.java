package com.example.hsianglin.bceoapp.view.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hsianglin.bceoapp.R;

/**
 * Created by HsiangLin on 11/12/15.
 */
public class FragmentBottomMenu extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);
        return view;
    }
}
