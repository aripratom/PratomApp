package com.aripratom.pratomapp.click;

import android.view.View;

/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */
public class CustomOnItemClickListener implements View.OnClickListener {
    private int position;
    private OnItemClickCallback onItemClickCallback;
    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback){
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position);

    }
    public interface OnItemClickCallback{
        void onItemClicked(View view, int position);
    }
}

