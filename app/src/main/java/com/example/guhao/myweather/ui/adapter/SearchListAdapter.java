package com.example.guhao.myweather.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.guhao.myweather.data.bean.CityEntity;

import java.util.List;

/**
 * Author: GuHao
 * Date: 6/21/17
 * Time: 2:11 PM
 * Desc:
 */

public class SearchListAdapter extends ArrayAdapter<CityEntity> {
    private int resource;

    public SearchListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CityEntity> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CityEntity cityEntity = getItem(position);
        TextView textView;
        if (convertView == null){
            textView = (TextView)LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }else{
            textView = (TextView) convertView;
        }

        //TextView textView = new TextView(getContext());

        String cityName;
        if (cityEntity.getCity_cn().equals(cityEntity.getUp_cn())&&cityEntity.getUp_cn().equals(cityEntity.getState_cn())){
            cityName = cityEntity.getCity_cn();
        }else if (cityEntity.getState_cn().equals(cityEntity.getUp_en())){
            cityName = cityEntity.getCity_cn()+ " " + cityEntity.getState_cn();
        }else if (cityEntity.getCity_cn().equals(cityEntity.getUp_cn())){
            cityName = cityEntity.getCity_cn() + " " + cityEntity.getState_cn();
        }else {
            cityName = cityEntity.getCity_cn() + " " + cityEntity.getUp_cn() + " " + cityEntity.getState_cn();
        }
        textView.setText(cityName);
        textView.setTextSize(20);
        //textView.
        return textView;
    }


}
