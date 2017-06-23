package com.example.guhao.myweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.bean.WeatherEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: GuHao
 * Date: 6/22/17
 * Time: 11:15 AM
 * Desc:
 */


public class CityRecycleViewAdapter extends RecyclerView.Adapter<CityRecycleViewAdapter.ViewHolder>{
    private List<WeatherEntity> mData;

    public CityRecycleViewAdapter(List<WeatherEntity> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<WeatherEntity> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTv.setText(getInfo(mData.get(position)));
    }

    public String getInfo(WeatherEntity entity){
        String city = entity.getHeWeather5().get(0).getBasic().getCity();
        String temp = entity.getHeWeather5().get(0).getNow().getTmp();
        String cond = entity.getHeWeather5().get(0).getNow().getCond().getTxt();

        return city + "\n" + temp + "\n" + cond;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.city_card_tv);
        }
    }
}