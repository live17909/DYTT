package com.bzh.dytt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.BaseFragment;
import com.bzh.dytt.R;


public class ColorHuntFragment extends BaseFragment {

    public static ColorHuntFragment newInstance() {
        return new ColorHuntFragment();
    }


    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.colorhunt_page, container, false);
    }

}