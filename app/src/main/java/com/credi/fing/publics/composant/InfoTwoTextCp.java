package com.credi.fing.publics.composant;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credi.fing.R;
import com.credi.fing.publics.OnClickView;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.S;

public class InfoTwoTextCp {
    private OnClickView onClickView;
    private TextView title;
    private TextView subTitle;
    private Context context;
    private LinearLayout lmain;
    private int titleTextColors;
    private int subTitleTextColors;
    private int subTitleTextSize;
    private int titleTextSize;
    private int backgrounColors;
    private String s_title,s_subTitle;

    public InfoTwoTextCp(Context context) {
        this.context = context;
    }

    public InfoTwoTextCp(Context context, String s_title, String s_subTitle) {
        this.context = context;
        this.s_title = s_title;
        this.s_subTitle = s_subTitle;
    }

    private View view(){
        View view= Ut.getView(context, R.layout.info_two_text_layout);
        title=view.findViewById(R.id.title);
        subTitle=view.findViewById(R.id.secondre);
        lmain=view.findViewById(R.id.lmain);
        title.setText(s_title);
        subTitle.setText(s_subTitle);
        if (backgrounColors > 0) {
            lmain.setBackgroundColor(Ut.getColor(context,backgrounColors));
        }
        if(titleTextColors>0){
            title.setTextColor(Ut.getColor(context,titleTextColors));
        }
        if(subTitleTextColors>0){
            subTitle.setTextColor(Ut.getColor(context,subTitleTextColors));
        }
        if(titleTextSize>0){
            title.setTextSize(S.dpToPx(titleTextSize,context.getResources()));
        }
        if(subTitleTextSize>0){
            subTitle.setTextSize(S.dpToPx(subTitleTextSize,context.getResources()));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickView!=null){
                    onClickView.onClick(view,0);
                }
            }
        });

        return view;
    }

    public OnClickView getOnClickView() {
        return onClickView;
    }

    public InfoTwoTextCp setOnClickView(OnClickView onClickView) {
        this.onClickView = onClickView;
        return this;
    }

    public TextView getTitle() {
        return title;
    }

    public InfoTwoTextCp setTitle(TextView title) {
        this.title = title;
        return this;
    }

    public TextView getSubTitle() {
        return subTitle;
    }

    public InfoTwoTextCp setSubTitle(TextView subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public InfoTwoTextCp setContext(Context context) {
        this.context = context;
        return this;
    }

    public LinearLayout getLmain() {
        return lmain;
    }

    public InfoTwoTextCp setLmain(LinearLayout lmain) {
        this.lmain = lmain;
        return this;
    }

    public int getTitleTextColors() {
        return titleTextColors;
    }

    public InfoTwoTextCp setTitleTextColors(int titleTextColors) {
        this.titleTextColors = titleTextColors;
        return this;
    }

    public int getSubTitleTextColors() {
        return subTitleTextColors;
    }

    public InfoTwoTextCp setSubTitleTextColors(int subTitleTextColors) {
        this.subTitleTextColors = subTitleTextColors;
        return this;
    }

    public int getSubTitleTextSize() {
        return subTitleTextSize;
    }

    public InfoTwoTextCp setSubTitleTextSize(int subTitleTextSize) {
        this.subTitleTextSize = subTitleTextSize;
        return this;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public InfoTwoTextCp setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public int getBackgrounColors() {
        return backgrounColors;
    }

    public InfoTwoTextCp setBackgrounColors(int backgrounColors) {
        this.backgrounColors = backgrounColors;
        return this;
    }

    public String getS_title() {
        return s_title;
    }

    public InfoTwoTextCp setS_title(String s_title) {
        this.s_title = s_title;
        return this;
    }

    public String getS_subTitle() {
        return s_subTitle;
    }

    public InfoTwoTextCp setS_subTitle(String s_subTitle) {
        this.s_subTitle = s_subTitle;
        return this;
    }
}
