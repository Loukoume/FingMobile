package com.credi.fings.publics.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.credi.fings.R;
import com.credi.fings.publics.UploadFileResponse;
import com.credi.fings.publics.service.ApiClient;
import com.credi.fings.publics.service.ApiService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaUtils {
    public  String appNam;
    public String url;
    public ImageView go;
    public ProgressBar prg;
    public Context context;
    public String ext;
    public Activity activity;


    public MediaUtils(String url, ImageView go, ProgressBar prg, Context context) {
        this.url = url;
        this.go = go;
        this.prg = prg;
        this.context = context;
        this.appNam=context.getResources().getString(R.string.app_name);
        this.ext="."+S.fileType(url);
        this.activity= (Activity) context;
    }

    public MediaUtils(Context context) {
     this.context=context;
     this.appNam=context.getResources().getString(R.string.app_name);
    }

    public  void dowloand(String url,String ext, UploadCallback uploadCallback){
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
                    fnam=S.filemane(url).replace(ext!=null?ext:"","");
                    File file = writeResponseBodyToDisk(response.body(),ext,fnam);
                    if(file!=null&& file.exists()) {
                        Media media=new Media();
                        media.setPath(file.getAbsolutePath());
                        uploadCallback.onSucces(media);
                    }else {
                       uploadCallback.onFailure("Fichier corrompu");
                    }

                } else {
                    go.setVisibility(View.VISIBLE);
                   uploadCallback.onFailure("Erreur technique");
                }
                if(prg!=null)  prg.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(prg!=null)  prg.setVisibility(View.GONE);go.setVisibility(View.VISIBLE);
                S.toast(context,"Echec de recupération du fichier.");
                uploadCallback.onFailure(t+"");
            }
        });
    }

    public   File writeResponseBodyToDisk(ResponseBody body, String ext,String nom) {
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
                return file;
            } catch (IOException e) {
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
            return null;
        }
    }

    //public  static String FOLDER_YAAYI ="";

    public  String extraireChema(String url){
        String fnam=S.filemane(url);
        fnam=S.getPath(appNam+"/"+fnam,context);
        return fnam;
    }
    public void uploadFile(final Media media, final View view, final String url, List<String> prams,
                           UploadCallback uploadCallback) {
        Uri fileUri=Uri.parse(new File(media.getPath()).toString());
        String ph=media.getPath();
        ///ph=ph.replace(" ","%");
        //files/media
        if(fileUri==null||ph==null){
           return;
        }
        File file = new File(ph);
        if(view!=null){
            view.findViewById(R.id.pbc).setVisibility(View.VISIBLE);
        }
        String tp=getMimeType(ph);
        if(tp==null){
            tp="application/pdf";
        }
        ApiService apiService = ApiClient.getApiClient3().create(ApiService.class);
        RequestBody requestFile = RequestBody.create(
                MediaType.parse(tp),
                file
        );
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                null;
        try {
            body = MultipartBody.Part.createFormData("file",  URLEncoder.encode(file.getName(), "utf-8"), requestFile);
        } catch (UnsupportedEncodingException e) {
            // Dialogue.neutreDialog(id+"  vvvv"+" "+body,ty,context).show();
            e.printStackTrace();
            return;
        }

        Map<String, RequestBody> params = new HashMap<>();
        for (String p:prams){
            if(p.contains(":")){
                String[] m=p.split(":");
                params.put(m[0], RequestBody.create(MediaType.parse("text/plain"), m[1]));
            }
        }
        // finally, execute the request
        Call<UploadFileResponse> call = apiService.uploadCompteProfil(url, body,params);
        call.enqueue(new Callback<UploadFileResponse>() {
            @Override
            public void onResponse(Call<UploadFileResponse> call,  Response<UploadFileResponse> response) {
                if(response.body()!=null){
                    // finish();
                    media.setPath(response.body().getFileDownloadUri());
                    uploadCallback.onSucces(media);

                }else {
                    uploadCallback.onFailure("Erreur technique");
                }
                if(view!=null){
                    view.findViewById(R.id.pbc).setVisibility(View.GONE);
                }
                System.out.println("########--"+(response.body())+" -- "+"----");
                // Dialogue.neutreDialog(id+"  "+response.body()+" ",ty,mContext).show();
            }

            @Override
            public void onFailure(Call<UploadFileResponse> call, Throwable t) {
                if(view!=null){
                    view.findViewById(R.id.pbc).setVisibility(View.GONE);
                }
                uploadCallback.onFailure(t+"");
            }
        });
    }


    public  String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public String getFilePathFromUri(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String fileName = getFileName(uri);
        File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);

        try (InputStream inputStream = contentResolver.openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {

            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath();
    }
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    final int PICK_IMAGE_REQUEST=1,REQUEST_CODE_PERMISSION=2;
    public void openGallery() {
        // Create an intent to open the image gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    static final int REQUEST_IMAGE_CAPTURE = 3;

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
           activity. startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }
    public String saveImage(Bitmap imageBitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageFileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Interface de callback pour les upload.
     */
    public interface UploadCallback {
        default void onSucces(){

        };
        void onSucces(Media media);

        void onFailure(String message);
    }

}

