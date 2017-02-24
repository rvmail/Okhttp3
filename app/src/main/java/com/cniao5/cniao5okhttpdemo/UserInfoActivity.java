package com.cniao5.cniao5okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.btn_get)
    Button mBtnGet;
    @BindView(R.id.imgview)
    ImageView mImgview;
    @BindView(R.id.txt_username)
    TextView mTxtUsername;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get)
    public void onClick() {




        getUserInfo();
    }

    private void getUserInfo() {


        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .get()
                .url("http://192.168.1.189:5000/user/info?id=1")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Log.d("UserInfoActivity","请求失败:"+e.getLocalizedMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {



                if(response.isSuccessful()){

                   String json =  response.body().string();

                    showuser(json);


                }




            }
        });




    }



    private void showuser(final String json){



        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObj = new JSONObject(json);


                    String id = jsonObj.optString("id");
                    String username = jsonObj.optString("username");
                    String head_url = jsonObj.optString("head_url");



                    mTxtUsername.setText(username);

                    Picasso.with(UserInfoActivity.this).load(head_url).resize(200,200).centerCrop().into(mImgview);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });



    }



}
