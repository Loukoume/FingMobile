package com.credi.fings.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.credi.fings.R;
import com.credi.fings.entity.Client;
import com.credi.fings.entity.LoanAccount;
import com.credi.fings.main.SectionsPagerAdapter;
import com.credi.fings.pojo.LoanPojo;
import com.credi.fings.pojo.LoanProductResponse;
import com.credi.fings.publics.service.ApiService;
import com.credi.fings.publics.service.RetrofitClient;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.utils.Json;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    SectionsPagerAdapter sectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        context=this;app=findViewById(R.id.app);
        cover=findViewById(R.id.cover);
        top=findViewById(R.id.top);
        ltop=findViewById(R.id.ltop);

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}