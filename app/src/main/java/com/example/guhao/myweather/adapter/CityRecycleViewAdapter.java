package com.example.guhao.myweather.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.util.IconUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: GuHao
 * Date: 6/22/17
 * Time: 11:15 AM
 * Desc:
 */


public class CityRecycleViewAdapter extends RecyclerView.Adapter<CityRecycleViewAdapter.ViewHolder> implements ItemTouchHelperAdapter{
    private List<String> mData;
    private List<String> mCond;
    private List<String> mTemp;
    private MyItemOnClickListener listener;
    private Context context;

    public CityRecycleViewAdapter(List<String> data, Context context) {
        this.mData = data;
        this.context = context;
        mCond = new ArrayList<>();
        mTemp = new ArrayList<>();
        for (int i = 0; i < data.size();i++){
            mCond.add(null);
            mTemp.add(null);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item,parent,false);
        final ViewHolder holder = new ViewHolder(v);
        holder.cardview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                listener.onItemClick(v,position);
            }
        });

        return holder;
    }

    public void setItemOnClickListener(MyItemOnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.city_cond.setImageDrawable(context.getResources().getDrawable(IconUtil.getWeatherID(mCond.get(position))));
        holder.city_name.setText(mData.get(position));
        holder.city_temp.setText(mTemp.get(position)+"˚");
    }

    public void updateData(String city,String cond, String temp, int position){
        mData.set(position,city);
        mCond.set(position,cond);
        mTemp.set(position,temp);
        notifyItemChanged(position);
    }

    public void addItems(){
        mCond.add("----");
        mTemp.add("---");
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView city_name;
        ImageView city_cond;
        TextView city_temp;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.city_card_view);
            city_cond = (ImageView) itemView.findViewById(R.id.city_cond);
            city_temp = (TextView) itemView.findViewById(R.id.city_temp);
            city_name = (TextView) itemView.findViewById(R.id.city_card_tv);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mData, i, i + 1);
                Collections.swap(mCond, i, i + 1);
                Collections.swap(mTemp, i, i + 1);
                Collections.swap(WeatherConstant.weatherList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mData, i, i - 1);
                Collections.swap(mCond, i, i - 1);
                Collections.swap(mTemp, i, i - 1);
                Collections.swap(WeatherConstant.weatherList, i, i - 1);
            }
        }
        //WeatherConstant.updateSharedPreferences(context);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        mCond.remove(position);
        mTemp.remove(position);
        WeatherConstant.weatherList.remove(position);
        WeatherConstant.updateSharedPreferences(context);
        notifyItemRemoved(position);
    }
}