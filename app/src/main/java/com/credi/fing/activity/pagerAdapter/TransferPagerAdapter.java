package com.credi.fing.activity.pagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TransferPagerAdapter extends FragmentStateAdapter {
    public TransferPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new SenderFragment();
            case 1: return new BeneficiaryFragment();
            case 2: return new AmountFragment();
            default: return new NoteFragment();
        }
    }

    @Override public int getItemCount() { return 3; }
}

