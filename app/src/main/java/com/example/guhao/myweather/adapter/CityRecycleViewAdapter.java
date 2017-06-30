package com.example.guhao.myweather.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;

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
    private MyItemOnClickListener listener;

    public CityRecycleViewAdapter(List<String> data) {
        this.mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item,parent,false);
        final ViewHolder holder = new ViewHolder(v);
        holder.cardview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
//                String str = mData.get(position);
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
        //holder.mTv.setText(getInfo(mData.get(position)));
        //holder.mTv.setText(mData.get(position));
        holder.mTv.setText(mData.get(position));
    }

    public void updateData(String str, int position){
        mData.set(position,str);
        notifyItemChanged(position);
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
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.city_card_view);
            mTv = (TextView) itemView.findViewById(R.id.city_card_tv);
            //WeatherConstant.cardList.add(mTv);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mData, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mData, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }
}