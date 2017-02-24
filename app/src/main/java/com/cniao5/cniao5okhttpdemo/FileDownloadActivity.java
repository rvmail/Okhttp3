package com.cniao5.cniao5okhttpdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cniao5.cniao5okhttpdemo.okhttp.ProgressListener;
import com.cniao5.cniao5okhttpdemo.okhttp.ProgressResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileDownloadActivity extends AppCompatActivity {


    public String url ="http://112.124.22.238:8081/course_api/css/net_music.apk";
    public String fileName = "net_music.apk";



    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        ButterKnife.bind(this);


        requestPermission();
        initOKhttp();
    }

    private void initOKhttp() {

//        httpClient = new OkHttpClient();

        httpClient =  new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {


                Response reponse = chain.proceed(chain.request());


                return reponse.newBuilder().body(new ProgressResponseBody(reponse.body(),new Prg())).build();
            }
        }).build();
    }



    class Prg implements ProgressListener{


        @Override
        public void onProgress(final int progress) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                }
            });

        }

        @Override
        public void onDone(long totalSize) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FileDownloadActivity.this,"下载完成",Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;

    public void requestPermission(){
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"please give me the permission",Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作

                    Toast.makeText(FileDownloadActivity.this,"已获取权限",Toast.LENGTH_LONG).show();
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }

    }






    @OnClick(R.id.btn_download)
    public void onClick() {

        downloadAPK();

    }

    private void downloadAPK() {



        Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("LoginActivity","请求文件出差");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {



                writeFile(response);



            }
        });




    }



    Handler mHandler = new Handler(){


        @Override
        public void handleMessage(Message msg) {


            if(msg.what==1){


               int  progress = msg.arg1;
                mProgressBar.setProgress(progress);

            }

        }
    };


    private void writeFile(Response response) {


        InputStream is =null;
        FileOutputStream fos = null;


        is = response.body().byteStream();


        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        Log.d("FileDownloadActivity","path:"+path);

        File file = new File(path,fileName);
        try {
            fos = new FileOutputStream(file);



            byte[] bytes = new byte[1024];

            int len =0;

//            long  totalSize = response.body().contentLength();
//
//            long sum =0;

            while ((len =is.read(bytes)) !=-1){


                fos.write(bytes);

//
//                sum +=len;
//
//                int progress = (int) ((sum * 1.0f / totalSize) * 100);
//
//                Message msg = mHandler.obtainMessage(1);
//                msg.arg1 = progress;
//
//                mHandler.sendMessage(msg);



            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

                try {
                    if(is !=null){
                        is.close();
                    }

                    if(fos !=null){
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();

            }


        }


    }


}
