package com.example.galaxytrader.listView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.galaxytrader.R;

public class PlanetListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] planetName;
    private final Integer[] imgid;
    private Integer playerOnPlanet;

    public PlanetListViewAdapter(Activity context, String[] planetName, Integer[] imgid, Integer playerOnPlanet) {
        super(context, R.layout.planet_view, planetName);

        this.playerOnPlanet = playerOnPlanet;
        this.context=context;
        this.planetName=planetName;
        this.imgid=imgid;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.planet_view, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.planetNameListView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.planetListImg);

        titleText.setText(planetName[position]);
        imageView.setImageResource(imgid[position]);

        if(playerOnPlanet == position) {
            TextView currentlyOnPlanet = (TextView) rowView.findViewById(R.id.currentlyOnPlanet);
            currentlyOnPlanet.setVisibility(View.VISIBLE);
            ConstraintLayout layout = (ConstraintLayout) rowView.findViewById(R.id.planetListViewLayout);
            int color = ResourcesCompat.getColor(parent.getResources(), R.color.primaryLightColor, null);
            layout.setBackgroundColor(color);
            layout.setAlpha(0.7f);
        }

        return rowView;
    }
}
