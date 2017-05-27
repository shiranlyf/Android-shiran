package com.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.car.R;
import com.car.activity.NearbySearchActivity;


/**
 * 问答
 * 高德地图
 * 1、获取SHA1：Window--Prefenrence--Android--Build  
 * 48:4A:4B:E7:CB:D5:D3:32:7D:26:43:1D:79:7F:21:C0:50:99:68:B6
 * 
 */
public class SearchFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_search_main, null);
		startActivity(new Intent(getActivity(), NearbySearchActivity.class));
    	return view;
    }
}
