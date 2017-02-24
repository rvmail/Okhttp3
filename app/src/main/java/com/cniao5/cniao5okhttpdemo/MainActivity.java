package com.cniao5.cniao5okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        OkHttpClient

    }




    public void getRequest(View v){



        OkHttpClient client = new OkHttpClient();


        String url = "http://192.168.1.189:5000/user/info?id=1";

        Request request = new Request.Builder().url(url).build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Log.d("MainActivity","失败------"+e.getLocalizedMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {



                String result = response.body().string();

                Log.d("MainActivity","成功:"+result);


                if(response.body() !=null){
                    response.body().close();
                }




            }
        });




    }



    private void printHeader(Headers hs){

        Map<String,List<String>> headers = hs.toMultimap();


        for(Map.Entry<String,List<String>> entry : headers.entrySet()){


            Log.d("MainActivity",entry.getKey());
            for (String val : entry.getValue()){
                Log.d("MainActivity",val);
            }


        }

    }



}
