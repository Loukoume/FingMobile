package com.credi.fing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.viewpager.widget.ViewPager;

import com.credi.fing.R;
import com.credi.fing.entity.Client;
import com.credi.fing.main.SectionsPagerAdapter;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.LesConnectes;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class PagerActivity extends AppCompatActivity {
    public static int tab;
    Context context;

    public static LinearLayout top,ltop;
    public static View cover;
    public static Animation atg,bs_ht,drt_ch,gch_drt,rot,rot2,anime,bsht,htbs;
    static AppBarLayout app;
    public static String monnaie;
    public static List<Object> savingsAccounts;
    public static List<Object> loanAccounts;
    Client client;
    ViewPager viewPager;
    TabLayout tabs;
    ImageView mort;
    SectionsPagerAdapter sectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        context=this;app=findViewById(R.id.app);
        cover=findViewById(R.id.cover);
        top=findViewById(R.id.top);
        ltop=findViewById(R.id.ltop);
        mort=findViewById(R.id.mor);
       // mort.setVisibility(View.GONE);
        client= (Client) getIntent().getSerializableExtra("client");
        loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
        savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
         viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
         tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        ImageView fab = findViewById(R.id.mor);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetBehavior sheetBehavior;
               // sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
               // hideBottomSheet(sheetBehavior);app.setVisibility(View.VISIBLE);
                top.setVisibility(View.GONE);
            }
        });
        if(getIntent().hasExtra("new")){
            tabs.getTabAt(1).select();
        }
        mort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] m={"Se déconnecter"};
                PopupMenu pop= S.popupMenu(view,m);
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                new LesConnectes().remove(context,Inscription.user);
                                MonFichier.ecrire(context,"displayName","");
                                MonFichier.ecrire(context,"client","");
                                startActivity(new Intent(context, Inscription.class));
                                finish();
                                break;
                            case 2:
                                break;
                        }
                        return false;
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}