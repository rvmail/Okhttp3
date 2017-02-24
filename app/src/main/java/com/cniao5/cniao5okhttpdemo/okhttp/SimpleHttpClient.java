package com.cniao5.cniao5okhttpdemo.okhttp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Ivan on 2016/10/6.
 */

public class SimpleHttpClient {



    private Builder  mBuilder;


    private SimpleHttpClient(Builder builder){
        this.mBuilder = builder;
    }



    public Request buildRequest(){




       Request.Builder builder =  new Request.Builder();

        if(mBuilder.method=="GET") {

            builder.url(buildGetRequestParam());
            builder.get();
        }

        else if (mBuilder.method =="POST"){

            try {
                builder.post(buildRequestBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            builder.url(mBuilder.url);
        }



        return builder.build();
    }




    private String buildGetRequestParam(){

        if (mBuilder.mParams.size()<=0)
            return this.mBuilder.url;

        Uri.Builder builder = Uri.parse(mBuilder.url).buildUpon();

        for (RequestParam  p: mBuilder.mParams) {

            builder.appendQueryParameter(p.getKey(),p.getObj()==null?"":p.getObj().toString());
        }

        String url = builder.build().toString();


        return url;


    }

    private RequestBody buildRequestBody() throws JSONException {

        if(mBuilder.isJsonParam){

            JSONObject jsonObj = new JSONObject();

            for (RequestParam p: mBuilder.mParams) {

                jsonObj.put(p.getKey(),p.getObj());
            }

            String json = jsonObj.toString();

            Log.d("SimpleRequest","request json="+json);
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        }


        FormBody.Builder builder = new FormBody.Builder();

        for (RequestParam  p: mBuilder.mParams) {

            builder.add(p.getKey(),p.getObj()==null?"":p.getObj().toString());
        }

        return builder.build();


    }



    public void enqueue(BaseCallback callback){



        OKHttpManager.getInstance().request(this,callback);



    }



    public static   Builder newBuilder(){

        return new Builder();
    }




    public static class Builder{


        private String url;
        private String method;
        private boolean isJsonParam;

        private List<RequestParam> mParams;


        private Builder(){

            method = "GET";


        }
        public  SimpleHttpClient build(){

            return new SimpleHttpClient(this);
        }



        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder get(){


            method="GET";
            return this;

        }

        /**
         * Form 表单
         * @return
         */
        public Builder post(){

            method="POST";
            return this;
        }


        /**
         * JSON 参数
         * @return
         */
        public Builder json(){

            isJsonParam =true;

            return post();

        }

        public Builder addParam(String key, Object value){

            if(mParams==null){
                mParams = new ArrayList<>();
            }

            mParams.add(new RequestParam(key,value));

            return this;

        }



    }
}
