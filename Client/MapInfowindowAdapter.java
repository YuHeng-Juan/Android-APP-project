package com.example.myapplication;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapInfowindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public MapInfowindowAdapter (Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.layout_map_infowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.infowindow_title);
        tvTitle.setText(marker.getTitle());

        return view;
    }
}