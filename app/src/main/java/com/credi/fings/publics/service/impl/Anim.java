package com.credi.fings.publics.service.impl;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.credi.fings.R;
import com.credi.fings.publics.utils.S;

public class Anim {
    public static Animation atg, bs_ht, drt_ch, gch_drt, rot, rot2, anime, bsht, htbs;


    public static Animation getAnime(Context c) {
        atg = AnimationUtils.loadAnimation(c, R.anim.atg);
        bs_ht = AnimationUtils.loadAnimation(c, R.anim.atgtwo);
        drt_ch = AnimationUtils.loadAnimation(c, R.anim.drt_gche);
        rot = AnimationUtils.loadAnimation(c, R.anim.rot_hs_bas_d);
        rot2 = AnimationUtils.loadAnimation(c, R.anim.rot_bs_ht);
        gch_drt = AnimationUtils.loadAnimation(c, R.anim.gch_drt);
        switch (S.randum(0, 5)) {
            case 0:
                return atg;
            case 1:
                return bs_ht;
            case 2:
                return drt_ch;
            case 3:
                return gch_drt;
            case 4:
                return rot;
            default:
                return rot2;
        }
    }

    public static Animation getAnime_(Context c) {
        atg = AnimationUtils.loadAnimation(c, R.anim.atg);
        bs_ht = AnimationUtils.loadAnimation(c, R.anim.atgtwo);
        drt_ch = AnimationUtils.loadAnimation(c, R.anim.drt_gche);
        gch_drt = AnimationUtils.loadAnimation(c, R.anim.gch_drt);
        switch (S.randum(0, 5)) {
            case 0:
                return atg;
            case 1:
                return bs_ht;
            case 2:
                return drt_ch;
            default:
                return gch_drt;
        }
    }

    public static Animation getAnimeHB(Context c) {
        atg = AnimationUtils.loadAnimation(c, R.anim.atg);
        bs_ht = AnimationUtils.loadAnimation(c, R.anim.atgtwo);
        switch (S.randum(0, 1)) {
            case 0:
                return atg;
            case 1:
                return bs_ht;
            default:
                return rot2;
        }
    }

    public static Animation getAnimeHB_(Context c) {
        return AnimationUtils.loadAnimation(c, R.anim.atg);
    }

    public static Animation getAnimeBH(Context c) {
        return AnimationUtils.loadAnimation(c, R.anim.atgtwo);
    }

    public static Animation getAnimeG(Context c) {
        return AnimationUtils.loadAnimation(c, R.anim.gch_drt);
    }

    public static Animation getAnimeD(Context c) {
        return AnimationUtils.loadAnimation(c, R.anim.drt_gche);
    }

    public static Animation getAnimeGD(Context c) {
        // drt_ch = AnimationUtils.loadAnimation(c, R.anim.drt_gche);
        return AnimationUtils.loadAnimation(c, R.anim.gch_drt);
    }

}
