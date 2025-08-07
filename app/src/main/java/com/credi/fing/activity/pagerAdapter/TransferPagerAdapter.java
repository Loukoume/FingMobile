package com.credi.fing.activity.pagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.credi.fing.activity.TransferActivity;
import com.credi.fing.activity.pagerBeneficiaireAdd.BeneficiaireFragment;
import com.credi.fing.activity.pagerBeneficiaireAdd.CompteFragment;
import com.credi.fing.enums.TypeAdapter;
import com.credi.fing.pojo.TransfertPojo;

public class TransferPagerAdapter extends FragmentStateAdapter {
    int size=3;
    TypeAdapter t;
    public TransferPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }
    public TransferPagerAdapter(@NonNull FragmentActivity fa, int size, TypeAdapter t) {
        super(fa);
        this.size=size;
        this.t=t;
    }

    @NonNull @Override
    public Fragment createFragment(int position) {
        switch (t){
            case BENEFICIAIRE:
                switch (position) {
                    case 1: return new CompteFragment();
                    default: return new BeneficiaireFragment();
                }
            default:
                switch (position) {
                    case 0: return new SenderFragment();
                    case 1: return new NoteFragment();
                    case 2: return new AmountFragment();
                    default: return new BeneficiaireFragment();
                }
        }

    }

    @Override public int getItemCount() { return size; }
}

