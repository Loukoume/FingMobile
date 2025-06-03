package com.credi.fings.publics.composant;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credi.fings.R;
import com.credi.fings.publics.OnClickView;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.S;

public class TextCp {
    private OnClickView onClickView;
    private TextView title;
    private Context context;
    private LinearLayout lmain;
    private int titleTextColors;
    private int titleTextSize;
    private int backgrounColors;
    private String s_title;


    public TextCp(Context context, String s_title) {
        this.context = context;
        this.s_title = s_title;
    }



    private View view(){
        View view= Ut.getView(context, R.layout.text);
        title=view.findViewById(R.id.title);
         lmain=view.findViewById(R.id.lmain);
        title.setText(s_title);
        if (backgrounColors > 0) {
            lmain.setBackgroundColor(Ut.getColor(context,backgrounColors));
        }
        if(titleTextColors>0){
            title.setTextColor(Ut.getColor(context,titleTextColors));
        }

        if(titleTextSize>0){
            title.setTextSize(S.dpToPx(titleTextSize,context.getResources()));
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

    public TextCp setOnClickView(OnClickView onClickView) {
        this.onClickView = onClickView;
        return this;
    }

    public TextView getTitle() {
        return title;
    }

    public TextCp setTitle(TextView title) {
        this.title = title;
        return this;
    }



    public Context getContext() {
        return context;
    }

    public TextCp setContext(Context context) {
        this.context = context;
        return this;
    }

    public LinearLayout getLmain() {
        return lmain;
    }

    public TextCp setLmain(LinearLayout lmain) {
        this.lmain = lmain;
        return this;
    }

    public int getTitleTextColors() {
        return titleTextColors;
    }

    public TextCp setTitleTextColors(int titleTextColors) {
        this.titleTextColors = titleTextColors;
        return this;
    }



    public int getTitleTextSize() {
        return titleTextSize;
    }

    public TextCp setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public int getBackgrounColors() {
        return backgrounColors;
    }

    public TextCp setBackgrounColors(int backgrounColors) {
        this.backgrounColors = backgrounColors;
        return this;
    }


}
