package com.credi.fing.publics;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.publics.adapters.generiqueAdapter.PdfAdapter;
import com.credi.fing.publics.ecouteur.GridSpacingItemDecoration;
import com.credi.fing.publics.ecouteur.Interface;
import com.credi.fing.publics.ecouteur.RecyclerTouchListener;
import com.credi.fing.publics.service.CustomVideoView;
import com.credi.fing.publics.utils.S;

import java.io.File;
import java.util.List;

public class PdfViewer extends AppCompatActivity {

    ImageView notif, back;
    TextView T, t, nu;
    Toolbar toolbar;
    
    static String path;
    RecyclerView recyclerView;
    static PdfAdapter adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        toolbar = findViewById(R.id.toolbar_personnalise);
        setSupportActionBar(toolbar);
        context=this;
        back=findViewById(R.id.back);
        path=getIntent().getStringExtra("url");
        //path= ApiClient.urlFile(path);
        System.out.println(" ==url==  "+path);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setSubtitleTextColor(Color.parseColor("#FFFFFF"));
        TextView tx=findViewById(R.id.tx_text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView=findViewById(R.id.pdfRecyclerView);
        if(path.contains(".mp4")){
            recyclerView.setVisibility(View.GONE);
            LinearLayout lv=findViewById(R.id.lvideo);
            lv.setVisibility(View.VISIBLE);
            CustomVideoView videoView=findViewById(R.id.custom_video);
            videoView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    String videoPath = path;
                    File file = new File(videoPath);
                    if (!file.exists() || !file.canRead()) {
                        Log.e("VideoView", "File does not exist or cannot be read: " + videoPath);
                        return;
                    }
                    // La surface est prête, vous pouvez configurer et préparer le MediaPlayer
                    videoView.setVideoPath(path); // Assurez-vous que videoPath est valide
                    videoView.start(); // ou préparez la vidéo ici
                    videoView.setCornerRadius(50f);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    // Peut rester vide
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    // Libérez les ressources du MediaPlayer si nécessaire
                    videoView.stopPlayback();
                }
            });



        }else {
            preparerDatas();
        }

        if(getIntent().hasExtra("title")){
            tx.setText(getIntent().getStringExtra("title").toUpperCase());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private  void preparerDatas() {
        adapter = new PdfAdapter(List.of(path.split(";")),context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount()==0)
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, S.dpToPx(0,context.getResources()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new RecyclerTouchListener.SwipeClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onSwipeLeft(View view, int position) {

            }

            @Override
            public void onSwipeRight(View view, int position) {

            }
        }));
        /*

        * */
    }


}
