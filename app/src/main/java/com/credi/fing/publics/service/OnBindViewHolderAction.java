package com.credi.fing.publics.service;

import com.credi.fing.publics.adapters.generiqueAdapter.AdapterViewHolder;

public interface OnBindViewHolderAction {
    void bind(AdapterViewHolder itemView, Object object, int position);
}

