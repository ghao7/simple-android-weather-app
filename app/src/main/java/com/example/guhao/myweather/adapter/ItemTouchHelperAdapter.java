package com.example.guhao.myweather.adapter;

/**
 * Author: GuHao
 * Date: 6/30/17
 * Time: 10:45 AM
 * Desc:
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}