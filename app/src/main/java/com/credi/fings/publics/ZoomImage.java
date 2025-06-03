package com.credi.fings.publics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.zoom.ZoomImageView;
import com.credi.fings.R;
import com.credi.fings.publics.service.ApiClient;
import com.credi.fings.publics.utils.S;

public class ZoomImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_image);
        ZoomImageView im=findViewById(R.id.zoom);
        im.zoomTo(0.5f,true);
       // im.setMaxHeight(MainActivity.y);im.setMaxWidth(MainActivity.x);
       String url=getIntent().getStringExtra("url");
        //Dialogue.neutreDialog(url,"",this).show();
      /*  Picasso.get()
                .load(ApiClient.urlFile(url))
                /*.resize(MainActivity.x,MainActivity.y)
                .centerCrop()*/
      /*  .into(im);
         {
            Picasso.get()
                    .load(new File(url))
                    /*.resize(MainActivity.x,MainActivity.y)
                    .centerCrop()*/
                /*    .into(im);
        }*/
        S.saveImage(this,im, ApiClient.urlFile(url));
    }
}
