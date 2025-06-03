package com.credi.fings.publics.service;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.MonFichier;
import com.credi.fings.publics.utils.S;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpApi {
    private String endPoint;
    private TextView viewer;
    private List<Object> list;
    private Object object;
    private Context context;
    private String message;
    private FindObject findObject;
    private FindObjects findObjects;
    private ProgressBar progressBar;
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public HttpApi setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    public FindObject getFindObject() {
        return findObject;
    }

    public void setFindObject(FindObject findObject) {
        this.findObject = findObject;
    }

    public FindObjects getFindObjects() {
        return findObjects;
    }

    public void setFindObjects(FindObjects findObjects) {
        this.findObjects = findObjects;
    }

    public List<Object> getList() {
        return list==null?new ArrayList<>():list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public HttpApi(Context context) {
        this.context = context;
    }

    public HttpApi(String endPoint, TextView viewer, Context context) {
        this.endPoint = endPoint;
        this.viewer = viewer;
        this.context = context;
        ApiClient.getServer(context);
    }

    public void getAllDatas() {
        viewer.setText("");
        String url=ApiClient.BASE_URL_PROD+""+endPoint;
        String sv=url.replace("/","_");
        String js = MonFichier.lire(context, sv);
        if (js.length() > 0) {
            list = Ut.listFromJs(js);
            viewer.setText("local");
        }

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<List<Object>> call = apiService.getDataList(url);
        String finalSv = sv;
        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                viewer.setText("Ok");
                if (response.body() != null) {
                    list = response.body();
                    String js = Ut.listJs(list);
                    MonFichier.ecrire(context, finalSv, js);
                } else {
                    String js = MonFichier.lire(context, finalSv);
                    if (js.length() > 0) {
                        S.toast(context, "Echec de technique");
                        list = Ut.listFromJs(js);
                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                message=t.toString();
                viewer.setText("Error");
            }
        });
    }
    public void getAllDatas(Object o) {
        if(o==null){
            getAllDatas();
        }else {
            viewer.setText("");
            String url=ApiClient.BASE_URL_PROD+""+endPoint;
            String sv=url.replace("/","_");
            Object idserveur=Ut.getValue(o,"idServeur");
            //  System.out.println(" http idserveur= "+idserveur);
            if(idserveur!=null){
                sv=sv+"_"+idserveur;
            }
            String js = MonFichier.lire(context, sv);
            if (js.length() > 0) {
                list = Ut.listFromJs(js);
                viewer.setText("local");
                // System.out.println(list.size()+"  =ziz="+sv);
            }

            ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
            Call<List<Object>> call = apiService.postDataList(url,o);
            String finalSv = sv;
            call.enqueue(new Callback<List<Object>>() {
                @Override
                public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                    viewer.setText("Ok");
                    if (response.body() != null) {
                        list = response.body();
                        String js = Ut.listJs(list);
                        MonFichier.ecrire(context, finalSv, js);
                    } else {
                        viewer.setText("technique");

                    }
                }

                @Override
                public void onFailure(Call<List<Object>> call, Throwable t) {
                    viewer.setText("Error");
                    message=t.toString();

                }
            });
        }
    }
    public void getListDatas(List<Object> o) {
        if(o==null){
            getDatas();
        }else {
            viewer.setText("");
            String url = ApiClient.BASE_URL_PROD + "" + endPoint;

            String sv=url.replace("/","_");
            if(!(o instanceof List<?>)){
                Object idserveur=Ut.getValue(o,"idServeur");
                if(idserveur!=null){
                    sv=sv+"_"+idserveur;
                }
            }
            String js = MonFichier.lire(context, sv);
            if (!js.isEmpty()) {
                object = Ut.fromJs(js,Object.class);
                viewer.setText("local");
            }

            ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
            Call<Object> call = apiService.postListeData(url, o);
            String finalSv = sv;
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.body() != null) {
                        object = response.body();
                        String js = Ut.js(object);
                        MonFichier.ecrire(context, finalSv, js);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewer.setText("Ok");
                            }
                        }, 100);

                    } else {
                        viewer.setText("technique");
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    message = t.toString();
                    viewer.setText("Error");
                }
            });
        }
    }
    public void getDatas(Object o) {
        if(o==null){
            getDatas();
        }else {
            viewer.setText("");
            String url = ApiClient.BASE_URL_PROD + "" + endPoint;

            String sv=url.replace("/","_");
            if(!(o instanceof List<?>)){
                Object idserveur=Ut.getValue(o,"idServeur");
                if(idserveur!=null){
                    sv=sv+"_"+idserveur;
                }
            }
            String js = MonFichier.lire(context, sv);
            if (!js.isEmpty()) {
                object = Ut.fromJs(js,Object.class);
                viewer.setText("local");
            }

            ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
            Call<Object> call = apiService.postData(url, o);
            String finalSv = sv;
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.body() != null) {
                        object = response.body();
                        String js = Ut.js(object);
                        MonFichier.ecrire(context, finalSv, js);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewer.setText("Ok");
                            }
                        }, 100);

                    } else {
                        viewer.setText("technique");
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    message = t.toString();
                    viewer.setText("Error");
                }
            });
        }
    }
    public void getDatas() {
        viewer.setText("");
        String url=ApiClient.BASE_URL_PROD+""+endPoint;

        String sv=url.replace("/","_");
        String js = MonFichier.lire(context, sv);
        if (!js.isEmpty()) {
            object = Ut.fromJs(js,Object.class);
            viewer.setText("local");
        }

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<Object> call = apiService.getData(url);
        String finalSv = sv;
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() != null) {
                    object = response.body();
                    String js = Ut.js(object);
                    MonFichier.ecrire(context, finalSv, js);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewer.setText("Ok");
                        }
                    }, 100);

                }else {
                    viewer.setText("technique");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                message=t.toString();
                viewer.setText("Error");
            }
        });
    }


    private String localUrl1(String endPoint,Object o){
        String url=endPoint.contains("http")?endPoint: ApiClient.BASE_URL_PROD+""+endPoint;
        String sv=url.replace("/","_").replace("?","_").replace("=","_")
                .replace("&","_");
        Object idserveur=Ut.getValue(o,"idServeur");
        if(idserveur!=null){
            sv=sv+"_"+idserveur;
        }
        return sv;
    }
    private String localUrl2(String endPoint,Object o){
        String url=ApiClient.BASE_URL_PROD+""+endPoint;
        String sv;
        Object idserveur=Ut.getValue(o,"idServeur");
        String rl=ApiClient.BASE_URL+""+endPoint;
        sv=rl.replace("/","_");
        if(idserveur!=null){
            sv=sv+"_"+idserveur;
        }
        return sv;
    }

    public void data(String url, Object obje,FindObject findObject) {
        if(findObject!=null){
            Object o;
            String vs=localUrl1(url,obje);
            String js=MonFichier.lire(context,vs);
            if(js!=null&&!js.isEmpty()){
                o= Ut.fromJs(js,Object.class);
                findObject.find(o,"local");
            }else {
                if(progressBar!=null){
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<Object> call = obje!=null?apiService.postData(url,obje):apiService.getData(url);;
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                if (response.body() != null) {
                    String js = Ut.js(response.body());
                    MonFichier.ecrire(context, localUrl1(url,obje), js);
                    if(findObject!=null){
                        findObject.find(response.body(),"ok");
                    }
                }else {
                  S.toast(context,"technique "+url);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                S.toast(context,t.toString());
            }
        });
    }
    public void datas(String url, Object obje,FindObjects findObjects) {
        if(findObjects!=null){
            List<Object> o;
            String vs=localUrl1(url,obje);
            String js=MonFichier.lire(context,vs);
            if(js!=null&&!js.isEmpty()){
                o= Ut.listFromJs(js);
                findObjects.finds(o,"local");
            }else {
                if(progressBar!=null){
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<List<Object>> call = obje!=null?apiService.postDataList(url,obje):apiService.getDataList(url);
        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                if (response.body() != null) {
                    String js = Ut.listJs(response.body());
                    MonFichier.ecrire(context, localUrl1(url,obje), js);
                    if(findObjects!=null){
                        findObjects.finds(response.body(),"200");
                    }
                }else {
                    if(findObjects!=null){
                        findObjects.finds(null,"Erreur technique");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                if(findObjects!=null){
                    findObjects.finds(null,t.toString());
                }
            }
        });
    }
    public void datas(String url, FindObjects findObjects) {
        if(findObjects!=null){
            List<Object> o;
            String vs=localUrl1(url,null);
            String js=MonFichier.lire(context,vs);
            if(js!=null&&!js.isEmpty()){
                o= Ut.listFromJs(js);
                findObjects.finds(o,"local");
            }else {
                if(progressBar!=null){
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<List<Object>> call = apiService.getDataList(url);
        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                if (response.body() != null) {
                    String js = Ut.listJs(response.body());
                    MonFichier.ecrire(context, localUrl1(url,null), js);
                    if(findObjects!=null){
                        findObjects.finds(response.body(),"200");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
    public void getdata(String url, FindObject findObjects) {
        if(findObjects!=null){
            Object o;
            String vs=localUrl1(url,null);
            String js=MonFichier.lire(context,vs);
            if(js!=null&&!js.isEmpty()){
                o= Ut.fromJs(js,Object.class);
                findObjects.find(o,"local");
            }else {
                if(progressBar!=null){
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<Object> call = apiService.getData(url);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                if (response.body() != null) {
                    String js = Ut.js(response.body());
                    MonFichier.ecrire(context, localUrl1(url,null), js);
                    if(findObjects!=null){
                        findObjects.find(response.body(),"ok");
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
