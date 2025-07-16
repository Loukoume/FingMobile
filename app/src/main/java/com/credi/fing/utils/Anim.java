package com.credi.fing.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.credi.fing.R;

public class Anim {
    public static Animation getAnimeGD(Context c) {
        // drt_ch = AnimationUtils.loadAnimation(c, R.anim.drt_gche);
        return AnimationUtils.loadAnimation(c, R.anim.gch_drt);
    }
    public static Animation getAnimeDG(Context c) {
        // drt_ch = AnimationUtils.loadAnimation(c, R.anim.drt_gche);
        return AnimationUtils.loadAnimation(c, R.anim.drt_gche);
    }
    public static Animation getAnimeBH(Context c){
        return  AnimationUtils.loadAnimation(c, R.anim.atgtwo);
    }

    public static Animation getAnimeHB(Context c) {
        return AnimationUtils.loadAnimation(c, R.anim.atg);
    }

    public static Animation getAnimeBalancoir(Context c) {
        return AnimationUtils.loadAnimation(c, R.anim.swing_animation);
    }
}
