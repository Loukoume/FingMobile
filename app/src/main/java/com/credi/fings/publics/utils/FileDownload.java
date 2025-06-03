package com.credi.fings.publics.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.credi.fings.R;
import com.credi.fings.publics.service.ApiClient;
import com.credi.fings.publics.service.ApiService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileDownload {
    public  String appNam;
    private String url;
    private ImageView go;
    private ProgressBar prg;
    private Context context;
    private String ext;
    private String typ;


    public FileDownload(String url, ImageView go, ProgressBar prg, Context context) {
        this.url = url;
        this.go = go;
        this.prg = prg;
        this.context = context;
        this.appNam=context.getResources().getString(R.string.app_name);
        this.ext="."+S.fileType(url);
        this.typ=S.fileType(url);
    }

    public FileDownload(Context context) {
     this.context=context;
        this.appNam=context.getResources().getString(R.string.app_name);
    }

    public  void dawloand(){
        if(prg!=null) prg.setVisibility(View.VISIBLE);
        if(go!=null) go.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null) {
                    String fnam;
                    fnam=S.filemane(url).replace(ext,"");
                    File file = writeResponseBodyToDisk(response.body(),ext,fnam);
                     Dialogue.neutreDialog(file+"",file.exists()+" ok"+fnam,context).show();
                    if(file!=null&& file.exists()) {
                        go.setVisibility(View.VISIBLE);
                        go.setImageResource(R.drawable.ic_pdf_black_24dp);
                    }else {
                        S.toast(context,"Fichier corrompu");
                    }

                } else {
                    go.setVisibility(View.VISIBLE);
                     Dialogue.neutreDialog("Erreur technique \n"+url,"Alert",context).show();

                }
                if(prg!=null)  prg.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(prg!=null)  prg.setVisibility(View.GONE);go.setVisibility(View.VISIBLE);
                Dialogue.neutreDialog(""+t,"Alert",context).show();
                S.toast(context,"Echec de recupération du fichier.");
            }
        });
    }

    private   File writeResponseBodyToDisk(ResponseBody body, String ext,String nom) {
        try {
            // todo change the file location/name according to your needs
            //File pdf = new File(context.getExternalFilesDir(null) + File.separator +"manual_utilisation_yaayi"+ ext);
            File folder = S.getFile(appNam,context);

            File  file;
            if(folder.exists()){
                file=new File(folder,nom+ext);
                // file= MonFichier.getPath(nom+ext,folder.getPath(),context);

                // file = new File(folder+"/"+ nom+ext);
            }else{
                file = new File(folder+"/"+ nom+ext);
            }


            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();
                System.out.println(fileSize+"trutruetrue---------------"+file.exists());
                return file;
            } catch (IOException e) {
                System.out.println("errreurrrrrr---------------"+e.getMessage());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("errreurrrrrr222---------------"+e.getMessage());
            return null;
        }
    }

    //public  static String FOLDER_YAAYI ="";

    public  String extraireChema(String url){
        String fnam=S.filemane(url);
        fnam=S.getPath(appNam+"/"+fnam,context);
        return fnam;
    }
}
