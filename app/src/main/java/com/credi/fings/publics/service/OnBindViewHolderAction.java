package com.credi.fings.publics.service;

import com.credi.fings.publics.adapters.generiqueAdapter.AdapterViewHolder;

public interface OnBindViewHolderAction {
    void bind(AdapterViewHolder itemView, Object object, int position);
}

