package com.credi.fing.publics.service;

import android.view.View;

public interface IBinder {
    void onClick(View itemView, Object data, int position);
    void onLongClick(View itemView, Object data, int position);
}
