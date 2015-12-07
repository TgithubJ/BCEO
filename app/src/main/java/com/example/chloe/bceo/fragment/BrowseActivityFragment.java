package com.example.chloe.bceo.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chloe.bceo.R;

/**
 * Created by HsiangLin on 11/12/15.
 */
public class BrowseActivityFragment extends Fragment {

    public BrowseActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browse, container, false);
    }
}
